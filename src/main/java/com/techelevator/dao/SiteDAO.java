package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Site;

public interface SiteDAO
{
	List<Site> getSitesAvailableForDateRange(long l, LocalDate startDate, LocalDate endDate)
			throws IllegalArgumentException;
	}


