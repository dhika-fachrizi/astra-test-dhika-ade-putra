package com.dhika.astralife.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentEmployeeId {

    private EmployeeEntity employee;

    private DepartmentEntity department;
}
