package jp.co.axa.apidemo.application;

import java.util.List;
import java.util.Random;

import jp.co.axa.apidemo.domain.Employee;
import jp.co.axa.apidemo.domain.EmployeeEntity;
import jp.co.axa.apidemo.domain.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void retrieveEmployees() {
        // setup
        Random random = new Random();
        int count = random.nextInt(1000);
        for (int i = 0; i < count; i++) {
            setupOfCreationEmployee();
        }

        // exercise
        List<EmployeeEntity> result = employeeService.retrieveEmployees();

        // verify
        assertEquals(count, result.size());
    }

    @Test
    public void getEmployee() {
        // setup
        EmployeeEntity createdEmployeeEntity = setupOfCreationEmployee();

        // exercise
        EmployeeEntity result = employeeService.getEmployee(createdEmployeeEntity.getId());

        // verify
        assertEquals(createdEmployeeEntity.getId(), result.getId());
        assertEquals(createdEmployeeEntity.getName(), result.getName());
        assertEquals(createdEmployeeEntity.getSalary(), result.getSalary());
        assertEquals(createdEmployeeEntity.getDepartment(), result.getDepartment());

        // teardown
        employeeService.deleteEmployee(result.getId());
    }

    @Test
    public void createEmployee() {
        // setup
        String name = "test name";
        Integer salary = 100;
        String department = "test department";
        Employee employee = new Employee(name, salary, department);

        // exercise
        EmployeeEntity result = employeeService.createEmployee(employee);

        // verify
        assertEquals(name, result.getName());
        assertEquals(salary, result.getSalary());
        assertEquals(department, result.getDepartment());

        // teardown
        employeeService.deleteEmployee(result.getId());
    }

    @Test
    public void updateEmployee() {
        // setup
        EmployeeEntity createdEmployeeEntity = setupOfCreationEmployee();

        String updatedName = "updated name";
        Integer updatedSalary = 200;
        String updatedDepartment = "updated department";
        Employee updatedEmployee = new Employee(updatedName, updatedSalary, updatedDepartment);

        // exercise
        EmployeeEntity result = employeeService.updateEmployee(updatedEmployee, createdEmployeeEntity.getId());

        // verify
        assertEquals(createdEmployeeEntity.getId(), result.getId());
        assertEquals(updatedName, result.getName());
        assertEquals(updatedSalary, result.getSalary());
        assertEquals(updatedDepartment, result.getDepartment());

        // teardown
        employeeService.deleteEmployee(result.getId());
    }

    @Test
    public void deleteEmployee() {
        // setup
        EmployeeEntity createdEmployeeEntity = setupOfCreationEmployee();

        // exercise
        employeeService.deleteEmployee(createdEmployeeEntity.getId());

        // verify
        try {
            employeeService.getEmployee(createdEmployeeEntity.getId());
        } catch (EmployeeNotFoundException e) {
            log.info("Created Employee was Deleted Successfully.");
        }
    }

    private EmployeeEntity setupOfCreationEmployee() {
        String name = "test name";
        Integer salary = 100;
        String department = "test department";
        Employee employee = new Employee(name, salary, department);

        return employeeService.createEmployee(employee);
    }

}