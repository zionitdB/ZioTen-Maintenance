package com.kfMaintenancce.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.BreakdownupdateRepo;
import com.kfMaintenancce.repo.SparesRepo;

@Service
public class BreakdownServicesImpl  implements BreakdownServices{
	
	@Autowired
	BreakdownRepo breakdownRepo;
	
	@Autowired
	BreakdownupdateRepo breakdownupdateRepo;
	@Autowired
	SparesRepo sparesRepo;

	@Override
	public List<Breakdown> getBreakDownListByMachine(int machine_id) {
		// TODO Auto-generated method stub
		return breakdownRepo.getBreakDownListByMachine(machine_id);
	}

	@Override
	public void addBreakdown(Breakdown b) {
		// TODO Auto-generated method stub
		breakdownRepo.save(b);
	}

	@Override
	public List<Breakdown> getBreakdownList() {
		// TODO Auto-generated method stub
		return breakdownRepo.getBreakdownList();
	}

	@Override
	public void deleteBreakdown(int breakdown_id) {
		// TODO Auto-generated method stub
		Optional<Breakdown> optional=breakdownRepo.findById(breakdown_id);
		breakdownRepo.save(optional.get());
		
	}

	@Override
	public Breakdown getBreakdownById(int breakdown_id) {
		// TODO Auto-generated method stub
		Optional<Breakdown> optional =   breakdownRepo.findById(breakdown_id);
		
		if (optional.isPresent()) {
			return optional.get();
		}else{
			return null;	
		}
		
	}

	@Override
	public Breakdownupdate saveBreakdownUpdate(Breakdownupdate breakdownupdate) {
		// TODO Auto-generated method stub
		return breakdownupdateRepo.save(breakdownupdate);
	}

	@Override
	public String getNewBreakDownNo() {
		// TODO Auto-generated method stub
		
		
		
		
		String bdNo="";
		String mnyr="";
		Calendar c = Calendar.getInstance();
		int yr = c.get(Calendar.YEAR);
		int mn = c.get(Calendar.MONTH)+1;
		String year = String.valueOf(yr).substring(2,4);
		  String month = "";
		if(mn<=9){
			month="0"+String.valueOf(mn);
		}else{
			month=String.valueOf(mn);
		}
		mnyr=year+month;
		//System.out.println("Year MOnth"+mnyr);
		
		int count=breakdownRepo.getBreakdownCountByYearMonth(mnyr);
		

		if(count==0){
			bdNo=mnyr+"0001";
		}else{
			String maxNo="1"+breakdownRepo.getMaxBreakdownNoNoByYearMonth(mnyr);
			///System.out.println("MAX NO "+changeManagementRepo.getMaxChangeMangementNoByYearMonth(mnyr));
			int mxint=Integer.parseInt(maxNo);
			mxint++;
			bdNo=String.valueOf(mxint).substring(1,9);
		//	System.out.println("NEW MAX NO "+newmaxNo);

			
		}
		return bdNo;
	}

	@Override
	public List<Breakdown> getBreakdoenById(int breakdown_id) {
		// TODO Auto-generated method stub
		return breakdownRepo.getBreakdoenById(breakdown_id);
	}

	@Override
	public List<Spares> getSpareListByBreakdownUpdate(int breakdown_update_id) {
		// TODO Auto-generated method stub
		return sparesRepo.getSparesByBreakDownUpdate(breakdown_update_id);
	}

	@Override
	public Breakdownupdate getBreakDownUpdateByBreakDown(int breakdown_id) {
		// TODO Auto-generated method stub
		List<Breakdownupdate>optional= breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown_id);
		
		if(optional.size()!=0){
			return optional.get(0);
		}else{
			return null;
		}
			
	}

}
