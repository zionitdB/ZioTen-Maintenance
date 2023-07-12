package com.kfMaintenancce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.service.UserAndLoginService;




@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserAndLoginService loginService;
	
	@GetMapping("/getAlluser")
	public ResponseEntity<List<UserDetails>> getUsers() {
		
		List<UserDetails> allUsers =	loginService.getUsers();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}



	}
	
	

