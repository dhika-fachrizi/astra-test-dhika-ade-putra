package com.dhika.astralife.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dhika.astralife.exception.InvalidDataException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.entity.TitleEntity;
import com.dhika.astralife.entity.TitleId;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.TitleModel;
import com.dhika.astralife.repository.TitleRepository;
import com.dhika.astralife.service.EmployeeService;
import com.dhika.astralife.service.TitleService;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class TitleServiceImpl implements TitleService{

    private final EmployeeService employeeService;

    private final TitleRepository titleRepository;

    @Autowired
    Validator validator;

    @Override
    public TitleEntity createTitleByEmployee(Integer employeeId, TitleModel request){
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);

        TitleEntity titleEntity = TitleEntity.builder()
                .employee(employee)
                .title(request.getTitle())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .build();

        checkDateTitle(titleEntity);
        return titleRepository.save(titleEntity);
    }

    @Override
    public List<TitleEntity> getTitlesByEmployee(Integer employeeId){
        List<TitleEntity> salaries = titleRepository.getTitlesByEmployee(employeeId);
        if (salaries.size() == 0)
            throw new NotFoundException("Title not found");
        return salaries;
    }

    @Override
    public TitleEntity getCurrentTitleByEmployee(Integer employeeId){
        TitleEntity title = titleRepository.getCurrentTitleByEmployee(employeeId);
        return title;
    }

    @Override
    public TitleEntity updateTitleByEmployee(Integer employeeId, TitleModel request){
        //Check employee data
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);

        //
        Set<ConstraintViolation<TitleModel>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<TitleModel> violation : violations) {
                // bypass fromDate validation
                if (!violation.getPropertyPath().toString().equals("fromDate") &&
                        !violation.getPropertyPath().toString().equals("title"))
                    throw new ConstraintViolationException(violations);
            }

        }

        TitleEntity title = titleRepository.getCurrentTitleByEmployee(employeeId);
        if (title == null)
            throw new NotFoundException("Title not found");

        if(request.getToDate() != null) title.setToDate(request.getToDate());

        checkDateTitle(title);

        return titleRepository.save(title);
    }

    @Override
    public void deleteCurrentTitle(Integer employeeId) {
        //Check employee data
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);

        TitleEntity title = titleRepository.getCurrentTitleByEmployee(employeeId);
        if (title == null)
            throw new NotFoundException("Title not found");

        TitleId titleId = TitleId.builder()
                .employee(employee)
                .title(title.getTitle())
                .fromDate(title.getFromDate())
                .build();

        titleRepository.deleteById(titleId);
    }

    private TitleEntity checkTitle(TitleId titleId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dt = dateFormat.format(titleId.getFromDate());
        TitleEntity title = titleRepository.getTitleByEmployee(titleId.getEmployee().getEmployeeNo(),dt);
        if (title == null)
            throw new NotFoundException("Data Not Found");
        else return title;
    }

    private void checkDateTitle(TitleEntity titleEntity){
        Date employeeHireDate  = titleEntity.getEmployee().getHireDate();
        Date titleFromDate = titleEntity.getFromDate();
        Date titleToDate = titleEntity.getToDate();
        if (titleToDate != null){
            if (titleToDate.before(titleFromDate)){
                throw new InvalidDateException("to date must be before from date");
            }
        }


        if (employeeHireDate.after(titleFromDate)){
            throw new InvalidDateException("from date must be same as or after from employee hire date");
        }

    }
}
