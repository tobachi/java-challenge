package jp.co.axa.apidemo.application;

import jp.co.axa.apidemo.domain.Employee;
import jp.co.axa.apidemo.domain.EmployeeEntity;
import jp.co.axa.apidemo.domain.EmployeeNotFoundException;
import jp.co.axa.apidemo.domain.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeEntity> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeEntity getEmployee(Long employeeId) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> optEmp = employeeRepository.findById(employeeId);
        try {
            return optEmp.get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            throw new EmployeeNotFoundException(employeeId);
        }
    }

    public EmployeeEntity createEmployee(Employee employee){
        EmployeeEntity result = employeeRepository.save(employee.generateEntity());
        log.info("Employee ID " + String.valueOf(result.getId()) + " was Created Successfully");
        return result;
    }

    public EmployeeEntity updateEmployee(Employee employee, Long employeeId) {
        getEmployee(employeeId);
        EmployeeEntity result = employeeRepository.save(employee.generateEntityWithId(employeeId));
        log.info("Employee ID " + String.valueOf(employeeId) + " was Updated Successfully");
        return result;
    }

    public void deleteEmployee(Long employeeId){
        try {
            employeeRepository.deleteById(employeeId);
            log.info("Employee ID " + String.valueOf(employeeId) + " was Deleted Successfully");
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new EmployeeNotFoundException(employeeId);
        }
    }

}