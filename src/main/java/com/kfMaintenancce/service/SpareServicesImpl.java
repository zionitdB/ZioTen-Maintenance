package com.kfMaintenancce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.repo.SparesRepo;

@Service
public class SpareServicesImpl  implements SpareServices{

	@Autowired
	SparesRepo sparesRepo;
	@Override
	public void addSpares(Spares s) {
		// TODO Auto-generated method stub
		sparesRepo.save(s);
	}

}
