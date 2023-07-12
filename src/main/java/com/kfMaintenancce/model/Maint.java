package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity

@Table(name="machine_maint")

public class Maint implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="maint_id")
	private int maint_id;
	
	@Column(name="schedule_date")
	Date schedule_date;
	
	@Transient
	private List<MaintenenaceCheckPoint> checkpointlist;
	
	
	@Transient
	private String status;
	
	@Transient
	private int type;
	
	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	
	
	

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	@ManyToOne
	@JoinColumn(name="checklist_id")
	private Checklist checkpoint;
	
	
	public Checklist getCheckpoint() {
		return checkpoint;
	}


	public void setCheckpoint(Checklist checkpoint) {
		this.checkpoint = checkpoint;
	}


	public Date getSchedule_date() {
		return schedule_date;
	}


	public void setSchedule_date(Date schedule_date) {
		this.schedule_date = schedule_date;
	}
	
	@Transient
	private String action;
	
	

	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}

	@Transient
	private List<Maint> checklist;
	
//	@Transient
//	private List<Object> checklist;
	

	public List<Maint> getChecklist() {
		return checklist;
	}


	public void setChecklist(List<Maint> checklist) {
		this.checklist = checklist;
	}



	@Transient
	private String arr[];
	
	
	
	
	public String[] getArr() {
		return arr;
	}


	public void setArr(String[] arr) {
		this.arr = arr;
	}


	@Column(name="mode")
	private String 	mode;
	

	@Column(name="status_code")
	private int statusCode;
	
	
	
	@Column(name="frequency")
	private String 	frequency;
	
	@Column(name="overall_status")
	private String 	overall_status;
	
	@Column(name="done_by")
	private String 	done_by;
	

	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	

	@Transient
	private int s_id;
	
	public int getS_id() {
		return s_id;
	}


	public void setS_id(int s_id) {
		this.s_id = s_id;
	}


	public int getMaint_id() {
		return maint_id;
	}


	public void setMaint_id(int maint_id) {
		this.maint_id = maint_id;
	}


	

	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getOverall_status() {
		return overall_status;
	}


	public void setOverall_status(String overall_status) {
		this.overall_status = overall_status;
	}


	public String getDone_by() {
		return done_by;
	}


	public void setDone_by(String done_by) {
		this.done_by = done_by;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public List<MaintenenaceCheckPoint> getCheckpointlist() {
		return checkpointlist;
	}


	public void setCheckpointlist(List<MaintenenaceCheckPoint> checkpointlist) {
		this.checkpointlist = checkpointlist;
	}


	@Override
	public String toString() {
		return "Maint [maint_id=" + maint_id + ", status=" + status + ", statusCode=" + statusCode + ", overall_status="
				+ overall_status + ", done_by=" + done_by + ", machine=" + machine + "]";
	}

	
	

	
}
