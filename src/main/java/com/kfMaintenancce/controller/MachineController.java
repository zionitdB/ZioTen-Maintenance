 package com.kfMaintenancce.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.service.MachineServices;



@CrossOrigin
@RestController
@Component
@Service
@RequestMapping(value={"/machine_mst"})

public class MachineController {

	@Autowired
	MachineServices machineServices;
	

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addMachine(@RequestBody Machine machine) {
		try {
			
			
			machine.setDeletes(1);
			System.out.println(machine.toString());
			machineServices.addMachine(machine);
			return new Status("Employee added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}


	
	@GetMapping(value = "/{list}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getMachines() {
		List<Machine> machineList = null;
		try {
			
			machineList = machineServices.getMachineList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineList;
	}
	@RequestMapping(value = "/getGetMachinesByName", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getGetMachinesByName(@RequestBody Machine machine) {
		List<Machine> list= new ArrayList<>();
		try {
			
			
			System.out.println("MAINT ID "+machine.getMachine_name());
			list = machineServices.getGetMachinesByName(machine.getMachine_name());
		} catch (Exception e) {
			e.printStackTrace();
			// new Status(e.toString());
		}
			return list;
	}

	/*@GetMapping(value = "/getGetMachinesByName/{machineName}", produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody
	List<Machine> getGetMachinesByName(@PathVariable("machineName") String machineName) {
		List<Machine> machines = null;
		try {
			
			System.out.println("MAINT ID "+machineName);
			machines = machineServices.getGetMachinesByName(machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machines;
	}
	
	
*/
	@GetMapping(value = "/machineName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Set<String> getMachineNames() {
		Set<String> machineNames = new HashSet<String>();
		
		try {
			
			List<Machine> machineList = machineServices.getMachineList();
			for(Machine machine: machineList){
				machineNames.add(machine.getMachine_name());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineNames;
	}
	
	
	@GetMapping(value= "/delete/{machine_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteMachine(@PathVariable("machine_id") int machine_id){
		try{
			machineServices.deleteMachine(machine_id);
			return new Status("Machine Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	
	
	
}
