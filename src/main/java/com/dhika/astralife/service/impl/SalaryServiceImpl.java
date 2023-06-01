package com.dhika.astralife.service.impl;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.entity.SalaryId;
import com.dhika.astralife.exception.InvalidDataException;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.SalaryModel;
import com.dhika.astralife.repository.SalaryRepository;
import com.dhika.astralife.service.EmployeeService;
import com.dhika.astralife.service.SalaryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeService employeeService;

    @Autowired
    Validator validator;

    @Override
    public SalaryEntity createSalaryByEmployee(Integer employeeId, SalaryModel request){
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);
        
        SalaryEntity salaryEntity = SalaryEntity.builder()
                .employee(employee)
                .salary(request.getSalary())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .build();

        checkDateSalary(salaryEntity);
        return salaryRepository.save(salaryEntity);
    }

    @Override
    public List<SalaryEntity> getSalariesByEmployee(Integer employeeId){
        List<SalaryEntity> salaries = salaryRepository.getSalariesByEmployee(employeeId);
        return salaries;
    }

    @Override
    public SalaryEntity getCurrentSalaryByEmployee(Integer employeeId){
        SalaryEntity salary = salaryRepository.getCurrentSalaryByEmployee(employeeId);
        return salary;
    }

    @Override
    public SalaryEntity updateSalaryByEmployee(Integer employeeId, SalaryModel request){
        //Check employee data
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);

        //
        Set<ConstraintViolation<SalaryModel>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<SalaryModel> violation : violations) {
                // bypass fromDate validation
                if (!violation.getPropertyPath().toString().equals("fromDate"))
                    throw new ConstraintViolationException(violations);
            }

        }

        SalaryEntity salary = salaryRepository.getCurrentSalaryByEmployee(employeeId);
        if (salary == null)
            throw new NotFoundException("Salary not found");

        if (request.getToDate() != null) salary.setToDate(request.getToDate());
        if (request.getSalary() != null) salary.setSalary(request.getSalary());

        checkDateSalary(salary);

        return salaryRepository.save(salary);
    }

    @Override
    public void deleteCurrentSalary(Integer employeeId) {
        //Check employee data
        EmployeeEntity employee = employeeService.checkEmployee(employeeId);

        SalaryEntity salary = salaryRepository.getCurrentSalaryByEmployee(employeeId);
        if (salary == null)
            throw new NotFoundException("Salary not found");

        SalaryId salaryId = SalaryId.builder()
                .employee(employee)
                .fromDate(salary.getFromDate())
                .build();

        salaryRepository.deleteById(salaryId);
    }

    private SalaryEntity checkSalary(SalaryId salaryId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dt = dateFormat.format(salaryId.getFromDate());
        SalaryEntity salary = salaryRepository.getSalaryByEmployee(salaryId.getEmployee().getEmployeeNo(),dt);
        if (salary == null)
            throw new NotFoundException("Data Not Found");
        else return salary;
    }

    private void checkDateSalary(SalaryEntity salaryEntity){
        Date employeeHireDate  = salaryEntity.getEmployee().getHireDate();
        Date salaryFromDate = salaryEntity.getFromDate();
        Date salaryToDate = salaryEntity.getToDate();
        if (salaryToDate != null){
            if (salaryToDate.before(salaryFromDate)){
                throw new InvalidDateException("to date must be before from date");
            }
        }


        if (employeeHireDate.after(salaryFromDate)){
            throw new InvalidDateException("from date must be same as or after from employee hire date");
        }

    }
}
