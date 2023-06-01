package com.dhika.astralife.assembers;

import com.dhika.astralife.controller.EmployeeController;
import com.dhika.astralife.entity.TitleEntity;
import com.dhika.astralife.model.TitleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@Slf4j
public class TitleModelAssembler extends RepresentationModelAssemblerSupport<TitleEntity, TitleModel> {


    public TitleModelAssembler() {
        super(EmployeeController.class, TitleModel.class);
    }

    @Override
    public TitleModel toModel(TitleEntity entity) {

        if (entity !=null){
            TitleModel titleModel = instantiateModel(entity);

            //map entity to model
            titleModel.setTitle(entity.getTitle());
            titleModel.setFromDate(entity.getFromDate());
            titleModel.setToDate(entity.getToDate());

            // link
            titleModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetTitleEmployee(entity.getEmployee().getEmployeeNo()))
                    .withSelfRel());

            titleModel.add(linkTo(
                    methodOn(EmployeeController.class)
                            .doGetEmployee(entity.getEmployee().getEmployeeNo()))
                    .withRel("employee"));

            return titleModel;
        }else {
            return null;
        }


    }



    @Override
    public CollectionModel<TitleModel> toCollectionModel(Iterable<? extends TitleEntity> entities)
    {
        if (entities.iterator().hasNext()){
            CollectionModel<TitleModel> titleModels = super.toCollectionModel(entities);

            for (TitleModel iterator : titleModels){
                iterator.removeLinks();
                iterator.add(linkTo(
                        methodOn(EmployeeController.class)
                                .doGetEmployee(entities.iterator().next().getEmployee().getEmployeeNo()))
                        .withRel("employee"));
            }

            titleModels.add(linkTo(methodOn(EmployeeController.class).doGetAllTitleEmployee(entities.iterator().next().getEmployee().getEmployeeNo())).withSelfRel());
            titleModels.add(linkTo(methodOn(EmployeeController.class).doGetTitleEmployee(entities.iterator().next().getEmployee().getEmployeeNo())).withRel("current"));

            return titleModels;
        }else {
            return null;
        }

    }

}
