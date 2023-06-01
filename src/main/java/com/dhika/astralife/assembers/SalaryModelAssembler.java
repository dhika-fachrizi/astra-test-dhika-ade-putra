package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.SalaryEntity;
import com.dhika.astralife.model.SalaryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@Slf4j
public class SalaryModelAssembler extends RepresentationModelAssemblerSupport<SalaryEntity, SalaryModel> {


    public SalaryModelAssembler() {
        super(EmployeeController.class, SalaryModel.class);
    }

    @Override
    public SalaryModel toModel(SalaryEntity entity) {

        if (entity !=null){
            SalaryModel salaryModel = instantiateModel(entity);

            //map entity to model
            salaryModel.setSalary(entity.getSalary());
            salaryModel.setFromDate(entity.getFromDate());
            salaryModel.setToDate(entity.getToDate());

            // link
            salaryModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetSalaryEmployee(entity.getEmployee().getEmployeeNo()))
                    .withSelfRel());

            salaryModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetEmployee(entity.getEmployee().getEmployeeNo()))
                    .withRel("employee"));

            return salaryModel;
        }else {
            return null;
        }


    }



    @Override
    public CollectionModel<SalaryModel> toCollectionModel(Iterable<? extends SalaryEntity> entities)
    {
        if (entities.iterator().hasNext()){
            CollectionModel<SalaryModel> salaryModels = super.toCollectionModel(entities);

            for (SalaryModel iterator : salaryModels){
                iterator.removeLinks();
                iterator.add(linkTo(
                        methodOn(EmployeeController.class)
                                .doGetEmployee(entities.iterator().next().getEmployee().getEmployeeNo()))
                        .withRel("employee"));
            }

            salaryModels.add(linkTo(methodOn(EmployeeController.class).doGetAllSalaryEmployee(entities.iterator().next().getEmployee().getEmployeeNo())).withSelfRel());
            salaryModels.add(linkTo(methodOn(EmployeeController.class).doGetSalaryEmployee(entities.iterator().next().getEmployee().getEmployeeNo())).withRel("current"));


            return salaryModels;
        }else {
            return null;
        }

    }
}
