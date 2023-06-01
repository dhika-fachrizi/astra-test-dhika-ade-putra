package com.dhika.astralife.model;

import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.entity.DepartmentManagerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "DepartmentModel", itemRelation = "DepartmentModel")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DepartmentModel extends RepresentationModel<DepartmentModel> {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4}$",
            message = "dep_no must be 4 length and no special characters")
    private String departmentNo;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9 ]{3,40}$",
            message = "dep_name max 40 length no number and no special characters")
    private String departmentName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DepartmentManagerEntity> deptManager;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DepartmentEmployeeEntity> depEmp;

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo.toUpperCase();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName.toLowerCase();
    }


}
