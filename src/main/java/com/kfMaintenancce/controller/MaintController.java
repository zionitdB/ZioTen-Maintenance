package com.kfMaintenancce.controller;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
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

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.service.MaintServices;



@CrossOrigin
@RestController
@Component
@Service
@RequestMapping("/maint")

public class MaintController {

	@Autowired
	MaintServices maintServices;
	
	

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addMaint(@RequestBody Maint maint) {
		try {
			
			String arr1 [] = maint.getArr();
			
			System.out.println(arr1.length);
			
			System.out.println(maint.toString());
			
			
			for(int i=0; i<arr1.length; i++) {
			System.out.println("Date "+arr1[i].substring(0,10));
		    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(arr1[i].substring(0,10).substring(0,10));  
		    System.out.println("New Date "+date1);
			Calendar c = Calendar.getInstance(); 
			c.setTime(date1); 
			c.add(Calendar.DATE, 1);
			maint.setSchedule_date(c.getTime());
			//	maint.setSchedule_date(date1);
				maint.setMaint_id(0);
				maint.setStatusCode(1);
				
				
				maintServices.addMaint(maint);
				
			}
			
			return new Status("maintenance done !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}

	
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status updateMaint(@RequestBody  Maint maint) {
		try {
			
			System.out.println("Hello : " + maint);
			System.out.println("Check List : " + maint.getChecklist());

			System.out.println(maint.getDone_by());
			
			int maintId = maint.getMaint_id();
			
			List<Maint> maintListById = maintServices.getByMaintId(maintId);
			
			for(Maint m : maintListById) {
				
				m.setOverall_status(maint.getOverall_status());
				m.setDone_by(maint.getDone_by());
				m.setStatusCode(0);
				maintServices.addMaint(m);
			}
		
			for(MaintenenaceCheckPoint  maintenenaceCheckPoint:maint.getCheckpointlist()) {
				System.out.println(" POint :: "+maintenenaceCheckPoint.getCheckpoint().toString());
				System.out.println(" S :: "+maintenenaceCheckPoint.getStatus());

				/*MaintenenaceCheckPoint checkPoint= new MaintenenaceCheckPoint();
				Maint maint2= new Maint();
				maint2.setMaint_id(maintId);
				Checklist checklist= new Checklist();
				checklist.setChecklist_id(maintenenaceCheckPoint.getCheckpoint().getCheckpointId());
				checkPoint.setMaint(maint2);
				checkPoint.setCheckpoint(checklist);
				checkPoint.setStatus(maintenenaceCheckPoint.getStatus());
				checkPoint.setDone_by(maintenenaceCheckPoint.getDone_by());
				checkPoint.setDone_date(new Date());
				maintServices.saveMaintenenaceChekPoint(checkPoint);*/
					
					
				}
			
		/*for(int i=0; i<maint.getChecklist().size(); i++) {
				
				Maint l = maint.getChecklist().get(i);
				
			
				List<Scheduledchecklist> checklistById = scheduledchecklistServices.getChecklistById(maintId, l.getCheckpoint().getChecklist_id());
				
				
				if(checklistById.size()!=0) {
					
					for(Scheduledchecklist sl : checklistById) 
					{
						
						sl.setChecklist(l.getCheckpoint());
						sl.setStatus(l.getAction());
						sl.setMaint(maint);
						scheduledchecklistServices.addChecklist(sl);
						
						
					}
				}
				
				else {
					
					Scheduledchecklist s = new Scheduledchecklist();
					
					s.setChecklist(l.getCheckpoint());
					s.setStatus(l.getAction());
					s.setMaint(maint);
					scheduledchecklistServices.addChecklist(s);
					
					
				}
			
			}*/
		
		
			return new Status("maintenance done !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Maint> getMaints() {
		List<Maint> maintList = null;
		try {
			
			maintList = maintServices.getMaintList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
	@GetMapping(value = "/getDoneMaintenanceByMachine/{machineId}", produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody
	List<Maint> getDoneMaintenanceByMachine(@PathVariable("machineId") int machineId) {
		List<Maint> maintList = null;
		try {
			
			System.out.println("MAINT ID "+machineId);
			maintList = maintServices.getDoneMaintenanceByMachine(machineId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
	
	
	

	@RequestMapping(value = "/getRecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getReport(@RequestBody Maint maint) {
		List<Maint> maintList = null;
		try {
			maintList = maintServices.getReport(maint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
	
	
	

	@GetMapping(value= "/delete/{maint_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteCategory(@PathVariable("maint_id") int maint_id){
		try{
			maintServices.deleteMaint(maint_id);
			return new Status("Shift Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	
	@RequestMapping(value = "/maintenanceDone", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status maintenanceDone(@RequestBody  Maint maint) {
		try {
			System.out.println("Hii I am Doing Maintenance "+maint.toString());
			  maint.setStatusCode(0);
			  Maint m=maintServices.saveMaint(maint);
			
				System.out.println("Maintenance Check Point :: "+maint.getCheckpointlist().toString());

		for(MaintenenaceCheckPoint  maintenenaceCheckPoint:maint.getCheckpointlist()) {
			System.out.println("Maintenance  Ccheck POint :: "+maintenenaceCheckPoint.toString());
			MaintenenaceCheckPoint checkPoint= new MaintenenaceCheckPoint();
			Maint maint2= new Maint();
			maint2.setMaint_id(m.getMaint_id());
			Checklist checklist= new Checklist();
			checklist.setChecklist_id(maintenenaceCheckPoint.getCheckpoint().getCheckpointId());
			checkPoint.setMaint(maint2);
			checkPoint.setCheckpoint(checklist);
			checkPoint.setStatus(maintenenaceCheckPoint.getStatus());
			checkPoint.setDone_by(maintenenaceCheckPoint.getDone_by());
			checkPoint.setDone_date(new Date());
			maintServices.saveMaintenenaceChekPoint(checkPoint);
				
				
			}
		
		
			return new Status("maintenance done !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	

	@GetMapping(value = "/getCheckPointByMaintenacce", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> getCheckPointByMaintenacce(@RequestParam("maintId") int maintId) {
		List<Checklist> checkList= null;
		try {
			Maint maint = maintServices.getMaintById(maintId);
			if(maint !=null){
				checkList=maintServices.getMaintenencaceCheckPointByMaintenace(maint.getMachine().getMachine_id(),maint.getFrequency());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkList;
	}
	
	
}
