package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Site;

public interface SiteDAO
{
	List<Site> getSitesAvailableForDateRange(long campgroundID, LocalDate startDate, LocalDate endDate, boolean accessible, int max_rv_length, boolean utilities, int max_occupancy)
			throws IllegalArgumentException;
	}


