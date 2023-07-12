package com.kfMaintenancce.repo;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
