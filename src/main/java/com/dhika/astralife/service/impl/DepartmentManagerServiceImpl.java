package com.dhika.astralife.service.impl;

import com.dhika.astralife.entity.*;
import com.dhika.astralife.exception.InvalidDataException;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.model.DepartmentManagerModel;
import com.dhika.astralife.repository.DepartmentManagerRepository;
import com.dhika.astralife.service.DepartmentManagerService;
import com.dhika.astralife.service.DepartmentService;
import com.dhika.astralife.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentManagerServiceImpl implements DepartmentManagerService {

    private final DepartmentManagerRepository departmentManagerRepository;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Override
    public DepartmentManagerEntity createDepartmentManager(Integer idEmp
            , String idDept
            , DepartmentManagerModel request){

        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);


        DepartmentManagerId departmentManagerId = DepartmentManagerId.builder()
                .department(department)
                .employee(employee)
                .build();

        //handle duplicate data
        if (departmentManagerRepository.findById(departmentManagerId).orElse(null) != null)
            throw new InvalidDataException("data already exist");

        DepartmentManagerEntity addDepartmentManager = DepartmentManagerEntity.builder()
                .department(department)
                .employee(employee)
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .build();
        departmentManagerRepository.save(addDepartmentManager);


        return addDepartmentManager;
    }

    @Override
    public DepartmentManagerEntity getDepartmentManager(Integer idEmp, String idDept){

        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentManagerId departmentManagerId = DepartmentManagerId.builder()
                .employee(employee)
                .department(department)
                .build();

        return departmentManagerRepository.findById(departmentManagerId).orElse(null);
    }

    @Override
    public List<DepartmentManagerEntity> getAllDepartmentManager(Integer idEmployee){
        List<DepartmentManagerEntity> departmentManagerEntities = departmentManagerRepository
                .getAllDepartmentManager(idEmployee);
        return departmentManagerEntities;
    }

    @Override
    public DepartmentManagerEntity updateDepartmentManager(Integer idEmp
            , String idDept
            , DepartmentManagerModel request){

        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentManagerId departmentManagerId = DepartmentManagerId.builder()
                .employee(employee)
                .department(department)
                .build();

        DepartmentManagerEntity departmentManager = departmentManagerRepository
                .findById(departmentManagerId)
                .orElse(null);

        if (request.getFromDate() != null) departmentManager.setFromDate(request.getFromDate());
        if (request.getToDate() != null) departmentManager.setToDate(request.getToDate());

        return departmentManagerRepository.save(departmentManager);
    }

    @Override
    public void deleteDepartmentManager(Integer idEmp, String idDept){
        //check department
        DepartmentEntity department = departmentService.checkDepartment(idDept);

        //check employee
        EmployeeEntity employee = employeeService.checkEmployee(idEmp);

        DepartmentManagerId departmentManagerId = DepartmentManagerId.builder()
                .employee(employee)
                .department(department)
                .build();

        departmentManagerRepository.deleteById(departmentManagerId);
    }

    private void checkDateDepartmentManager(DepartmentManagerEntity departmentManagerEntity){
        Date employeeHireDate  = departmentManagerEntity.getEmployee().getHireDate();
        Date deptFromDate = departmentManagerEntity.getFromDate();
        Date deptToDate = departmentManagerEntity.getToDate();
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
