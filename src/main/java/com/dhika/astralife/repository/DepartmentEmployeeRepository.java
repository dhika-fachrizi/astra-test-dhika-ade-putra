package com.dhika.astralife.repository;

import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.entity.DepartmentEmployeeId;
import com.dhika.astralife.entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployeeEntity, DepartmentEmployeeId> {

    @Query(
            value = "select * from dept_emp where emp_no = :employeeNo " +
                    "ORDER BY from_date DESC ",
            nativeQuery = true
    )
    List<DepartmentEmployeeEntity> getAllDepartmentEmployee(@Param("employeeNo") Integer employeeNo);
}
