package com.dhika.astralife.service;

import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.model.DepartmentModel;

import java.util.List;

public interface DepartmentService {

    DepartmentEntity createDepartment(DepartmentModel request);

    DepartmentEntity getDepartment(String idDept);

    List<DepartmentEntity> getAllDepartment();

    DepartmentEntity updateDepartment(String id, DepartmentModel request);

    void deleteDepartment(String id);

    DepartmentEntity checkDepartment(String id);
}
