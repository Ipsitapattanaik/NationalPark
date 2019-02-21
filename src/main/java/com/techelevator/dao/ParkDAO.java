package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Park;

public interface ParkDAO {
	List<Park> getAllParks();

	List<Park> getParkByNameAndState(String parkName, String state);

	List<Park> getParksByState(String state);

	Park getParkById(long id);
}
