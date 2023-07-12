package com.kfMaintenancce.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.service.ChecklistServices;
import com.kfMaintenancce.service.MachineServices;
import com.kfMaintenancce.service.MaintServices;

@CrossOrigin
@RestController
@Component
@Service
@RequestMapping("/checklist")

public class ChecklistController {

	@Autowired
	ChecklistServices checklistServices;
	
	@Autowired
	MachineServices machineServices;
	
	

	@Autowired
	MaintServices maintServices;
	
	
	
/*
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Status2 saveUserDataAndFile(@RequestBody MultipartFile file) {

		List<Object> machinelist = null;
		//machinelist = new ArrayList<>();
	
		try {
			int i = 1;
			if (file.isEmpty()) {
				System.out.println("File not found");
			} else {
				System.out.println(file.getOriginalFilename());
			
				try {

					File dir = new File(System.getProperty("catalina.base"), "uploads");
					File uplaodedFile = new File(dir + file.getOriginalFilename());
					file.transferTo(uplaodedFile);
					FileInputStream excelFile = new FileInputStream(uplaodedFile);
					Workbook workbook = new XSSFWorkbook(excelFile);
					Sheet datatypeSheet = workbook.getSheetAt(0);
					DataFormatter formatter = new DataFormatter();
		
					System.out.println(datatypeSheet.getLastRowNum());

					while (i <= datatypeSheet.getLastRowNum()) {

						Checklist checklist = new Checklist();
						XSSFRow row = (XSSFRow) datatypeSheet.getRow(i++);
				
						int j  = row.getFirstCellNum(); 
					
						System.out.println(row.getFirstCellNum());

						if (row.getCell(j) != null) {
							
							
							List<Maint> maintList =  null;
									
							checklist.setTask(formatter.formatCellValue(row.getCell(j)));
							checklist.setFrequency(formatter.formatCellValue(row.getCell(j+2)));


							
							List<Machine> machineList = machineServices.getListByname(formatter.formatCellValue(row.getCell(j+1)));
							
								
							if(machineList.size() != 0){
								
								for(Machine m : machineList){
									
									checklist.setMachine(m);
									
									 maintList = maintServices.getList(m, formatter.formatCellValue(row.getCell(j+2)));
									
									
									
								}
							}
							
							else {
								
								
								machinelist.add(formatter.formatCellValue(row.getCell(j+1)));
								
								Machine machine = new Machine();
								machine.setMachine_name(formatter.formatCellValue(row.getCell(j+1)));
								machine.setDeletes(1);
								machineServices.addMachine(machine);
								
								checklist.setMachine(machine);
								
								 maintList = maintServices.getList(machine, formatter.formatCellValue(row.getCell(j+2)));
								
							}
							
							checklistServices.addChecklist(checklist);
							
							
							
							for(Maint mList : maintList) {
								
								Scheduledchecklist scheduledchecklist = new Scheduledchecklist();
								
								//scheduledchecklist.setChecklist(checklist);
								scheduledchecklist.setMaint(mList);
								scheduledchecklist.setStatus(null);
								
								scheduledchecklistServices.addChecklist(scheduledchecklist);
								
							}
							
							
					}

					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			
			System.out.println(e);

			//return new Status2("");
		
		}

		return new Status2(1,"machineExist", machinelist);
}
	*/
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addChecklist(@RequestBody Checklist checklist) {
		Status  status= new Status();
		List<Object> ExistingChecklist = null;
		ExistingChecklist = new ArrayList<>();
		try {
			
			
			//String arr1 [] = checklist.getArr();
			
		//	System.out.println(arr1.length);
			
		
			List<Checklist> arr = checklist.getChecklist();
			
			
			for(Checklist c : arr) {
			
				
				List<Checklist> existingList = checklistServices.getExistingList(c.getTask(), checklist.getMachine().getMachine_id(), checklist.getFrequency(), checklist.getType());
				
				if(existingList.size()==0) {
					
					checklist.setTask(c.getTask());
					checklist.setOperation(c.getOperation());
					checklist.setAcceptableRange(c.getAcceptableRange());
					checklist.setChecklist_id(0);
				
					
					checklistServices.addChecklist1(checklist);
					
				}
			
				else {
					
					ExistingChecklist.add(c.getTask());
					status.setMessage("exist");
					status.getDatas(ExistingChecklist);
					//return new Status2(0,"exist", ExistingChecklist);
				}
			
			
			}
			status.setMessage("success");
			status.getDatas(ExistingChecklist);
			//return new Status2(1, "success", ExistingChecklist);
		} catch (Exception e) {
			// e.printStackTrace();
			
		}
		return status;

	}


	
	@GetMapping(value = "/{list}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> getChecklist() {
		List<Checklist> checklistList = null;
		try {
			
			checklistList = checklistServices.getChecklistList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checklistList;
	}
	
	
	
	@GetMapping(value= "/delete/{checklist_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteChecklist(@PathVariable("checklist_id") int checklist_id){
		try{
			checklistServices.deleteChecklist(checklist_id);
			return new Status("Checklist Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	

	@GetMapping(value = "/getchecklist", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> getChecklists(@RequestParam("machine_id") int machine_id, @RequestParam("freq") String freq, @RequestParam("type") String type) {
		List<Checklist> checkList = null;
	
		try {
			
			System.out.println(machine_id);
			
			System.out.println(freq);
			
			checkList = checklistServices.getChecklistByMachineNFrequency(machine_id, freq, type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkList;
	}
	
	
	
	
}
