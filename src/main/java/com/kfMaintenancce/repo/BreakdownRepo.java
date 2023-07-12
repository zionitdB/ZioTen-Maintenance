package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Breakdown;

public interface BreakdownRepo  extends JpaRepository<Breakdown, Integer>{
	@Query("From Breakdown b where b.machine.machine_id=?1 and b.deletes=1")
	List<Breakdown> getBreakDownListByMachine(int machine_id);
	@Query("From Breakdown b where b.status=1 and b.deletes=1")
	List<Breakdown> getBreakdownList();
	@Query("select count(*) from Breakdown b where  substr(b.bd_slip,1,4)=?1")
	int getBreakdownCountByYearMonth(String mnyr);
	@Query("select MAX(b.bd_slip) from  Breakdown b where  substr(b.bd_slip,1,4)=?1")
	String getMaxBreakdownNoNoByYearMonth(String mnyr);
	@Query("From Breakdown b where b.breakdown_id=?1 and b.deletes=1")
	List<Breakdown> getBreakdoenById(int breakdown_id);
	@Query("From Breakdown b where b.status=?1 and b.deletes=1")
	List<Breakdown> getBreakBodownByStatus(int i);
	
	@Query("From Breakdown b where b.ticket_raised_time>=?1 and b.ticket_raised_time<=?2")
	List<Breakdown> getHistory(Date startDate, Date endDate);

}
