package com.dhika.astralife.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salaries")
@IdClass(SalaryId.class)
@Builder
public class SalaryEntity {

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "emp_no",
            referencedColumnName = "emp_no"
    )
    @JsonBackReference
    private EmployeeEntity employee;

    @Column(name = "salary", length = 11 , nullable = false)
    private Integer salary;

    @Id
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;



}
