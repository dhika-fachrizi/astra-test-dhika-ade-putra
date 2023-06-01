package com.dhika.astralife.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentManagerId {

    private EmployeeEntity employee;

    private DepartmentEntity department;
}
