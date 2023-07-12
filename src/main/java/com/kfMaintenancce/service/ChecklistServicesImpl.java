package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.repo.ChecklistRepo;

@Service
public class ChecklistServicesImpl implements ChecklistServices {

	@Autowired
	ChecklistRepo checklistRepo;
	@Override
	public void addChecklist1(Checklist checklist) {
		// TODO Auto-generated method stub
		checklistRepo.save(checklist);
	}

	@Override
	public List<Checklist> getExistingList(String task, int machine_id, String frequency, String type) {
		// TODO Auto-generated method stub
		return checklistRepo.getExistingList(task,machine_id,frequency,type);
	}

	@Override
	public List<Checklist> getChecklistList() {
		// TODO Auto-generated method stub
		return checklistRepo.getChecklistList();
	}

	@Override
	public void deleteChecklist(int checklist_id) {
		// TODO Auto-generated method stub
		Optional<Checklist>optional =checklistRepo.findById(checklist_id);
		checklistRepo.delete(optional.get());
	}

	@Override
	public List<Checklist> getChecklistByMachineNFrequency(int machine_id, String freq, String type) {
		// TODO Auto-generated method stub
		return checklistRepo.getChecklistByMachineNFrequency(machine_id,freq,type);
	}

}
