package com.allianz.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.kafka.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String>{

}
