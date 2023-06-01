package com.dhika.astralife;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.DepartmentModel;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.GenderEnumModel;
import com.dhika.astralife.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@AllArgsConstructor
public class AstralifeApplication implements CommandLineRunner {

	private final EmployeeService employeeService;

	private final DepartmentService departmentService;

	private final SalaryService salaryService;

	private final TitleService titleService;

	private final DepartmentEmployeeService departmentEmployeeService;

	private final DepartmentManagerService departmentManagerService;

	public static void main(String[] args) {
		SpringApplication.run(AstralifeApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
		//initial Employee data

		if(employeeService.getAllEmployee().size() == 0){
			EmployeeModel employee = EmployeeModel.builder()
					.employeeNo(1)
					.firstName("dhika")
					.lastName("ade putra")
					.hireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"))
					.gender(GenderEnumModel.M)
					.birthDate(new SimpleDateFormat("yyyy-MM-dd").parse("1996-08-12"))
					.build();
			employeeService.createEmployee(employee);
		}

		if (departmentService.getAllDepartment().size() == 0){
			DepartmentModel departmentModel = DepartmentModel.builder()
					.departmentNo("AST1")
					.departmentName("IT Security")
					.build();
			departmentService.createDepartment(departmentModel);
		}



	}

}
