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
@Table(name = "dept_manager",indexes = {
        @Index(name = "dept_no", columnList = "dept_no"),
        @Index(name = "emp_no", columnList = "emp_no")
})
@IdClass(DepartmentManagerId.class)
@Builder
public class DepartmentManagerEntity {

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "dept_no",
            referencedColumnName = "dept_no",
            foreignKey = @ForeignKey(name="FK_2_dept_no")
    )
    @JsonBackReference("department")
    private DepartmentEntity department;

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "emp_no",
            referencedColumnName = "emp_no",
            foreignKey = @ForeignKey(name="FK_2_emp_no")
    )
    @JsonBackReference("employee")
    private EmployeeEntity employee;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;


}
