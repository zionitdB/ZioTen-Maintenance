package com.kfMaintenancce.dto;

import java.util.List;

public class Status {

	
	private String message;
	private List<Object> datas;

	public Status() {
	}

	public Status(String message) {
		
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getDatas(List<Object> existingChecklist) {
		return datas;
	}

	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}
	
	
	
}
