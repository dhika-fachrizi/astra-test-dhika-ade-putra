package com.dhika.astralife.service.impl;

import com.dhika.astralife.entity.*;
import com.dhika.astralife.exception.InvalidDataException;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.model.DepartmentEmployeeModel;
import com.dhika.astralife.repository.DepartmentEmployeeRepository;
import com.dhika.astralife.service.DepartmentEmployeeService;
import com.dhika.astralife.service.DepartmentService;
import com.dhika.astralife.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService {

    private final DepartmentEmployeeRepository departmentEmployeeRepository;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Override
    public DepartmentEmployeeEntity createDepartmentEmployee(Integer idEmp
            , String idDept
            , DepartmentEmployeeModel request){
        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentEmployeeId departmentEmployeeId = DepartmentEmployeeId.builder()
                .department(department)
                .employee(employee)
                .build();

        //handle duplicate data
        if (departmentEmployeeRepository.findById(departmentEmployeeId).orElse(null) != null)
            throw new InvalidDataException("data already exist");

        DepartmentEmployeeEntity addDepartmentEmployee = DepartmentEmployeeEntity.builder()
                .department(department)
                .employee(employee)
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .build();
        departmentEmployeeRepository.save(addDepartmentEmployee);


        return addDepartmentEmployee;
    }

    @Override
    public DepartmentEmployeeEntity getDepartmentEmployee(Integer idEmp, String idDept){

        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentEmployeeId departmentEmployeeId = DepartmentEmployeeId.builder()
                .employee(employee)
                .department(department)
                .build();

        return departmentEmployeeRepository.findById(departmentEmployeeId).orElse(null);
    }

    @Override
    public List<DepartmentEmployeeEntity> getAllDepartmentEmployee(Integer idEmployee){
        List<DepartmentEmployeeEntity> departmentEmployeeEntities = departmentEmployeeRepository
                .getAllDepartmentEmployee(idEmployee);
        return departmentEmployeeEntities;
    }

    @Override
    public DepartmentEmployeeEntity updateDepartmentEmployee(Integer idEmp
            ,String idDept
            , DepartmentEmployeeModel request){

        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentEmployeeId departmentEmployeeId = DepartmentEmployeeId.builder()
                .employee(employee)
                .department(department)
                .build();

        DepartmentEmployeeEntity departmentEmployee = departmentEmployeeRepository
                .findById(departmentEmployeeId)
                .orElse(null);

        departmentEmployee.setFromDate(request.getFromDate());
        departmentEmployee.setToDate(request.getToDate());

        return departmentEmployeeRepository.save(departmentEmployee);
    }

    @Override
    public void deleteDepartmentEmployee(Integer idEmp, String idDept){
        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentEmployeeId departmentEmployeeId = DepartmentEmployeeId.builder()
                .employee(employee)
                .department(department)
                .build();

        departmentEmployeeRepository.deleteById(departmentEmployeeId);
    }

    private void checkDateDepartmentEmployee(DepartmentEmployeeEntity departmentEmployeeEntity){
        Date employeeHireDate  = departmentEmployeeEntity.getEmployee().getHireDate();
        Date deptFromDate = departmentEmployeeEntity.getFromDate();
        Date deptToDate = departmentEmployeeEntity.getToDate();
        if (deptToDate != null){
            if (deptToDate.before(deptFromDate)){
                throw new InvalidDateException("to date must be before from date");
            }
        }


        if (employeeHireDate.after(deptFromDate)){
            throw new InvalidDateException("from date must be same as or after from employee hire date");
        }

    }

}
