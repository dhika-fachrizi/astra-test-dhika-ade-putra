package com.dhika.astralife.model;

import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Relation(collectionRelation = "departmentManager", itemRelation = "departmentManager")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DepartmentManagerModel extends RepresentationModel<DepartmentManagerModel> {

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
