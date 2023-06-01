package com.dhika.astralife.repository;

import com.dhika.astralife.entity.TitleEntity;
import com.dhika.astralife.entity.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<TitleEntity, TitleId> {

    @Query(
            value = "select * from titles where emp_no = :employeeNo " +
                    "ORDER BY from_date",
            nativeQuery = true
    )
    List<TitleEntity> getTitlesByEmployee(@Param("employeeNo") int employeeNo);

    @Query(
            value = "select * from titles where emp_no = :employeeNo " +
                    "ORDER BY from_date DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )

    TitleEntity getCurrentTitleByEmployee(@Param("employeeNo") int employeeNo);
    @Query(
            value = "select * from titles where emp_no = :employeeNo " +
                    "AND DATE(from_date) = :dateFrom " +
                    "LIMIT 1",
            nativeQuery = true
    )
    TitleEntity getTitleByEmployee(@Param("employeeNo") int employeeNo, @Param("dateFrom") String dateFrom);
}
