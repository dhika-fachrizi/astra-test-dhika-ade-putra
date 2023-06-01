package com.dhika.astralife.repository;

import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,String> {

    @Query(
            value = "select * from dept ORDER BY dept_no",
            nativeQuery = true
    )
    List<DepartmentEntity> getAllDepartment();
}
