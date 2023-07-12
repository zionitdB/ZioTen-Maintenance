package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Department;

public interface DepartmentService {

	void addDepartment(Department department);

	void deletDepartment(Department department);

	List<Department> getAllDepartments();

}
