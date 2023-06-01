package com.dhika.astralife.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryId {

    private EmployeeEntity employee;
    private Date fromDate;
}
