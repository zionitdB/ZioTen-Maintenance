package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Maint;

public interface MaintRepo  extends JpaRepository<Maint, Integer>{
	@Query("From Maint m where m.maint_id=?1")
	List<Maint> getByMaintId(int maintId);
	@Query("From Maint m where m.statusCode=1")
	List<Maint> getMaintList();
	
	@Query("from Maint m where m.machine.machine_id=?1 and m.statusCode=0")
	List<Maint> getDoneMaintenanceByMachine(int machineId);

}
