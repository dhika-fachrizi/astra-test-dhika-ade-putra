package com.dhika.astralife.repository;

import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.entity.DepartmentManagerEntity;
import com.dhika.astralife.entity.DepartmentManagerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentManagerRepository extends JpaRepository<DepartmentManagerEntity, DepartmentManagerId> {

    @Query(
            value = "select * from dept_manager where emp_no = :employeeNo " +
                    "ORDER BY from_date DESC ",
            nativeQuery = true
    )
    List<DepartmentManagerEntity> getAllDepartmentManager(@Param("employeeNo") Integer employeeNo);
}
