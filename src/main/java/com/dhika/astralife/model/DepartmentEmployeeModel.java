package com.dhika.astralife.model;

import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "departmentEmployee", itemRelation = "departmentEmployee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DepartmentEmployeeModel extends RepresentationModel<DepartmentEmployeeModel> {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private EmployeeEntity employee;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private DepartmentEntity department;

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date fromDate;

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date toDate;
}
