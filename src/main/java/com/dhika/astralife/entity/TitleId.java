package com.dhika.astralife.entity;

import com.dhika.astralife.entity.EmployeeEntity;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleId {
    private EmployeeEntity employee;
    private String title;
    private Date fromDate;
}
