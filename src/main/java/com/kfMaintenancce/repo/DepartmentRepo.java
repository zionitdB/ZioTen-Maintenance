package com.kfMaintenancce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.Department;

public interface DepartmentRepo  extends JpaRepository<Department, Integer>{

}
