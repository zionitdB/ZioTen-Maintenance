package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.MachineOwner;

public interface MachineOwnerRepo extends JpaRepository<MachineOwner, Integer> {
	@Query("from MachineOwner m where m.user.id=?1")
	List<MachineOwner> getAllMachineOwnersByUser(String userId);
	@Query("from MachineOwner m where m.user.id=?1 and m.machine.machine_id=?2")

	Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(String id, int machine_id);

}
