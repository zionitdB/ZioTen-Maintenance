package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Machine;

public interface MachineServices {

	void addMachine(Machine machine);

	List<Machine> getMachineList();

	List<Machine> getGetMachinesByName(String machine_name);

	void deleteMachine(int machine_id);

}
