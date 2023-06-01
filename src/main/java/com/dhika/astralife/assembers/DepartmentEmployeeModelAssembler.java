package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.DepartmentController;
import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.DepartmentEmployeeEntity;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.model.DepartmentEmployeeModel;
import com.dhika.astralife.model.SalaryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@Slf4j
public class DepartmentEmployeeModelAssembler extends RepresentationModelAssemblerSupport<DepartmentEmployeeEntity, DepartmentEmployeeModel> {


    public DepartmentEmployeeModelAssembler() {
        super(EmployeeController.class, DepartmentEmployeeModel.class);
    }

    @Override
    public DepartmentEmployeeModel toModel(DepartmentEmployeeEntity entity) {

        if (entity != null){
            DepartmentEmployeeModel departmentEmployeeModel = instantiateModel(entity);

            //map entity to model
            departmentEmployeeModel.setFromDate(entity.getFromDate());
            departmentEmployeeModel.setToDate(entity.getToDate());

            // link
            departmentEmployeeModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetDepartment(entity.getEmployee().getEmployeeNo(),entity.getDepartment().getDepartmentNo()))
                    .withSelfRel());

            departmentEmployeeModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetEmployee(entity.getEmployee().getEmployeeNo()))
                    .withRel("employee"));

            departmentEmployeeModel.add(linkTo(
                    methodOn(DepartmentController.class)
                            .doGetDepartment(entity.getDepartment().getDepartmentNo()))
                    .withRel("department"));

            return departmentEmployeeModel;
        }else {
            return null;
        }


    }



    @Override
    public CollectionModel<DepartmentEmployeeModel> toCollectionModel(Iterable<? extends DepartmentEmployeeEntity> entities)
    {
        if (entities.iterator().hasNext()){
            CollectionModel<DepartmentEmployeeModel> departmentEmployeeModels = super.toCollectionModel(entities);

            departmentEmployeeModels.add(linkTo(methodOn(EmployeeController.class).doGetAllDepartment(entities.iterator().next().getEmployee().getEmployeeNo())).withSelfRel());

            return departmentEmployeeModels;
        }else {
            return null;
        }

    }
}
