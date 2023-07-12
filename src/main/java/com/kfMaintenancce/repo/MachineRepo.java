package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Machine;

public interface MachineRepo extends JpaRepository<Machine, Integer> {
	@Query("From Machine m where m.machine_name=?1")
	List<Machine> getGetMachinesByName(String machine_name);

}
