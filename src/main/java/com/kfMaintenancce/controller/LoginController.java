package com.kfMaintenancce.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.MobileLogin;
import com.kfMaintenancce.dto.MobileLoginRequest;
import com.kfMaintenancce.dto.MobileLoginResponce;
import com.kfMaintenancce.dto.ResponceObj;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.service.UserAndLoginService;



@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {	
	

	@Autowired
	UserAndLoginService loginService;
	

	@CrossOrigin(origins = "http://localhost:8083")
	@PostMapping("/authenticate")
	public ResponceObj serviceLogin(@RequestHeader("id") String id,
			@RequestHeader("password") String password) {
		ResponceObj  obj= new ResponceObj();
			System.out.println("id pass==="+id+password);
			
			
			Optional<UserDetails> optional = loginService.getUserById(id);
			
			if(optional.isPresent()){
				UserDetails details =optional.get();
				if(details.getPassword().equals(password)){
					obj.setCode(200);
					obj.setMessage("Login Successfully");
					obj.setData(details);
				}else {
					obj.setCode(500);
					obj.setMessage("Invalid Password");
				}
				
				
			}else{
				obj.setCode(500);
				obj.setMessage("Invalid UserId");
			}
			
			
			
		return obj;
			
		}
	@SuppressWarnings("unused")
	@PostMapping("/mobileLogin")
	public ResponseEntity<MobileLoginResponce> mobileLogin(@RequestBody MobileLogin mobileLogin) {
			System.out.println("id pass==="+mobileLogin.getUserId()+mobileLogin.getPassword());
			UserDetails returnbody = loginService.getUserByIdAndPassword(mobileLogin.getUserId(), mobileLogin.getPassword());
			MobileLoginResponce loginResponce = new MobileLoginResponce();
			
			System.out.println("User:"+returnbody.toString());
			String x=returnbody.getId();
			if(returnbody!=null){
				UserDetails user= new UserDetails();
				user.setFirstName(returnbody.getFirstName());
				user.setLastName(returnbody.getLastName());
				user.setId(returnbody.getId());
				user.setEmpId(returnbody.getEmpId());
				loginResponce.setStatusCode(200);
				loginResponce.setMessage("Login Successfully");
				loginResponce.setUser(user);
			}else{
				loginResponce.setStatusCode(500);
				loginResponce.setMessage("Login Failed");
			}
			/*user.setFirstName(returnbody.getFirstName());
			user.setLastName(user.getLastName());
			user.setId(x);
			user.setEmpId(returnbody.getEmpId());*/
			if (returnbody != null) {
			return new ResponseEntity<MobileLoginResponce>(loginResponce,HttpStatus.OK);
			}
			else {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
			}
		}
	
	}
