package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.repo.ChecklistRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;

@Service
public class MaintServicesImpl implements MaintServices{

	@Autowired
	MaintRepo maintRepo;
	@Autowired
	MaintenenaceCheckPointRepo maintenenaceCheckPointRepo;
	@Autowired
	ChecklistRepo checklistRepo;
	@Override
	public void addMaint(Maint maint) {
		// TODO Auto-generated method stub
		maintRepo.save(maint);
	}

	@Override
	public List<Maint> getByMaintId(int maintId) {
		// TODO Auto-generated method stub
		return maintRepo.getByMaintId(maintId);
	}

	@Override
	public List<Maint> getMaintList() {
		// TODO Auto-generated method stub
		return maintRepo.getMaintList();
	}

	@Override
	public List<Maint> getDoneMaintenanceByMachine(int machineId) {
		// TODO Auto-generated method stub
		return maintRepo.getDoneMaintenanceByMachine(machineId);
	}

	@Override
	public List<Maint> getReport(Maint maint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMaint(int maint_id) {
		// TODO Auto-generated method stub
		Optional<Maint> optional = maintRepo.findById(maint_id);
		
		maintRepo.delete(optional.get());
	}

	@Override
	public void saveMaintenenaceChekPoint(MaintenenaceCheckPoint checkPoint) {
		// TODO Auto-generated method stub
		maintenenaceCheckPointRepo.save(checkPoint);
	}

	@Override
	public Maint saveMaint(Maint maint) {
		// TODO Auto-generated method stub
		return maintRepo.save(maint);
	}

	@Override
	public Maint getMaintById(int maintId) {
		// TODO Auto-generated method stub
		Optional<Maint>  optional=maintRepo.findById(maintId);
		if(optional.isPresent()){
			return optional.get();
		}else{
			return null;
		}
		
	}

	@Override
	public List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency) {
		// TODO Auto-generated method stub
		return checklistRepo.getMaintenencaceCheckPointByMaintenace(machine_id, frequency);
	}

}
