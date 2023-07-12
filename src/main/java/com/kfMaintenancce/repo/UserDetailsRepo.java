package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	@Query("From UserDetails u where u.id=?1")
	Optional<UserDetails> getUserById(String id);

}
