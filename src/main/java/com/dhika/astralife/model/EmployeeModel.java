package com.dhika.astralife.model;

import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.entity.DepartmentManagerEntity;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.entity.TitleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "employee", itemRelation = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class EmployeeModel extends RepresentationModel<EmployeeModel> {

    private Integer employeeNo;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z ]{3,14}$",
            message = "first name must be 14 length and no special characters")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z ]{3,16}$",
            message = "last name must be 16 length and no special characters")
    @NotBlank
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderEnumModel gender;

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date hireDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SalaryEntity> salaries;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TitleEntity> titles;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DepartmentEmployeeEntity> deptEmp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DepartmentManagerEntity> deptManager;

    public void setFirstName(String firstName){
        this.firstName = firstName.toLowerCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toLowerCase();;
    }

}
