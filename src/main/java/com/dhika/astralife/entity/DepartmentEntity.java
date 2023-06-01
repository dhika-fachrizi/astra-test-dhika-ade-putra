package com.dhika.astralife.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dept")
@Builder
public class DepartmentEntity {

    @Id
    @Column(name = "dept_no",nullable = false,columnDefinition = "char(4)")
    private String departmentNo;

    @Column(name = "dept_name", length = 40, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<DepartmentManagerEntity> deptManager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference("department")
    private List<DepartmentEmployeeEntity> depEmp;
}
