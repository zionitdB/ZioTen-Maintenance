package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.Date;

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

@Table(name="breakdown_tx")

public class Breakdown implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int breakdown_id;
	
	
	

	@Column(name="status")
	private int status;
	
	@Column(name="spares_used", length = 2000)
	private String spares_used;
	
	public String getSpares_used() {
		return spares_used;
	}


	public void setSpares_used(String spares_used) {
		this.spares_used = spares_used;
	}
	
	
	@Column(name="ticket_closed_flag")
	private int ticket_closed_flag;
	
	
	@Column(name="tc_tr_hr")
	private double tc_tr_hr;
	
	
	@Column(name="total_trial_hr")
	private double total_trial_hr;
	
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	
	
	public int getTicket_closed_flag() {
		return ticket_closed_flag;
	}


	public void setTicket_closed_flag(int ticket_closed_flag) {
		this.ticket_closed_flag = ticket_closed_flag;
	}
	

	public double getTc_tr_hr() {
		return tc_tr_hr;
	}


	public void setTc_tr_hr(double tc_tr_hr) {
		this.tc_tr_hr = tc_tr_hr;
	}


	public double getTotal_trial_hr() {
		return total_trial_hr;
	}


	public void setTotal_trial_hr(double total_trial_hr) {
		this.total_trial_hr = total_trial_hr;
	}


	@Column(name="done_by", length = 200)
	private String done_by;
	
	
	@Column(name="action_taken", length = 2000)
	private String action_taken;
	

	@Column(name="engg_finding", length = 1000)
	private String engg_finding;
	
	@Column(name="root_cause", length = 2000)
	private String root_cause;
	
	@Column(name="prev_action_plan", length = 2000)
	private String prev_action_plan;
	
	
	
	public String getRoot_cause() {
		return root_cause;
	}


	public void setRoot_cause(String root_cause) {
		this.root_cause = root_cause;
	}


	public String getPrev_action_plan() {
		return prev_action_plan;
	}


	public void setPrev_action_plan(String prev_action_plan) {
		this.prev_action_plan = prev_action_plan;
	}


	public String getEngg_finding() {
		return engg_finding;
	}


	public void setEngg_finding(String engg_finding) {
		this.engg_finding = engg_finding;
	}


	public String getDone_by() {
		return done_by;
	}


	public void setDone_by(String done_by) {
		this.done_by = done_by;
	}


	public String getAction_taken() {
		return action_taken;
	}


	public void setAction_taken(String action_taken) {
		this.action_taken = action_taken;
	}


	@Transient
	private int machine_downtime_id;
	
	
	@Column(name="ticket_raised_time")
	private Date ticket_raised_time;
	
	@Column(name="ticket_closed_time")
	private Date ticket_closed_time;
	

	/**
	 * @return the ticket_closed_time
	 */
	public Date getTicket_closed_time() {
		return ticket_closed_time;
	}


	/**
	 * @param ticket_closed_time the ticket_closed_time to set
	 */
	public void setTicket_closed_time(Date ticket_closed_time) {
		this.ticket_closed_time = ticket_closed_time;
	}


	public Date getTicket_raised_time() {
		return ticket_raised_time;
	}


	public void setTicket_raised_time(Date ticket_raised_time) {
		this.ticket_raised_time = ticket_raised_time;
	}


	public int getMachine_downtime_id() {
		return machine_downtime_id;
	}


	public void setMachine_downtime_id(int machine_downtime_id) {
		this.machine_downtime_id = machine_downtime_id;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	@Column(name="bd_slip", length = 200)
	private String bd_slip;
	
	
	@Column(name="observation", length = 2000)
	private String observation;
	
	@Column(name="detected_by", length = 200)
	private String detected_by;
	
	
	
	@Column(name="deletes", length = 2)
	private int deletes;

	@Column(name="diff_days", length =11, nullable = false, columnDefinition = "int default 0")
	private long diff_days;

	
	public long getDiff_days() {
		return diff_days;
	}


	public void setDiff_days(long diff_days) {
		this.diff_days = diff_days;
	}


	@ManyToOne
	@JoinColumn(name="shift_id")
	private Shift shift;
	
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	
	
	@ManyToOne
	@JoinColumn(name="cat_id")
	private Category category;
	
	@Column(name="tr_month")
	private String tr_month;
	
	public String getTr_month() {
		return tr_month;
	}

	public void setTr_month(String tr_month) {
		this.tr_month = tr_month;
	}

	
	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public int getBreakdown_id() {
		return breakdown_id;
	}


	public void setBreakdown_id(int breakdown_id) {
		this.breakdown_id = breakdown_id;
	}


	public String getBd_slip() {
		return bd_slip;
	}


	public void setBd_slip(String bd_slip) {
		this.bd_slip = bd_slip;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public String getDetected_by() {
		return detected_by;
	}


	public void setDetected_by(String detected_by) {
		this.detected_by = detected_by;
	}


	public int getDeletes() {
		return deletes;
	}


	public void setDeletes(int deletes) {
		this.deletes = deletes;
	}


	public Shift getShift() {
		return shift;
	}


	public void setShift(Shift shift) {
		this.shift = shift;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}


	@Override
	public String toString() {
		return "Breakdown [done_by=" + done_by + ", action_taken=" + action_taken + ", observation=" + observation
				+ "]";
	}


	
	
	

	
}
