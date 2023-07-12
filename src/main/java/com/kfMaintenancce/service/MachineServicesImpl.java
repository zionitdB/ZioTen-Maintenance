package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.repo.MachineRepo;
@Service
public class MachineServicesImpl  implements MachineServices{
	@Autowired
	MachineRepo machineRepo;

	@Override
	public void addMachine(Machine machine) {
		// TODO Auto-generated method stub
		machineRepo.save(machine);
	}

	@Override
	public List<Machine> getMachineList() {
		// TODO Auto-generated method stub
		return machineRepo.findAll();
	}

	@Override
	public List<Machine> getGetMachinesByName(String machine_name) {
		// TODO Auto-generated method stub
		return machineRepo.getGetMachinesByName(machine_name);
	}

	@Override
	public void deleteMachine(int machine_id) {
		// TODO Auto-generated method stub
		Optional<Machine> optional= machineRepo.findById(machine_id);
		machineRepo.delete(optional.get());
	}

}
