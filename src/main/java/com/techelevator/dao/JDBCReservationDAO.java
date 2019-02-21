package com.techelevator.dao;

import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Campground;
import com.techelevator.model.Reservation;

public class JDBCReservationDAO implements ReservationDAO {

	@Override
	public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate)
			throws IllegalArgumentException, InvalidKeyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Reservation> getReservationsByStartDateForCampground(Campground campground) {
		// TODO Auto-generated method stub
		return null;
	}

}
