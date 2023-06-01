package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.DepartmentController;
import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.DepartmentModel;
import com.dhika.astralife.model.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<EmployeeEntity, EmployeeModel> {
    public EmployeeModelAssembler() {
        super(EmployeeController.class, EmployeeModel.class);
    }

    @Override
    public EmployeeModel toModel(EmployeeEntity entity) {

        if (entity != null){
        EmployeeModel employeeModel = instantiateModel(entity);

        // map entity to model
        employeeModel.setEmployeeNo(entity.getEmployeeNo());
        employeeModel.setGender(entity.getGender());
        employeeModel.setFirstName(entity.getFirstName());
        employeeModel.setLastName(entity.getLastName());
        employeeModel.setHireDate(entity.getHireDate());

        // link
        employeeModel.add(linkTo(
                methodOn(EmployeeController.class)
                        .doGetEmployee(entity.getEmployeeNo()))
                .withSelfRel());

        if (entity.getSalaries() != null) {
            if (entity.getSalaries().size() > 0) {
                employeeModel.add(linkTo(
                        methodOn(EmployeeController.class)
                                .doGetSalaryEmployee(entity.getEmployeeNo()))
                        .withRel("salary"));
            }
        }


         if (entity.getTitles() != null) {
             if (entity.getTitles().size() > 0) {
                 employeeModel.add(linkTo(
                         methodOn(EmployeeController.class)
                                 .doGetTitleEmployee(entity.getEmployeeNo()))
                         .withRel("title"));
             }
         }

         if (entity.getDeptEmp() != null) {
             if (entity.getDeptEmp().size() > 0){
                 employeeModel.add(linkTo(
                         methodOn(EmployeeController.class)
                                 .doGetAllDepartment(entity.getEmployeeNo()))
                         .withRel("department"));
             }
         }

         if (entity.getDeptManager() != null) {
             if (entity.getDeptManager().size() > 0){
                 employeeModel.add(linkTo(
                         methodOn(EmployeeController.class)
                                 .doGetAllManager(entity.getEmployeeNo()))
                         .withRel("manager"));
            }
         }

            return employeeModel;
        }else {
            return null;
        }
    }

    @Override
    public CollectionModel<EmployeeModel> toCollectionModel(Iterable<? extends EmployeeEntity> entities)
    {
        if (entities.iterator().next() != null){
            CollectionModel<EmployeeModel> employeeModels = super.toCollectionModel(entities);

            employeeModels.add(linkTo(methodOn(EmployeeController.class).doGetAllEmployee()).withSelfRel());
            return employeeModels;
        }else {
            return null;
        }

    }
}
