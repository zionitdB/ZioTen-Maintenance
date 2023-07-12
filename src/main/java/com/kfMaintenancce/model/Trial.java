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

@Table(name="trial")

public class Trial implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int trial_id;
	
	

	
	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	

	@Transient
	private Date start_date;
	
	@Transient
	private Date end_date;
	
	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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


	@Column(name="trial_result", length = 2000)
	private String trial_result;
	
	
	
	@Column(name="ticket_raised_time")
	private Date ticket_raised_time;
	
	
	@Column(name="ticket_closed_time")
	private Date ticket_closed_time;
	

	@Column(name="sent_to_trial_time")
	private Date sent_to_trial_time;
	
/*	
	@Column(name="trial_done_time")
	private Date trial_done_time;*/
	
	
	@Column(name="ticket_raised_flag")
	private int ticket_raised_flag;
	
	
	@Column(name="ticket_closed_flag")
	private int ticket_closed_flag;
	
	
	
	
	public Date getTicket_raised_time() {
		return ticket_raised_time;
	}

	public void setTicket_raised_time(Date ticket_raised_time) {
		this.ticket_raised_time = ticket_raised_time;
	}

	public Date getTicket_closed_time() {
		return ticket_closed_time;
	}

	public void setTicket_closed_time(Date ticket_closed_time) {
		this.ticket_closed_time = ticket_closed_time;
	}

	public Date getSent_to_trial_time() {
		return sent_to_trial_time;
	}

	public void setSent_to_trial_time(Date sent_to_trial_time) {
		this.sent_to_trial_time = sent_to_trial_time;
	}

/*	public Date getTrial_done_time() {
		return trial_done_time;
	}

	public void setTrial_done_time(Date trial_done_time) {
		this.trial_done_time = trial_done_time;
	}*/

	public int getTicket_raised_flag() {
		return ticket_raised_flag;
	}

	public void setTicket_raised_flag(int ticket_raised_flag) {
		this.ticket_raised_flag = ticket_raised_flag;
	}

	public int getTicket_closed_flag() {
		return ticket_closed_flag;
	}

	public void setTicket_closed_flag(int ticket_closed_flag) {
		this.ticket_closed_flag = ticket_closed_flag;
	}


	@Column(name="trial_date")
	private Date trial_date;
	
	@Column(name="status")
	private int status;
	
	
	@Column(name="ftrstatus")
	private int ftrstatus;
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFtrstatus() {
		return ftrstatus;
	}

	public void setFtrstatus(int ftrstatus) {
		this.ftrstatus = ftrstatus;
	}


	@Column(name="month")
	private String month;
	
	@Column(name="tr_month")
	private String tr_month;
	
	public String getTr_month() {
		return tr_month;
	}

	public void setTr_month(String tr_month) {
		this.tr_month = tr_month;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}


	@Column(name="comment", length = 1000)
	private String comment;
	
	
	@Column(name="devialtion_comment", length = 1000)
	private String devialtion_comment;
	
	

	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;

	
	@ManyToOne
	@JoinColumn(name="cat_id")
	private Category category;
	
	
	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
	@ManyToOne
	@JoinColumn(name="breakdown_update_id")
	private Breakdownupdate breakdownupdate;
	

	@ManyToOne
	@JoinColumn(name="breakdown_id")
	private Breakdown breakdown;
	
	
	
	public Breakdown getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}


	@Column(name="deletes", length = 2)
	private int deletes;



	public int getTrial_id() {
		return trial_id;
	}



	public void setTrial_id(int trial_id) {
		this.trial_id = trial_id;
	}



	public String getTrial_result() {
		return trial_result;
	}



	public void setTrial_result(String trial_result) {
		this.trial_result = trial_result;
	}



	public Date getTrial_date() {
		return trial_date;
	}



	public void setTrial_date(Date trial_date) {
		this.trial_date = trial_date;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getDevialtion_comment() {
		return devialtion_comment;
	}



	public void setDevialtion_comment(String devialtion_comment) {
		this.devialtion_comment = devialtion_comment;
	}



	public Breakdownupdate getBreakdownupdate() {
		return breakdownupdate;
	}



	public void setBreakdownupdate(Breakdownupdate breakdownupdate) {
		this.breakdownupdate = breakdownupdate;
	}



	public int getDeletes() {
		return deletes;
	}



	public void setDeletes(int deletes) {
		this.deletes = deletes;
	}

	@Override
	public String toString() {
		return "Trial [trial_id=" + trial_id + ", startDate=" + startDate + ", endDate=" + endDate + ", start_date="
				+ start_date + ", end_date=" + end_date + ", trial_result=" + trial_result + ", ticket_raised_time="
				+ ticket_raised_time + ", ticket_closed_time=" + ticket_closed_time + ", sent_to_trial_time="
				+ sent_to_trial_time + ", ticket_raised_flag=" + ticket_raised_flag + ", ticket_closed_flag="
				+ ticket_closed_flag + ", trial_date=" + trial_date + ", status=" + status + ", ftrstatus=" + ftrstatus
				+ ", month=" + month + ", tr_month=" + tr_month + ", comment=" + comment + ", devialtion_comment="
				+ devialtion_comment + ", machine=" + machine + ", category=" + category + ", breakdownupdate="
				+ breakdownupdate + ", breakdown=" + breakdown + ", deletes=" + deletes + ", getStart_date()="
				+ getStart_date() + ", getEnd_date()=" + getEnd_date() + ", getStartDate()=" + getStartDate()
				+ ", getEndDate()=" + getEndDate() + ", getTicket_raised_time()=" + getTicket_raised_time()
				+ ", getTicket_closed_time()=" + getTicket_closed_time() + ", getSent_to_trial_time()="
				+ getSent_to_trial_time() + ", getTicket_raised_flag()=" + getTicket_raised_flag()
				+ ", getTicket_closed_flag()=" + getTicket_closed_flag() + ", getStatus()=" + getStatus()
				+ ", getFtrstatus()=" + getFtrstatus() + ", getTr_month()=" + getTr_month() + ", getMonth()="
				+ getMonth() + ", getMachine()=" + getMachine() + ", getCategory()=" + getCategory()
				+ ", getBreakdown()=" + getBreakdown() + ", getTrial_id()=" + getTrial_id() + ", getTrial_result()="
				+ getTrial_result() + ", getTrial_date()=" + getTrial_date() + ", getComment()=" + getComment()
				+ ", getDevialtion_comment()=" + getDevialtion_comment() + ", getBreakdownupdate()="
				+ getBreakdownupdate() + ", getDeletes()=" + getDeletes() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}
