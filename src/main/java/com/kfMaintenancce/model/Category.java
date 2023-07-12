package com.kfMaintenancce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity

@Table(name="cat_mst")

public class Category implements Serializable {

	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cat_id;
	
	
	
	

	@Column(name="cat_name", length = 200)
	private String cat_name;
	
	
	@Column(name="deletes", length = 2)
	private int deletes;



	public int getCat_id() {
		return cat_id;
	}


	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}


	public int getDeletes() {
		return deletes;
	}


	public void setDeletes(int deletes) {
		this.deletes = deletes;
	}


	public String getCat_name() {
		return cat_name;
	}
	

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}


	

	
	

	
}
