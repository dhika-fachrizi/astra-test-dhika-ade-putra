package com.dhika.astralife.service;

import com.dhika.astralife.entity.DepartmentManagerEntity;
import com.dhika.astralife.model.DepartmentManagerModel;

import java.util.List;

public interface DepartmentManagerService {

    DepartmentManagerEntity createDepartmentManager(Integer idEmp
            , String idDept
            , DepartmentManagerModel request);

    DepartmentManagerEntity getDepartmentManager(Integer idEmp, String idDept);

    List<DepartmentManagerEntity> getAllDepartmentManager(Integer idEmployee);

    DepartmentManagerEntity updateDepartmentManager(Integer idEmployee
            ,String idDep
            , DepartmentManagerModel request);

    void deleteDepartmentManager(Integer idEmp, String idDept);
}
