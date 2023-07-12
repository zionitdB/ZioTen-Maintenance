package com.kfMaintenancce.controller;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.TrialServices;

import ch.qos.logback.classic.Logger;

@CrossOrigin
@RestController
@Component
@Service
@RequestMapping("/trial")

public class TrialController {

	@Autowired
	TrialServices trialServices;

	@Autowired
	BreakdownServices breakdownServices;



	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial(@RequestBody Trial trial) {
		try {
			
			System.out.println("TRAILL OBJ "+trial.toString());
			
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE+++++++++++++++++++++++++++++++++");
			trial.setDeletes(1);
			System.out.println(trial.toString());
			trial.setTicket_raised_time(trial.getBreakdownupdate().getBreakdown().getTicket_raised_time());
			trial.setBreakdown(trial.getBreakdownupdate().getBreakdown());
			trial.setMachine(trial.getBreakdownupdate().getMachine());
			trial.setSent_to_trial_time(trial.getBreakdownupdate().getBreakdown_date());
		
			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(trial.getTrial_date());

			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}
				
				br.setTicket_closed_time(new Date());
				System.out.println("CLOSED TIME ====== "+br.getTicket_closed_time());

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setTicket_closed_flag(1);
				br.setStatus(3);

				if (br.getDone_by() == null) {

					br.setDone_by(trial.getBreakdownupdate().getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + trial.getBreakdownupdate().getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(trial.getBreakdownupdate().getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + trial.getBreakdownupdate().getAction_taken());
				}
				
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);
			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(3);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}

	@RequestMapping(value = "/create1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial1(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE  1111111111111 +++++++++++++++++++++++++++++++++");

