package com.kfMaintenancce.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.BreakDownUpdateMobileApp;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.service.BreakdownServices;


@RestController
@CrossOrigin("*")
@RequestMapping("/breakdown")
public class BreakdownController {

	@Autowired
	BreakdownServices breakdownServices; 


	
	@PostMapping(value = "/create")
	public @ResponseBody
	Status addBreakdown(@RequestBody Breakdown breakdown) {
		Status status= new Status();
		try {
			
			
			System.out.println("BREAK DOWN ADD:: ");
			
			String bdNo=breakdownServices.getNewBreakDownNo();
			if(breakdown.getMachine_downtime_id()!=0) {
				
				
			}
			
			 SimpleDateFormat f = new SimpleDateFormat("MMM");
			
			  String m2 =  f.format(breakdown.getTicket_raised_time());
			  breakdown.setTr_month(m2);
		
			  breakdown.setBd_slip(bdNo);
			  breakdown.setTc_tr_hr(0);
			  breakdown.setTotal_trial_hr(0);
			  breakdown.setTicket_closed_flag(0);
			  
			  
			  List<Breakdown> listByMachine = breakdownServices.getBreakDownListByMachine(breakdown.getMachine().getMachine_id());
			  
			  for(Breakdown b : listByMachine) {
				  
				  long diff_days = (breakdown.getTicket_raised_time().getTime()-b.getTicket_raised_time().getTime())/(3600000*24);
				  
				  
				  b.setDiff_days(diff_days);
				  
				  breakdownServices.addBreakdown(b);
				  
				  break;
			  }
			  
			breakdown.setDeletes(1);
			System.out.println(breakdown.toString());
			breakdownServices.addBreakdown(breakdown);
		} catch (Exception e) {
			// e.printStackTrace();
			}
		return status;


	}

	@PostMapping(value = "/updateBreakdown")
	public @ResponseBody
	Status updateBreakdown(@RequestBody Breakdown breakdown) {
		Status status= new Status();
		try {
			
			
			System.out.println("BREAK DOWN ADD:: "+breakdown.toString());
			
			
			
		
			  
			
			breakdownServices.addBreakdown(breakdown);
		} catch (Exception e) {
			// e.printStackTrace();
			}
		return status;


	}
	@GetMapping(value = "/list")
	public @ResponseBody
	List<Breakdown> getBreakdowns() {
		List<Breakdown> breakdownList = null;
		try {
			
			breakdownList = breakdownServices.getBreakdownList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	
	
	
	
	@GetMapping(value= "/delete/{breakdown_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteCategory(@PathVariable("breakdown_id") int breakdown_id){
		try{
			breakdownServices.deleteBreakdown(breakdown_id);
			return new Status("Breakdown Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	
	
	
	@RequestMapping(value = "/updateBreakdownMobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status updateBreakdownMobile(@RequestBody BreakDownUpdateMobileApp breakDownUpdateMobileApp) {
		try {
			System.out.println("Call Create MOBIWEL............................");
			
			Breakdown breakdown= breakdownServices.getBreakdownById(breakDownUpdateMobileApp.getBreakdown().getBreakdown_id());
			/*Shift shift =breakdown.getShift();
			shift.setUpDate(new Date());*/
			breakdown.setTicket_closed_time(new Date());
		//	breakdown.setShift(shift);
			breakdown.setStatus(2);
			breakdownServices.addBreakdown(breakdown);
			Breakdownupdate breakdownupdate = new Breakdownupdate();
			breakdown.setAction_taken(breakDownUpdateMobileApp.getActionTaken());
			breakdown.setDone_by(breakDownUpdateMobileApp.getActionTakenBy());
			//breakdown.set
			breakdownupdate.setAction_by(breakDownUpdateMobileApp.getActionTakenBy());
			breakdownupdate.setAction_taken(breakDownUpdateMobileApp.getActionTaken());
			breakdownupdate.setBreakdown_date(breakDownUpdateMobileApp.getBreakdown().getTicket_raised_time());
			breakdownupdate.setDeletes(1);
			breakdownupdate.setObservation(breakDownUpdateMobileApp.getBreakdown().getObservation());
			breakdownupdate.setPrev_action_plan(breakDownUpdateMobileApp.getPreventiveactionPlan());
			breakdownupdate.setRoot_cause(breakDownUpdateMobileApp.getRootClause());
			breakdownupdate.setBreakdown(breakDownUpdateMobileApp.getBreakdown());
			breakdownupdate.setMachine(breakDownUpdateMobileApp.getBreakdown().getMachine());
			//breakdownupdate.setShift(breakDownUpdateMobileApp.getShift());
			//breakdownupdate.setShift(shift);
			Breakdownupdate breakdownupdate2= breakdownServices.saveBreakdownUpdate(breakdownupdate);
	
			
			
			return new Status("Breakdown added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	
}
