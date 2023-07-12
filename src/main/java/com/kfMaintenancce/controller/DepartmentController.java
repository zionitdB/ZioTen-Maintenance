package com.kfMaintenancce.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.service.DepartmentService;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;

	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addDepartment(@RequestBody Department department) {
		try {
			
			departmentService.addDepartment(department);
			return new Status("Consumption Device added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status changeStatus(@RequestBody Department department) {
		try {
			if (department.getActive()==1) {
				department.setActive(0);
			}else {
				department.setActive(1);
			}
			
			departmentService.addDepartment(department);
			return new Status("Department status changed Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/deletDepartment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deletDepartment(@RequestBody Department department) {
		try {
			
			departmentService.deletDepartment(department);
			return new Status("Department deleted  Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllDepartments", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Department> getAllDepartments() {
		List<Department> list = null;
		try {
			
			list = departmentService.getAllDepartments();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
