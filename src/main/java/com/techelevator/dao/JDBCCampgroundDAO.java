package com.techelevator.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;

public class JDBCCampgroundDAO implements CampgroundDAO{

private JdbcTemplate jdbcTemplate;
	
	public void JDBCCityDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundsByParkID(long id) {
			List<Campground> campgrounds = null;
			
			String sqlFindCampgroundById = "SELECT id, name, openFrom, openTo, dailyFee"+
								   "FROM campground "+
								   "WHERE id = ?";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCampgroundById, id);
			if(results.next()) {
				Campground theCampground = mapRowToCampground(results);
				campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	
	
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		
		theCampground.setId(results.getLong("id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFrom(results.getInt("openFrom"));
		theCampground.setOpenTo(results.getInt("openTo"));
		theCampground.setDailyFee(results.getBigDecimal("dailyFee"));
		return theCampground;
	}


}
