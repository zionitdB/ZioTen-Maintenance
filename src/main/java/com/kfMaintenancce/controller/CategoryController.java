package com.kfMaintenancce.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.BreakDownUpdateMobileApp;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.repo.CategoryRepo;
import com.kfMaintenancce.service.BreakdownServices;


@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryRepo categoryRepo;


	
	
	@GetMapping(value = "/list")
	public @ResponseBody
	List<Category> Category() {
		List<Category> list = null;
		try {
			
			list = categoryRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
}
