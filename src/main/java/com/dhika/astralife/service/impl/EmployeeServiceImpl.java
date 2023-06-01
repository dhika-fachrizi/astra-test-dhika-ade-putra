package com.dhika.astralife.service.impl;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.entity.SalaryId;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.SalaryModel;
import com.dhika.astralife.repository.EmployeeRepository;
import com.dhika.astralife.repository.SalaryRepository;
import com.dhika.astralife.service.EmployeeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    Validator validator;

    @Override
    public EmployeeEntity createEmployee(EmployeeModel request) {


        //mapped model to entity
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity addEmployee = modelMapper.map(request, EmployeeEntity.class);
        EmployeeEntity employee = employeeRepository.save(addEmployee);

        return employee;
    }

    @Override
    public EmployeeEntity getEmployee(Integer id) {

        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        return employee;
    }

    @Override
    public List<EmployeeEntity> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities;
    }

    @Override
    public EmployeeEntity updateEmployee(Integer id, EmployeeModel request) {

        EmployeeEntity employee = checkEmployee(id);
        request.setEmployeeNo(employee.getEmployeeNo());
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity addEmployee = modelMapper.map(request, EmployeeEntity.class);
        return employeeRepository.save(addEmployee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        EmployeeEntity employee = checkEmployee(id);
        employeeRepository.delete(employee);
    }

    public EmployeeEntity checkEmployee(Integer id) {
        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new NotFoundException("Data Not Found");
        else return employee;
    }

}
