package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Shift;
import com.kfMaintenancce.model.Spares;


public class BreakDownUpdateMobileApp {
	private  Breakdown breakdown;
	private String rootClause;
	private String actionTaken;
	private String preventiveactionPlan;
	private List<Spares> spares;
	private String actionTakenBy;
	private Shift shift;
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public Breakdown getBreakdown() {
		return breakdown;
	}
	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}
	public String getRootClause() {
		return rootClause;
	}
	public void setRootClause(String rootClause) {
		this.rootClause = rootClause;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getPreventiveactionPlan() {
		return preventiveactionPlan;
	}
	public void setPreventiveactionPlan(String preventiveactionPlan) {
		this.preventiveactionPlan = preventiveactionPlan;
	}
	public String getActionTakenBy() {
		return actionTakenBy;
	}
	public void setActionTakenBy(String actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}
	public List<Spares> getSpares() {
		return spares;
	}
	public void setSpares(List<Spares> spares) {
		this.spares = spares;
	}
	
	
	

}
