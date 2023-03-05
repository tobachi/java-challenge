package jp.co.axa.apidemo.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class EmployeeTest {

    @Test
    public void generateEntity() {
        // setup
        String name = "test name";
        Integer salary = 100;
        String department = "test department";
        Employee employee = new Employee(name, salary, department);

        // exercise
        EmployeeEntity result = employee.generateEntity();

        // verify
        assertNull(result.getId());
        assertEquals(result.getName(), name);
        assertEquals(result.getSalary(), salary);
        assertEquals(result.getDepartment(), department);
    }

    @Test
    public void generateEntityWithId() {
        // setup
        Long id = 1000000L;
        String name = "test name";
        Integer salary = 100;
        String department = "test department";
        Employee employee = new Employee(name, salary, department);

        // exercise
        EmployeeEntity result = employee.generateEntityWithId(id);

        // verify
        assertEquals(result.getId(), id);
        assertEquals(result.getName(), name);
        assertEquals(result.getSalary(), salary);
        assertEquals(result.getDepartment(), department);
    }

}
