package jp.co.axa.apidemo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "salary", "department"})
public class Employee {

    private String name;
    private Integer salary;
    private String department;

    public EmployeeEntity generateEntity() {
        return new EmployeeEntity(this);
    }

    public EmployeeEntity generateEntityWithId(Long employeeId) {
        return new EmployeeEntity(this, employeeId);
    }
}
