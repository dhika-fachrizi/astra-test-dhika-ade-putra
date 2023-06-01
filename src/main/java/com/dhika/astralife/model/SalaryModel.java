package com.dhika.astralife.model;

import com.dhika.astralife.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "salary", itemRelation = "salary")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SalaryModel extends RepresentationModel<SalaryModel>{

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private EmployeeEntity employee;

    @NotNull
    private Integer salary;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fromDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date toDate;

}
