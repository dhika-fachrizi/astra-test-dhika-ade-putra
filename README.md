# astra-test-dhika-ade-putra Employee

- implement HTOAS
- Rest API Dedocumentation
- Exception Handling
- Unit Test

# Initial Config

| Tools             | Version |
|-------------------| ------------------- |
| spring-boot-3.1.0 | 2.3.7-RELEASE       |
| MySql             | 10.4.11-MariaDB       |

please modify the database connection pint to your database

```application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_astralife
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
```

# How To Run
- RUN YOU DATABASE SERVICE 
- Run 
```mvn
mvnw spring-boot:run
```

# Rest API Documentation
[https://documenter.getpostman.com/view/26705744/2s93mBwJbB](https://documenter.getpostman.com/view/26705744/2s93mBwJbB)

# Init Data 
Init data when running the application
## Employee

```json
{
    "employeeNo": 1,
    "firstName": "dhika",
    "lastName": "ade putra",
    "gender": "M",
    "hireDate": "2022-12-31T17:00:00.000+00:00",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8083/employee/1"
        },
        {
            "rel": "salary",
            "href": "http://localhost:8083/employee/1/salary"
        },
        {
            "rel": "title",
            "href": "http://localhost:8083/employee/1/title"
        },
        {
            "rel": "department",
            "href": "http://localhost:8083/employee/1/department/all"
        },
        {
            "rel": "manager",
            "href": "http://localhost:8083/employee/1/manager/all"
        }
    ]
}

```

## Department
```json
{
    "departmentNo": "A001",
    "departmentName": "it security",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8083/department/A001"
        },
        {
            "rel": "department_employee",
            "href": "http://localhost:8083/employee/1/department/A001"
        },
        {
            "rel": "department_manager",
            "href": "http://localhost:8083/employee/1/manager/A001"
        }
    ]
}

```
