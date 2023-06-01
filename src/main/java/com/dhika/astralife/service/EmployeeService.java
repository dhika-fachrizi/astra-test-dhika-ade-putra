package com.dhika.astralife.service;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.SalaryModel;

import java.util.Date;
import java.util.List;

public interface EmployeeService {

    public EmployeeEntity createEmployee(EmployeeModel request);

    EmployeeEntity getEmployee(Integer id);

    List<EmployeeEntity> getAllEmployee();

    EmployeeEntity updateEmployee(Integer id, EmployeeModel employeeModel);

    void deleteEmployee(Integer id);

    EmployeeEntity checkEmployee(Integer id);


}
