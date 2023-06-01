package com.dhika.astralife.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "titles")
@IdClass(TitleId.class)
@Builder
public class TitleEntity {

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "emp_no",
            referencedColumnName = "emp_no"
    )
    @JsonBackReference
    private EmployeeEntity employee;

    @Id
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Id
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

}
