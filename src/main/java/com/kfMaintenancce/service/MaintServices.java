package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;

public interface MaintServices {

	void addMaint(Maint maint);

	List<Maint> getByMaintId(int maintId);

	List<Maint> getMaintList();

	List<Maint> getDoneMaintenanceByMachine(int machineId);

	List<Maint> getReport(Maint maint);

	void deleteMaint(int maint_id);

	void saveMaintenenaceChekPoint(MaintenenaceCheckPoint checkPoint);

	Maint saveMaint(Maint maint);

	Maint getMaintById(int maintId);

	List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency);

}
