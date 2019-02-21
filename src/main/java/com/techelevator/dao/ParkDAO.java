package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Park;

public interface ParkDAO {
	List<Park> getAllParks();

	Park getParkByName(String parkName);

	List<Park> getParksByState(String state);

	Park getParkById(long id);
	
	
}
