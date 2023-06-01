package com.dhika.astralife.service;

import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.model.DepartmentEmployeeModel;

import java.util.List;

public interface DepartmentEmployeeService {

    DepartmentEmployeeEntity createDepartmentEmployee(Integer idEmp
            , String idDept
            , DepartmentEmployeeModel request);

    DepartmentEmployeeEntity getDepartmentEmployee(Integer idEmp, String idDept);

    List<DepartmentEmployeeEntity> getAllDepartmentEmployee(Integer idEmployee);

    DepartmentEmployeeEntity updateDepartmentEmployee(Integer idEmployee
            ,String idDep
            , DepartmentEmployeeModel request);

    void deleteDepartmentEmployee(Integer idEmp, String idDept);
}
