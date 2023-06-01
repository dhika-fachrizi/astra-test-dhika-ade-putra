package com.dhika.astralife.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dept_emp",indexes = {
        @Index(name = "dept_no", columnList = "dept_no"),
        @Index(name = "emp_no", columnList = "emp_no")
})
@IdClass(DepartmentEmployeeId.class)
@Builder
public class DepartmentEmployeeEntity {

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "emp_no",
            referencedColumnName = "emp_no",
            foreignKey = @ForeignKey(name="FK_emp_no")
    )
    @JsonBackReference("employee")
    private EmployeeEntity employee;

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "dept_no",
            referencedColumnName = "dept_no",
            foreignKey = @ForeignKey(name="FK_dept_no")
    )
    @JsonBackReference("department")
    private DepartmentEntity department;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;




}
