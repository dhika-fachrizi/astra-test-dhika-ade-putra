package com.dhika.astralife.controller;

import com.dhika.astralife.assembers.*;
import com.dhika.astralife.entity.*;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.*;
import com.dhika.astralife.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final SalaryService salaryService;

    private final TitleService titleService;

    private final DepartmentEmployeeService departmentEmployeeService;

    private final DepartmentManagerService departmentManagerService;

    @Autowired
    private EmployeeModelAssembler employeeModelAssembler;

    @Autowired
    private SalaryModelAssembler salaryModelAssembler;

    @Autowired
    private TitleModelAssembler titleModelAssembler;

    @Autowired
    private DepartmentEmployeeModelAssembler departmentEmployeeModelAssembler;

    @Autowired
    private DepartmentManagerModelAssembler departmentManagerModelAssembler;


    //    employee endpoint start
    @GetMapping("/{idEmp}")
    public ResponseModel<EmployeeModel> doGetEmployee(@PathVariable("idEmp") Integer idEmp) {

        EmployeeEntity employee =  employeeService.getEmployee(idEmp);

        return ResponseModel.<EmployeeModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(employeeModelAssembler.toModel(employee))
                .build();
    }

    @GetMapping("all")
    public ResponseModel<CollectionModel<EmployeeModel>> doGetAllEmployee() {

        List<EmployeeEntity> employee =  employeeService.getAllEmployee();

        return ResponseModel.<CollectionModel<EmployeeModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(employeeModelAssembler.toCollectionModel(employee))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<EmployeeModel> doAddEmployee(@Valid @RequestBody EmployeeModel request){
        EmployeeEntity employee = employeeService.createEmployee(request);

        return ResponseModel.<EmployeeModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(employeeModelAssembler.toModel(employee))
                .build();
    }

    @PatchMapping("/{idEmp}")
    public ResponseModel<EmployeeModel> doUpdateEmployee(@PathVariable("idEmp") Integer idEmp,@Valid @RequestBody EmployeeModel request) {

        EmployeeEntity employee = employeeService.updateEmployee(idEmp, request);

        return ResponseModel.<EmployeeModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(employeeModelAssembler.toModel(employee))
                .build();
    }

    @DeleteMapping("/{idEmp}")
    public ResponseModel<EmployeeModel> doDeleteEmployee(@PathVariable("idEmp") Integer idEmp) {
        employeeService.deleteEmployee(idEmp);
        return ResponseModel.<EmployeeModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
    //    employee endpoint end

    //    employee and salary endpoint start
    @GetMapping("/{idEmp}/salary")
    public ResponseModel<SalaryModel> doGetSalaryEmployee(@PathVariable("idEmp") Integer idEmp) {

        SalaryEntity salary =  salaryService.getCurrentSalaryByEmployee(idEmp);

        return ResponseModel.<SalaryModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(salaryModelAssembler.toModel(salary))
                .build();
    }

    @GetMapping("/{idEmp}/salary/all")
    public ResponseModel<CollectionModel<SalaryModel>> doGetAllSalaryEmployee(@PathVariable("idEmp") Integer id) {

        List<SalaryEntity> salaries =  salaryService.getSalariesByEmployee(id);
        return ResponseModel.<CollectionModel<SalaryModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(salaryModelAssembler.toCollectionModel(salaries))
                .build();
    }

    @PostMapping("/{idEmp}/salary")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseModel<SalaryModel> doAddSalaryEmployee(@PathVariable("idEmp") Integer idEmp
            ,@Valid @RequestBody SalaryModel request){

        SalaryEntity currentSalary = salaryService.getCurrentSalaryByEmployee(idEmp);

        if(currentSalary != null){
            if (currentSalary.getToDate() == null){
                // update update latest toDate if null
                SalaryModel salaryModelForCurrent = SalaryModel.builder()
                        .toDate(request.getFromDate())
                        .salary(currentSalary.getSalary())
                        .build();

                salaryService.updateSalaryByEmployee(idEmp, salaryModelForCurrent);
            } else {
                // validate new salary toDate
                if (request.getFromDate().before(currentSalary.getToDate()))
                    throw new InvalidDateException("from date must higher then latest to date");
            }
        }
        SalaryEntity salary = salaryService.createSalaryByEmployee(idEmp,request);

        return ResponseModel.<SalaryModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(salaryModelAssembler.toModel(salary))
                .build();
    }

    @PatchMapping("/{idEmp}/salary")
    public ResponseModel<SalaryModel> doUpdateCurrentSalaryEmployee(@PathVariable("idEmp") Integer idEmp, @RequestBody SalaryModel request) {

        SalaryEntity salary = salaryService.updateSalaryByEmployee(idEmp, request);

        return ResponseModel.<SalaryModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(salaryModelAssembler.toModel(salary))
                .build();
    }

    @DeleteMapping("/{idEmp}/salary")
    public ResponseModel<SalaryModel> doDeleteCurrentSalaryEmployee(@PathVariable("idEmp") Integer idEmp)  {

        salaryService.deleteCurrentSalary(idEmp);

        return ResponseModel.<SalaryModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
    //    employee and salary endpoint start

    //    employee and title endpoint start
    @GetMapping("/{idEmp}/title")
    public ResponseModel<TitleModel> doGetTitleEmployee(@PathVariable("idEmp") Integer idEmp) {

        TitleEntity title =  titleService.getCurrentTitleByEmployee(idEmp);

        return ResponseModel.<TitleModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(titleModelAssembler.toModel(title))
                .build();
    }

    @GetMapping("/{idEmp}/title/all")
    public ResponseModel<CollectionModel<TitleModel>> doGetAllTitleEmployee(@PathVariable("idEmp") Integer idEmp) {

        List<TitleEntity> titles =  titleService.getTitlesByEmployee(idEmp);
        return ResponseModel.<CollectionModel<TitleModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(titleModelAssembler.toCollectionModel(titles))
                .build();
    }

    @PostMapping("/{idEmp}/title")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseModel<TitleModel> doAddTitleEmployee(@PathVariable("idEmp") Integer idEmp
            ,@Valid @RequestBody TitleModel request){

        TitleEntity currentTitle = titleService.getCurrentTitleByEmployee(idEmp);

        if(currentTitle != null){
            if (currentTitle.getToDate() == null){
                // update update latest toDate if null
                TitleModel titleModelForCurrent = TitleModel.builder()
                        .toDate(request.getFromDate())
                        .build();

                titleService.updateTitleByEmployee(idEmp, titleModelForCurrent);
            } else {
                // validate new title toDate
                if (request.getFromDate().before(currentTitle.getToDate()))
                    throw new InvalidDateException("from date must higher then latest to date");
            }
        }
        TitleEntity title = titleService.createTitleByEmployee(idEmp,request);

        return ResponseModel.<TitleModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(titleModelAssembler.toModel(title))
                .build();
    }

    @PatchMapping("/{idEmp}/title")
    public ResponseModel<TitleModel> doUpdateCurrentTitleEmployee(@PathVariable("idEmp") Integer idEmp, @RequestBody TitleModel request) {

        TitleEntity title = titleService.updateTitleByEmployee(idEmp, request);

        return ResponseModel.<TitleModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(titleModelAssembler.toModel(title))
                .build();
    }

    @DeleteMapping("/{idEmp}/title")
    public ResponseModel<TitleModel> doDeleteCurrentTitleEmployee(@PathVariable("idEmp") Integer idEmp)  {

        titleService.deleteCurrentTitle(idEmp);

        return ResponseModel.<TitleModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
    //    employee and title endpoint end

    //    employee and manager endpoint start
    @GetMapping("/{idEmp}/manager/{idDept}")
    public ResponseModel<DepartmentManagerModel> doGetManager(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept) {

        DepartmentManagerEntity departmentManager = departmentManagerService.getDepartmentManager(idEmp, idDept);

        return ResponseModel.<DepartmentManagerModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentManagerModelAssembler.toModel(departmentManager))
                .build();
    }

    @GetMapping("/{idEmp}/manager/all")
    public ResponseModel<CollectionModel<DepartmentManagerModel>> doGetAllManager(@PathVariable("idEmp") Integer idEmp) {

        List<DepartmentManagerEntity> allDepartmentManager =  departmentManagerService.getAllDepartmentManager(idEmp);

        return ResponseModel.<CollectionModel<DepartmentManagerModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentManagerModelAssembler.toCollectionModel(allDepartmentManager))
                .build();
    }

    @PostMapping("/{idEmp}/manager/{idDept}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DepartmentManagerModel> doCreateManager(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept, @Valid @RequestBody DepartmentManagerModel request){
        DepartmentManagerEntity departmentManager = departmentManagerService.createDepartmentManager(idEmp,idDept,request);

        return ResponseModel.<DepartmentManagerModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(departmentManagerModelAssembler.toModel(departmentManager))
                .build();
    }

    @PatchMapping("/{idEmp}/manager/{idDept}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<DepartmentManagerModel> doUpdateManager(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept, @Valid @RequestBody DepartmentManagerModel request){
        DepartmentManagerEntity departmentManager = departmentManagerService.updateDepartmentManager(idEmp, idDept, request);

        return ResponseModel.<DepartmentManagerModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentManagerModelAssembler.toModel(departmentManager))
                .build();
    }

    @DeleteMapping("/{idEmp}/manager/{idDept}")
    public ResponseModel<DepartmentManagerEntity> doDeleteManager(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept) {
        departmentManagerService.deleteDepartmentManager(idEmp, idDept);
        return ResponseModel.<DepartmentManagerEntity>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
    //    employee and manager endpoint and

    //    employee and department endpoint and
    @GetMapping("/{idEmp}/department/{idDept}")
    public ResponseModel<DepartmentEmployeeModel> doGetDepartment(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept) {

        DepartmentEmployeeEntity departmentEmployee = departmentEmployeeService.getDepartmentEmployee(idEmp, idDept);

        return ResponseModel.<DepartmentEmployeeModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentEmployeeModelAssembler.toModel(departmentEmployee))
                .build();
    }

    @GetMapping("/{idEmp}/department/all")
    public ResponseModel<CollectionModel<DepartmentEmployeeModel>> doGetAllDepartment(@PathVariable("idEmp") Integer idEmp) {

        List<DepartmentEmployeeEntity> allDepartmentEmployee =  departmentEmployeeService.getAllDepartmentEmployee(idEmp);

        return ResponseModel.<CollectionModel<DepartmentEmployeeModel>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentEmployeeModelAssembler.toCollectionModel(allDepartmentEmployee))
                .build();
    }

    @PostMapping("/{idEmp}/department/{idDept}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DepartmentEmployeeModel> doCreateDepartment(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept, @Valid @RequestBody DepartmentEmployeeModel request){
        DepartmentEmployeeEntity departmentEmployee = departmentEmployeeService.createDepartmentEmployee(idEmp,idDept,request);

        return ResponseModel.<DepartmentEmployeeModel>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(departmentEmployeeModelAssembler.toModel(departmentEmployee))
                .build();
    }

    @PatchMapping("/{idEmp}/department/{idDept}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<DepartmentEmployeeModel> doUpdateDepartment(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept, @Valid @RequestBody DepartmentEmployeeModel request){
        DepartmentEmployeeEntity departmentEmployee = departmentEmployeeService.updateDepartmentEmployee(idEmp, idDept, request);

        return ResponseModel.<DepartmentEmployeeModel>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(departmentEmployeeModelAssembler.toModel(departmentEmployee))
                .build();
    }

    @DeleteMapping("/{idEmp}/department/{idDept}")
    public ResponseModel<DepartmentEmployeeEntity> doDeleteDepartment(@PathVariable("idEmp") Integer idEmp
            , @PathVariable("idDept") String idDept) {
        departmentEmployeeService.deleteDepartmentEmployee(idEmp, idDept);
        return ResponseModel.<DepartmentEmployeeEntity>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
}
