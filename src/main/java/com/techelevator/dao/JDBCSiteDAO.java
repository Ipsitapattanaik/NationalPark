package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.techelevator.model.Site;

public class JDBCSiteDAO implements SiteDAO
{
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Site> mapRowToSite = (row, pos) -> {
		Site s = new Site();

		return s;
	};
	
	public JDBCSiteDAO(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesAvailableForDateRange(long campgroundID, LocalDate startDate, LocalDate endDate, boolean accessible, int rvLength, boolean utilities, int occupancy) throws IllegalArgumentException
	{
		if (startDate == null || endDate == null) throw new IllegalArgumentException("Date entered cannot be null");
		if (startDate.isAfter(endDate)) throw new IllegalArgumentException("Start date cannot be after end date");
		
		return jdbcTemplate.query("SELECT (max_occupancy, accessible, max_rv_length, utilities) FROM site "
				+ "WHERE campground_id = ? AND accessible = ? AND max_rv_length > ? AND utilities = ? AND max_occupancy > ? AND site_id NOT IN "
				+ "(SELECT DISTINCT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS (?, ?))",
				mapRowToSite, campgroundID, accessible, rvLength, utilities, occupancy, startDate, endDate);
	
//	String sqlQueryForSiteInformation = "SELECT (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "+
//			+ "WHERE campground_id = ? AND site_id NOT IN ";
		
	}
}