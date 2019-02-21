package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<>();
		String sqlGetAllParks = "SELECT * " + "FROM park " + "ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}

	@Override
	public Park getParkByName(String parkName) {
		Park thePark = new Park();
		String sqlGetParkByNameAndState = "SELECT * " + "FROM park " + "WHERE name = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkByNameAndState, parkName);
		if (results.next()) {
			thePark = mapRowToPark(results);
			
		}
		return thePark;
	}

	@Override
	public Park getParkById(long id) {
		Park thePark = new Park();
		String sqlGetParkById = "SELECT * " + "FROM park " + "WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkById, id);
		if (results.next()) {
			thePark = mapRowToPark(results);
		}
		return thePark;
	}

	@Override
	public List<Park> getParksByState(String state) {
		List<Park> parks = new ArrayList<>();
		String sqlGetParksByState = "SELECT * "+
									"FROM park "+
									"WHERE location = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParksByState, state);
		while(results.next()) {
			Park thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setDayEstablished(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
