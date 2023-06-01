package com.dhika.astralife.service;

import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.model.SalaryModel;

import java.util.List;

public interface SalaryService {

    SalaryEntity createSalaryByEmployee(Integer employeeId, SalaryModel request);

    List<SalaryEntity> getSalariesByEmployee(Integer employeeId);

    SalaryEntity getCurrentSalaryByEmployee(Integer employeeId);

    SalaryEntity updateSalaryByEmployee(Integer employeeId, SalaryModel request);

    void deleteCurrentSalary(Integer employeeId);
}
