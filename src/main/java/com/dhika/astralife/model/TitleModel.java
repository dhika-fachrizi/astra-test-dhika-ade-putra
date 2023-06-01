package com.dhika.astralife.model;

import com.dhika.astralife.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "title", itemRelation = "title")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TitleModel extends RepresentationModel<TitleModel> {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private EmployeeEntity employee;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z ]{3,50}$",
            message = "title must be 50 length and no special characters")
    private String title;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fromDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date toDate;
}
