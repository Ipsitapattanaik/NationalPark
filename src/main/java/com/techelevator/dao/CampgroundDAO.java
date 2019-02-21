package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Campground;

public interface CampgroundDAO {
	
	List<Campground> getCampgroundsByParkID(int parkID);
	
}
