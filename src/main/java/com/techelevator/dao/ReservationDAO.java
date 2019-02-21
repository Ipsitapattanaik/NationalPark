package com.techelevator.dao;

import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Campground;
import com.techelevator.model.Reservation;

public interface ReservationDAO
{
	int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) throws IllegalArgumentException, InvalidKeyException;
	List<Reservation> getReservationsByStartDateForCampground(Campground campground);

}
