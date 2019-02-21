package com.techelevator.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAvailableSitesByCampground(long campground_id, LocalDate start_date, LocalDate end_date) {
		List<Site> sites = new ArrayList<>();
String sqlGetAvailableSitesByCampground = "SELECT * "+
											"FROM site "+
											"WHERE campground_id = ? ";
SqlRowSet results =jdbcTemplate.queryForRowSet(sqlGetAvailableSitesByCampground, campground_id);
		while(results.next()) {
			Site theSite = mapRowToSite(results);
			String sqlGetReservations = "SELECT * "+
										"FROM reservation "+
										"Where site_id = ?";
			SqlRowSet results2=jdbcTemplate.queryForRowSet(sqlGetReservations, theSite.getId());
			while(results2.next()) {
				Reservation theReservation = mapRowToReservation(results2);
			}
		}
											
											
											return sites;
	}

	@Override
	public List<Site> getAvailableSitesByPark(long park_id, LocalDate start_date, LocalDate end_date) {
		List<Site> sites = new ArrayList<>();
		
		return sites;
	}

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
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation = new Reservation();
		theReservation.setId(results.getLong("reservation_id"));
		theReservation.setSiteId(results.getLong("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation
		return theReservation;
	}

}
