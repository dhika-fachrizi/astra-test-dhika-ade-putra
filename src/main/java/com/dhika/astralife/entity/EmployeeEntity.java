package com.dhika.astralife.entity;

import com.dhika.astralife.model.GenderEnumModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "emp_no", length = 11)
    private Integer employeeNo;

    @Column(name = "bird_date", nullable = false)
    private Date birthDate;

    @Column(name = "first_name", nullable = false , length = 14)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnumModel gender;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SalaryEntity> salaries;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TitleEntity> titles;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference("employee")
    private List<DepartmentEmployeeEntity> deptEmp;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)

    private List<DepartmentManagerEntity> deptManager;



}
