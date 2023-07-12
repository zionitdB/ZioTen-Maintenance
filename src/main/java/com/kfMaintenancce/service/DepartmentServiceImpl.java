package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.repo.DepartmentRepo;

@Service
public class DepartmentServiceImpl  implements DepartmentService{
	
	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public void addDepartment(Department department) {
		// TODO Auto-generated method stub
		departmentRepo.save(department);
	}

	@Override
	public void deletDepartment(Department department) {
		// TODO Auto-generated method stub
		departmentRepo.delete(department);
	}

	@Override
	public List<Department> getAllDepartments() {
		// TODO Auto-generated method stub
		return departmentRepo.findAll();
	}

}
