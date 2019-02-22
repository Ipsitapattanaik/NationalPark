package com.techelevator.dao;

import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.model.Campground;
import com.techelevator.model.Reservation;


public class JDBCReservationDAO implements ReservationDAO {

private JdbcTemplate jdbcTemplate;
	//build reservation

@Override
public List<Reservation> getReservationsByStartDateForCampground(Campground campground) {
	List<Reservation> reservation = new ArrayList<>();
	String sqlGetReservationByStartDate = "SELECT * FROM reservation JOIN site ON reservation.site_id = site.site_id "
			+ "WHERE campground_id = ? AND from_date BETWEEN current_date AND current_date + INTERVAL '30 day'";
	SqlRowSet Results = jdbcTemplate.queryForRowSet(sqlGetReservationByStartDate , campground.getId());
	while(Results.next()) {
		
		reservation.add(mapRowToReservation(Results));
	}
	return reservation;
}

private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation;
		theReservation = new Reservation(0, null, null, null);
				theReservation.setSiteId(results.getLong("site_id"));
				theReservation.setName(results.getString("name"));
				theReservation.setFromDate(results.getDate("from_date").toLocalDate()); 
				theReservation.setToDate(results.getDate("to_date").toLocalDate());
		theReservation.setId(results.getInt("reservation_id"));
		theReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return theReservation;
	};
	
	
	public JDBCReservationDAO(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) throws IllegalArgumentException, InvalidKeyException
	{
		if (name == null || fromDate == null || toDate == null) throw new IllegalArgumentException("No parameter can be null");
		if (fromDate.isAfter(toDate)) throw new IllegalArgumentException("Start date cannot be after end date");
		
		try
		{
			return jdbcTemplate.queryForObject("INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
					+ "VALUES (?,?,?,?,?) RETURNING reservation_id", Integer.class, siteId, name, fromDate, toDate, LocalDate.now());
		}
		catch (DataIntegrityViolationException e)
		{
			throw new InvalidKeyException("Site ID was not a valid site");
		}
	}
//
//	@Override
//	public List<Reservation> getReservationsByStartDateForCampground(Campground campground)
//	{
//		return jdbcTemplate.query("SELECT * FROM reservation JOIN site ON reservation.site_id = site.site_id "
//				+ "WHERE campground_id = ? AND from_date BETWEEN current_date AND current_date + INTERVAL '30 day'", mapRowToReservation, campground.getId());
//	}

}

