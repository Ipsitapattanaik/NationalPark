package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;

public class JDBCCampgroundDAO implements CampgroundDAO{

private JdbcTemplate jdbcTemplate;
	

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundsByParkID(long id) {
			List<Campground> campgrounds = new ArrayList<>();
			
			String sqlFindCampgroundById = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee "+
								   "FROM campground "+
								   "WHERE park_id = ?";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCampgroundById, id);
			while(results.next()) {
				Campground theCampground = mapRowToCampground(results);
				campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	
	
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		
		theCampground.setId(results.getLong("campground_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFrom(results.getInt("open_from_mm"));
		theCampground.setOpenTo(results.getInt("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}


}
