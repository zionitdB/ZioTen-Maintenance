package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.UserDetailsRepo;

@Service
public class UserAndLoginServiceImpl implements UserAndLoginService {
	@Autowired
	UserDetailsRepo userDetailsRepo;

	@Override
	public UserDetails getUserByIdAndPassword(String id, String password) {
		// TODO Auto-generated method stub
		Optional<UserDetails> optional = userDetailsRepo.getUserById(id);
		if(optional.isPresent()){
			
			
			if(optional.get().getPassword().equals(password)){
				return optional.get();
			}else{
				
			}return null;
			
		}else{
			return null;	
		}
		
	}

	@Override
	public Optional<UserDetails> getUserById(String id) {
		// TODO Auto-generated method stub
		return userDetailsRepo.getUserById(id);
	}

	@Override
	public List<UserDetails> getUsers() {
		// TODO Auto-generated method stub
		return userDetailsRepo.findAll();
	}

}
