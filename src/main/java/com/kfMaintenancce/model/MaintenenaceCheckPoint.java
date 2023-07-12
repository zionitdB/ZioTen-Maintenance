package com.kfMaintenancce.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="maint_check_point")
public class MaintenenaceCheckPoint {
	@Id
	@GeneratedValue
	@Column(name="maint_check_point_id")
	private int maintCheckPointId;
	
	@ManyToOne
	@JoinColumn(name="checklist_id")
	private Checklist checkpoint;
	
	@ManyToOne
	@JoinColumn(name="maint_id")
	private Maint maint;
	
	@Column(name="done_by")
	private String done_by;
	
	@Column(name="done_date")
	private Date done_date;
	
	
	
	@Column(name="status")
	private String status;

	public int getMaintCheckPointId() {
		return maintCheckPointId;
	}

	public void setMaintCheckPointId(int maintCheckPointId) {
		this.maintCheckPointId = maintCheckPointId;
	}

	public Checklist getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(Checklist checkpoint) {
		this.checkpoint = checkpoint;
	}

	public Maint getMaint() {
		return maint;
	}

	public void setMaint(Maint maint) {
		this.maint = maint;
	}

	public String getDone_by() {
		return done_by;
	}

	public void setDone_by(String done_by) {
		this.done_by = done_by;
	}

	public Date getDone_date() {
		return done_date;
	}

	public void setDone_date(Date done_date) {
		this.done_date = done_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MaintenenaceCheckPoint [maintCheckPointId=" + maintCheckPointId + ", checkpoint=" + checkpoint
				+ ", maint=" + maint + ", done_by=" + done_by + ", done_date=" + done_date + ", status=" + status + "]";
	}
	
	
	
	
	
}
