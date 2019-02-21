package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Site;

public interface SiteDAO
{
	List <Site> getAvailableSitesByCampground(long campground_id, LocalDate start_date, LocalDate end_date) throws IllegalArgumentException;
	List <Site> getAvailableSitesByPark(long park_id, LocalDate start_date, LocalDate end_date) throws IllegalArgumentException;
}