			trial.setDeletes(1);
			System.out.println(trial.toString());

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(trial.getTrial_date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setStatus(1);

				if (br.getDone_by() == null) {

					br.setDone_by(trial.getBreakdownupdate().getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + trial.getBreakdownupdate().getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(trial.getBreakdownupdate().getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + trial.getBreakdownupdate().getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setTicket_raised_flag(0);

			}

			trial.setTr_month(m2);
			trial.setTicket_closed_flag(0);
			trial.setTrial_date(d);
			trial.setStatus(1);
			trial.setFtrstatus(0);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}

	

	@RequestMapping(value = "/create2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial2(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE 222222222222+++++++++++++++++++++++++++++++++");

			trial.setDeletes(1);
			System.out.println(trial.toString());

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(trial.getTrial_date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);
				br.setTicket_closed_flag(1);
				br.setStatus(4);

				if (br.getDone_by() == null) {

					br.setDone_by(trial.getBreakdownupdate().getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + trial.getBreakdownupdate().getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(trial.getBreakdownupdate().getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + trial.getBreakdownupdate().getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(4);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Trial> getTrials() {
		List<Trial> trialList = null;
		try {

			trialList = trialServices.getTrialList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trialList;
	}

	/*
	 * @RequestMapping(value = "/ftrList", method = RequestMethod.POST, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody List<Object>
	 * getBreakDownHistory(@RequestBody Trial trial) {
	 * 
	 * List<Object> trialList = null; List<Trial> ftrList = null; try {
	 * 
	 * String date1 = trial.getStartDate(); String date2 = trial.getEndDate();
	 * 
	 * Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(date1); Date
	 * endDate = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
	 * 
	 * ftrList = trialServices.getBreakHistory(startDate, endDate,
	 * trial.getMachine());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return trialList; }
	 */

	@RequestMapping(value = "/breakdownHistoryList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdown> getBreakdownHistory(@RequestBody Trial trial) {

		List<Object> totalList = new ArrayList<Object>();
		List<Breakdown> ftrList = null;

		try {

			Date startDate = null;

			Date endDate = null;

			if (trial.getStartDate() != null && trial.getEndDate() != null) {

				String date1 = trial.getStartDate();
				String date2 = trial.getEndDate();

				startDate = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
				endDate = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

			}

			ftrList = trialServices.getHistory(startDate, endDate);

		}

		catch (Exception e) {

			System.out.println(e);
		}

		return ftrList;

	}


	@RequestMapping(value = "/getRecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdownupdate> getReport(@RequestBody Trial trial) {
		List<Breakdownupdate> maintList = null;
		try {
			maintList = trialServices.getReport(trial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
//**********************************************************************************************************************

	@RequestMapping(value = "/approve", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status approve(@RequestBody Trial trial) {
		try {
			
			
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE+++++++++++++++++++++++++++++++++");
			trial.setDeletes(1);
			System.out.println(trial.toString());
			trial.setTicket_raised_time(trial.getBreakdown().getTicket_raised_time());
			trial.setBreakdown(trial.getBreakdown());
			trial.setMachine(trial.getBreakdown().getMachine());
			trial.setSent_to_trial_time(trial.getSent_to_trial_time());
		
			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());

			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();
			Breakdownupdate breakdownupdate= breakdownServices.getBreakDownUpdateByBreakDown(trial.getBreakdown().getBreakdown_id());
			long trial_done_time = d.getTime();
			long sent_to_trial_time = breakdownupdate.getBreakdown_date().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(breakdownupdate.getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}
				
				br.setTicket_closed_time(new Date());
				System.out.println("CLOSED TIME ====== "+br.getTicket_closed_time());

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setTicket_closed_flag(1);
				br.setStatus(3);

				if (br.getDone_by() == null) {

					br.setDone_by(breakdownupdate.getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + breakdownupdate.getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(breakdownupdate.getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + breakdownupdate.getAction_taken());
				}
				
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);
			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(3);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/sendToMaintenance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status sendToMaintenance(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE  1111111111111 +++++++++++++++++++++++++++++++++");
			Breakdownupdate breakdownupdate= breakdownServices.getBreakDownUpdateByBreakDown(trial.getBreakdown().getBreakdown_id());

			trial.setDeletes(1);
			trial.setDeletes(1);
			System.out.println(trial.toString());
			trial.setTicket_raised_time(trial.getBreakdown().getTicket_raised_time());
			trial.setBreakdown(trial.getBreakdown());
			trial.setMachine(breakdownupdate.getMachine());
			trial.setSent_to_trial_time(breakdownupdate.getBreakdown_date());
		

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = breakdownupdate.getBreakdown_date().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			//System.out.println();
			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(breakdownupdate.getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setStatus(1);

				if (br.getDone_by() == null) {

					br.setDone_by(breakdownupdate.getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + breakdownupdate.getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(breakdownupdate.getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + breakdownupdate.getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setTicket_raised_flag(0);

			}

			trial.setTr_month(m2);
			trial.setTicket_closed_flag(0);
			trial.setTrial_date(d);
			trial.setStatus(1);
			trial.setFtrstatus(0);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/approveWithDeviation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status approveWithDeviation(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE 222222222222+++++++++++++++++++++++++++++++++");
			
			Breakdownupdate breakdownupdate= breakdownServices.getBreakDownUpdateByBreakDown(trial.getBreakdown().getBreakdown_id());

			trial.setDeletes(1);
			trial.setDeletes(1);
			System.out.println(trial.toString());
			trial.setTicket_raised_time(breakdownupdate.getBreakdown().getTicket_raised_time());
			trial.setBreakdown(breakdownupdate.getBreakdown());
			trial.setMachine(trial.getBreakdown().getMachine());
			trial.setSent_to_trial_time(breakdownupdate.getBreakdown_date());
		

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(breakdownupdate.getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + "\n" + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);
				br.setTicket_closed_flag(1);
				br.setStatus(4);

				if (br.getDone_by() == null) {

					br.setDone_by(breakdownupdate.getAction_by());
				}

				else {

					br.setDone_by(br.getDone_by() + "\n" + breakdownupdate.getAction_by());
				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(breakdownupdate.getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + "\n" + breakdownupdate.getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(4);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
}
