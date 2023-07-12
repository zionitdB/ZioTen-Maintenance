package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import com.kfMaintenancce.model.UserDetails;

public interface UserAndLoginService {

	UserDetails getUserByIdAndPassword(String id, String password);

	Optional<UserDetails> getUserById(String id);

	List<UserDetails> getUsers();

}
