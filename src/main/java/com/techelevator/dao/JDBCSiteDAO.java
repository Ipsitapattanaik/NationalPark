package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Site;

public class JDBCSiteDAO implements SiteDAO {

	@Override
	public List<Site> getAvailableSitesByCampground(int campground_id, LocalDate start_date, LocalDate end_date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getAvailableSitesByPark(int park_id, LocalDate start_date, LocalDate end_date) {
		// TODO Auto-generated method stub
		return null;
	}

}
