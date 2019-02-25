package com.techelevator.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Site;

public class JDBCSiteDAO implements SiteDAO {
	private JdbcTemplate jdbcTemplate;

	// private RowMapper<Site> mapRowToSite = (row, pos) -> {
	// Site s = new Site();
//
	// return s;
	// };

	private Site mapRowToSite(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setId(results.getLong("site_id"));
		theSite.setCampground(results.getLong("campground_id"));
		theSite.setNumber(results.getInt("site_number"));
		theSite.setMaxOccupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesAvailableForDateRange(long campgroundID, LocalDate fromDate, LocalDate toDate,
			boolean accessible, int rvLength, boolean utilities, int occupancy) throws IllegalArgumentException {
		if (fromDate == null || toDate == null)
			throw new IllegalArgumentException("Date entered cannot be null");
		if (fromDate.isAfter(toDate))
			throw new IllegalArgumentException("Start date cannot be after end date");

		List<Site> sites = new ArrayList<>();

		String sqlGetAvailableSites = "SELECT site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities FROM site "
				+ "WHERE campground_id = ? AND accessible = ? AND max_rv_length > ? AND utilities = ? AND max_occupancy > ? AND site_id NOT IN "
				+ "(SELECT DISTINCT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS (?, ?))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, campgroundID, accessible, rvLength,
				utilities, occupancy, java.sql.Date.valueOf(fromDate), java.sql.Date.valueOf(toDate));
		while (results.next()) {
			Site theSite = mapRowToSite(results);
			sites.add(theSite);
		}
		return sites;

		// return jdbcTemplate.query("SELECT max_occupancy, accessible, max_rv_length,
		// utilities FROM site "
		// + "WHERE campground_id = ? AND accessible = ? AND max_rv_length > ? AND
		// utilities = ? AND max_occupancy > ? AND site_id NOT IN "
		// + "(SELECT DISTINCT site_id FROM reservation WHERE (from_date, to_date)
		// OVERLAPS (?, ?))",
		// mapRowToSite, campgroundID, accessible, rvLength, utilities, occupancy,
		// startDate, endDate);

//	String sqlQueryForSiteInformation = "SELECT (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "+
//			+ "WHERE campground_id = ? AND site_id NOT IN ";

	}
}