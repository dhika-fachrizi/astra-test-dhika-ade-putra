package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.DepartmentController;
import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.DepartmentManagerEntity;
import com.dhika.astralife.model.DepartmentManagerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@Slf4j
public class DepartmentManagerModelAssembler extends RepresentationModelAssemblerSupport<DepartmentManagerEntity, DepartmentManagerModel> {


    public DepartmentManagerModelAssembler() {
        super(EmployeeController.class, DepartmentManagerModel.class);
    }

    @Override
    public DepartmentManagerModel toModel(DepartmentManagerEntity entity) {

        if (entity != null){
            DepartmentManagerModel departmentManagerModel = instantiateModel(entity);

            //map entity to model
            departmentManagerModel.setFromDate(entity.getFromDate());
            departmentManagerModel.setToDate(entity.getToDate());

            // link
            departmentManagerModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetManager(entity.getEmployee().getEmployeeNo(),entity.getDepartment().getDepartmentNo()))
                    .withSelfRel());

            departmentManagerModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetEmployee(entity.getEmployee().getEmployeeNo()))
                    .withRel("employee"));

            departmentManagerModel.add(linkTo(
                    methodOn(DepartmentController.class)
                            .doGetDepartment(entity.getDepartment().getDepartmentNo()))
                    .withRel("department"));

            return departmentManagerModel;
        }else {
            return null;
        }


    }



    @Override
    public CollectionModel<DepartmentManagerModel> toCollectionModel(Iterable<? extends DepartmentManagerEntity> entities)
    {
        if (entities.iterator().hasNext()){
            CollectionModel<DepartmentManagerModel> departmentManagerModels = super.toCollectionModel(entities);

            departmentManagerModels.add(linkTo(methodOn(EmployeeController.class).doGetAllManager(entities.iterator().next().getEmployee().getEmployeeNo())).withSelfRel());

            return departmentManagerModels;
        }else {
            return null;
        }

    }
}
