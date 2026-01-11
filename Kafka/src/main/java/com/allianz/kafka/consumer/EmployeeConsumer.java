package com.allianz.kafka.consumer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.allianz.kafka.misc.EmployeeEntity;
import com.allianz.kafka.repository.EmployeeRepository;
import com.eaton.kafka.confluent.employee.Employee;

import jakarta.transaction.Transactional;

@Service
public class EmployeeConsumer {


    private final EmployeeRepository repository;

    public EmployeeConsumer(EmployeeRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
        topics = "employee",
        groupId = "employee-db-writer-1",
        containerFactory = "employeeKafkaListenerContainerFactory"
    )
    @Transactional
    public void consume(Employee employee) {

        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployeeId(employee.getEmployeeId().toString());
        entity.setFullName(employee.getFullName().toString());
        entity.setDepartmentName(employee.getDepartmentName().toString());
        entity.setWorkEmail(employee.getWorkEmail().toString());
        entity.setEmploymentStatus(employee.getEmploymentStatus().toString());
        entity.setHireDate(employee.getHireDate().toString());
        entity.setBaseSalary(employee.getSalaryDetails().getBaseSalary());
        entity.setCurrency(employee.getSalaryDetails().getCurrency().toString());

        //repository.save(entity);
        try {
            repository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            System.out.println("Duplicate employee, skipping");
        }
    }
}
