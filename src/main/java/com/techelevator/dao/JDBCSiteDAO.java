package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.techelevator.model.Site;

public class JDBCSiteDAO implements SiteDAO {
	private JdbcTemplate db;

	private RowMapper<Site> mapRowToSite = (row, pos) -> {
		Site s = new Site();

		return s;
	};

	public JDBCSiteDAO(DataSource dataSource) {
		this.db = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesAvailableForDateRange(long campgroundID, LocalDate startDate, LocalDate endDate, boolean accessible, int max_rv_length, boolean utilities, int max_occupancy)
			throws IllegalArgumentException {
		if (startDate == null || endDate == null)
			throw new IllegalArgumentException("Date entered cannot be null");
		if (startDate.isAfter(endDate))
			throw new IllegalArgumentException("Start date cannot be after end date");

		return db.query("SELECT (site_id, site_number, max_occupancy, accessible, max_rv_length, utilities) FROM site "
				+ "WHERE campground_id = ? AND accessible = ? AND max_rv_length > ? AND utilities = ? AND max_occupancy > ? AND site_id NOT IN "
				+ "(SELECT DISTINCT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS (?, ?)) LIMIT 5",
				mapRowToSite, campgroundID, accessible, max_rv_length, utilities, max_occupancy, startDate, endDate);

	}
}