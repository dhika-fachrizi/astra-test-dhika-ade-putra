package com.dhika.astralife.controller;

import com.dhika.astralife.assembers.DepartmentModelAssembler;
import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.DepartmentModel;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.ResponseModel;
import com.dhika.astralife.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    private DepartmentModelAssembler departmentModelAssembler;

    @GetMapping("/{idDept}")
    public ResponseModel<DepartmentModel> doGetDepartment(@PathVariable("idDept") String idDept) {

        DepartmentEntity department =  departmentService.getDepartment(idDept);

        return ResponseModel.<DepartmentModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentModelAssembler.toModel(department))
                .build();
    }

    @GetMapping("/all")
    public ResponseModel<CollectionModel<DepartmentModel>> doGetAllDepartment() {

        Collection<DepartmentEntity> departments =  departmentService.getAllDepartment();

        return ResponseModel.<CollectionModel<DepartmentModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentModelAssembler.toCollectionModel(departments))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DepartmentModel> doCreateDepartment(@Valid @RequestBody DepartmentModel request){
        DepartmentEntity department = departmentService.createDepartment(request);

        return ResponseModel.<DepartmentModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(departmentModelAssembler.toModel(department))
                .build();
    }

    @PatchMapping("/{idDept}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<DepartmentModel> doUpdateDepartment(@RequestBody DepartmentModel request
    ,@PathVariable("idDept") String idDept){
        DepartmentEntity department = departmentService.updateDepartment(idDept, request);

        return ResponseModel.<DepartmentModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentModelAssembler.toModel(department))
                .build();
    }

    @DeleteMapping("/{idDept}")
    public ResponseModel<DepartmentModel> doDeleteEmployee(@PathVariable("idDept") String idDept) {
        departmentService.deleteDepartment(idDept);
        return ResponseModel.<DepartmentModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
}
