package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.DepartmentController;
import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.model.DepartmentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
public class DepartmentModelAssembler extends RepresentationModelAssemblerSupport<DepartmentEntity, DepartmentModel> {

    public DepartmentModelAssembler() {
        super(DepartmentController.class, DepartmentModel.class);
    }

    @Override
    public DepartmentModel toModel(DepartmentEntity entity) {

        if (entity != null) {
            DepartmentModel departmentModel = instantiateModel(entity);

            // map entity to model
            departmentModel.setDepartmentNo(entity.getDepartmentNo());
            departmentModel.setDepartmentName(entity.getDepartmentName());

            // link
            departmentModel.add(linkTo(
                    methodOn(DepartmentController.class)
                            .doGetDepartment(entity.getDepartmentNo()))
                    .withSelfRel());

            if (entity.getDepEmp() != null) {
                if (entity.getDepEmp().size() > 0) {
                    departmentModel.add(linkTo(
                            methodOn(EmployeeController.class)
                                    .doGetDepartment(entity.getDepEmp().get(0).getEmployee().getEmployeeNo(), entity.getDepartmentNo()))
                            .withRel("department_employee"));
                }
            }

            if (entity.getDeptManager() != null) {
                if (entity.getDeptManager().size() > 0) {
                    departmentModel.add(linkTo(
                            methodOn(EmployeeController.class)
                                    .doGetManager(entity.getDeptManager().get(0).getEmployee().getEmployeeNo(), entity.getDepartmentNo()))
                            .withRel("department_manager"));
                }
            }
            return departmentModel;
        }else {
            return null;
        }

    }

    @Override
    public CollectionModel<DepartmentModel> toCollectionModel(Iterable<? extends DepartmentEntity> entities)
    {
        if (entities.iterator().next() != null){
            CollectionModel<DepartmentModel> departmentModels = super.toCollectionModel(entities);

            departmentModels.add(linkTo(methodOn(DepartmentController.class).doGetAllDepartment()).withSelfRel());
            return departmentModels;
        }else {
            return null;
        }

    }
}
