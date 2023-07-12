package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity

@Table(name="breakdownupdate_tx")

public class Breakdownupdate implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int breakdown_update_id;
	
	
	@Transient
	private List<Breakdownupdate> breakdownupdate;
	
	
	public List<Breakdownupdate> getBreakdownupdate() {
		return breakdownupdate;
	}
	

	@Transient
	private String spare;
	
	@Transient
	private long qty;
	
	public String getSpare() {
		return spare;
	}


	public void setSpare(String spare) {
		this.spare = spare;
	}


	public long getQty() {
		return qty;
	}


	public void setQty(long qty) {
		this.qty = qty;
	}


	public void setBreakdownupdate(List<Breakdownupdate> breakdownupdate) {
		this.breakdownupdate = breakdownupdate;
	}



	
	@Column(name="breakdown_date")
	private Date breakdown_date;
	
	
	
	
	public Date getBreakdown_date() {
		return breakdown_date;
	}


	public void setBreakdown_date(Date breakdown_date) {
		this.breakdown_date = breakdown_date;
	}


	@Column(name="observation", length = 2000)
	private String observation;
	
	@Column(name="action_by", length = 200)
	private String action_by;
	
	@Column(name="breakdown_detected", length = 200)
	private String breakdown_detected;
	
	
	@Column(name="action_taken", length = 2000)
	private String action_taken;
	
	
	@Column(name="prev_action_plan", length = 2000)
	private String prev_action_plan;
	

	@Column(name="root_cause", length = 500)
	private String root_cause;
	
	
/*	@Column(name="spares_used", length = 500)
	private String spares_used;
	*/
	
	
	
	
	@Column(name="deletes", length = 2)
	private int deletes;


	
	@ManyToOne
	@JoinColumn(name="shift_id")
	private Shift shift;
	
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;

	
	@ManyToOne
	@JoinColumn(name="breakdown_id")
	private Breakdown breakdown;
	
	


	public Breakdown getBreakdown() {
		return breakdown;
	}


	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}

	
	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
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

	public int getBreakdown_update_id() {
		return breakdown_update_id;
	}


	public void setBreakdown_update_id(int breakdown_update_id) {
		this.breakdown_update_id = breakdown_update_id;
	}


	public String getAction_by() {
		return action_by;
	}


	public void setAction_by(String action_by) {
		this.action_by = action_by;
	}


	public String getBreakdown_detected() {
		return breakdown_detected;
	}


	public void setBreakdown_detected(String breakdown_detected) {
		this.breakdown_detected = breakdown_detected;
	}


	public String getAction_taken() {
		return action_taken;
	}


	public void setAction_taken(String action_taken) {
		this.action_taken = action_taken;
	}


	public String getPrev_action_plan() {
		return prev_action_plan;
	}


	public void setPrev_action_plan(String prev_action_plan) {
		this.prev_action_plan = prev_action_plan;
	}


	public String getRoot_cause() {
		return root_cause;
	}


	public void setRoot_cause(String root_cause) {
		this.root_cause = root_cause;
	}


	
	
}
