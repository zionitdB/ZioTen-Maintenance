package com.kfMaintenancce.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity

@Table(name="checklist_mst")

public class Checklist implements Serializable {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int checklist_id;
	
	@Column(name="task")
	private String task;
	
	@Column(name="operation")
	private String operation;
	
	@Column(name="acceptable_range")
	private String acceptableRange;
	
	@Transient
	String machineName;

	public String getAcceptableRange() {
		return acceptableRange;
	}

	public void setAcceptableRange(String acceptableRange) {
		this.acceptableRange = acceptableRange;
	}

	@Transient
	private int checkpointId;
	
	public int getCheckpointId() {
		return checkpointId;
	}

	public void setCheckpointId(int checkpointId) {
		this.checkpointId = checkpointId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name="frequency")
	private String frequency;
	

	/*@Transient
	private String arr[];
	*/


	
	
	
	@Column(name="type")
	private String type;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	

	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public int getChecklist_id() {
		return checklist_id;
	}

	public void setChecklist_id(int checklist_id) {
		this.checklist_id = checklist_id;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}


@Transient
	private List<Checklist> checklist;



public List<Checklist> getChecklist() {
	return checklist;
}

public void setChecklist(List<Checklist> checklist) {
	this.checklist = checklist;
}

public String getMachineName() {
	return machineName;
}

public void setMachineName(String machineName) {
	this.machineName = machineName;
}






}
