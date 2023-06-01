package com.dhika.astralife.service.impl;

import com.dhika.astralife.entity.DepartmentEntity;
import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.exception.InvalidDataException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.DepartmentModel;
import com.dhika.astralife.repository.DepartmentRepository;
import com.dhika.astralife.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentEntity createDepartment(DepartmentModel request){

        //handle duplicate data
        if (departmentRepository.findById(request.getDepartmentNo()).orElse(null) != null)
            throw new InvalidDataException("data already exist");

        // map model to entity
        ModelMapper modelMapper = new ModelMapper();
        DepartmentEntity addDepartment = modelMapper.map(request, DepartmentEntity.class);
        DepartmentEntity department = departmentRepository.save(addDepartment);

        return department;
    }

    @Override
    public DepartmentEntity getDepartment(String idDept){
        DepartmentEntity department =  departmentRepository.findById(idDept).orElse(null);
        return department;
    }

    @Override
    public List<DepartmentEntity> getAllDepartment(){
        List<DepartmentEntity> departments = departmentRepository.getAllDepartment();
        return departments;
    }

    @Override
    public DepartmentEntity updateDepartment(String id, DepartmentModel request){
        DepartmentEntity department = checkDepartment(id);
        department.setDepartmentName(request.getDepartmentName());

        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(String id){
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentEntity checkDepartment(String id){
        DepartmentEntity department = departmentRepository.findById(id).orElse(null);
        if(department == null)
            throw new NotFoundException("department not found");
        else
            return department;
    }

}
