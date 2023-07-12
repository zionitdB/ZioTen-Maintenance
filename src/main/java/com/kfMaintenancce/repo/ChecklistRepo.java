package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Checklist;

public interface ChecklistRepo extends JpaRepository<Checklist, Integer> {
	@Query("from Checklist c where c.machine.machine_id=?1 and c.frequency=?2")
	List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency);
	
	@Query("from Checklist c where c.task=?1 and  c.machine.machine_id=?2 and  c.frequency=?3 and c.type=?4")
	List<Checklist> getExistingList(String task, int machine_id, String frequency, String type);
	
	@Query("from Checklist c")
	List<Checklist> getChecklistList();
	
	@Query("from Checklist c where   c.machine.machine_id=?1 and  c.frequency=?2 and c.type=?3")
	List<Checklist> getChecklistByMachineNFrequency(int machine_id, String freq, String type);
}
