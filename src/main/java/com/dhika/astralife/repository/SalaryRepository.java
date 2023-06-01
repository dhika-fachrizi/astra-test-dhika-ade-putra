package com.dhika.astralife.repository;

import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.entity.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, SalaryId> {

    @Query(
            value = "select * from salaries where emp_no = :employeeNo " +
                    "ORDER BY from_date",
            nativeQuery = true
    )
    List<SalaryEntity> getSalariesByEmployee(@Param("employeeNo") int employeeNo);

    @Query(
            value = "select * from salaries where emp_no = :employeeNo " +
                    "ORDER BY from_date DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
   SalaryEntity getCurrentSalaryByEmployee(@Param("employeeNo") int employeeNo);
    @Query(
            value = "select * from salaries where emp_no = :employeeNo " +
                    "AND DATE(from_date) = :dateFrom " +
                    "LIMIT 1",
            nativeQuery = true
    )
    SalaryEntity getSalaryByEmployee(@Param("employeeNo") int employeeNo, @Param("dateFrom") String dateFrom);
}
