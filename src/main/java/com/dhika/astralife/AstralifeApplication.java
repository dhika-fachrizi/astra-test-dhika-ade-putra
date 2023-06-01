package com.dhika.astralife;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.*;
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

		if(employeeService.getAllEmployee().size() == 0 && departmentService.getAllDepartment().size() == 0){
			EmployeeModel employee = EmployeeModel.builder()
					.employeeNo(1)
					.firstName("dhika")
					.lastName("ade putra")
					.hireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"))
					.gender(GenderEnumModel.M)
					.birthDate(new SimpleDateFormat("yyyy-MM-dd").parse("1996-08-12"))
					.build();
			employeeService.createEmployee(employee);

			SalaryModel salaryModel = SalaryModel.builder()
					.salary(10000000)
					.fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-28"))
					.toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-03-28"))
					.build();
			salaryService.createSalaryByEmployee(employee.getEmployeeNo(),salaryModel);

			TitleModel titleModel = TitleModel.builder()
					.title("Senior Programmer")
					.fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-28"))
					.toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-28"))
					.build();
			titleService.createTitleByEmployee(employee.getEmployeeNo(),titleModel);

			DepartmentModel departmentModel = DepartmentModel.builder()
					.departmentNo("A001")
					.departmentName("IT Security")
					.build();
			departmentService.createDepartment(departmentModel);

			DepartmentModel departmentModel2 = DepartmentModel.builder()
					.departmentNo("A002")
					.departmentName("IT Security")
					.build();
			departmentService.createDepartment(departmentModel2);

			DepartmentManagerModel departmentManagerModel = DepartmentManagerModel.builder()
					.fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-28"))
					.toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-28"))
					.build();

			departmentManagerService.createDepartmentManager(employee.getEmployeeNo()
					, departmentModel.getDepartmentNo()
					, departmentManagerModel);

			DepartmentEmployeeModel departmentEmployeeModel = DepartmentEmployeeModel.builder()
					.fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-28"))
					.toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-28"))
					.build();

			departmentEmployeeService.createDepartmentEmployee(employee.getEmployeeNo()
					, departmentModel.getDepartmentNo()
					, departmentEmployeeModel);
		}




	}

}
