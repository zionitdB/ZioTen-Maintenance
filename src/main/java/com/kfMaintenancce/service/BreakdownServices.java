package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Spares;

public interface BreakdownServices {

	List<Breakdown> getBreakDownListByMachine(int machine_id);

	void addBreakdown(Breakdown b);

	List<Breakdown> getBreakdownList();

	void deleteBreakdown(int breakdown_id);

	Breakdown getBreakdownById(int breakdown_id);

	Breakdownupdate saveBreakdownUpdate(Breakdownupdate breakdownupdate);

	String getNewBreakDownNo();

	List<Breakdown> getBreakdoenById(int breakdown_id);

	List<Spares> getSpareListByBreakdownUpdate(int breakdown_update_id);

	Breakdownupdate getBreakDownUpdateByBreakDown(int breakdown_id);

}
