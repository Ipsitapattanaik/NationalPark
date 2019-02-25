package com.techelevator.model;

import java.time.LocalDate;

public class Reservation {
	private long reservation_id;
	private long id, siteId;
	private String name;
	LocalDate fromDate;
	LocalDate toDate, createDate;
	
	public Reservation() {
	}
	
	public Reservation(long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		this.siteId = siteId;
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSiteId() {
		return siteId;
	}

	public String getName() {
		return name;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFromDate(LocalDate arrivalDate) {
		this.fromDate = arrivalDate;
	}

	public void setToDate(LocalDate departureDate) {
		this.toDate = departureDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public long getReservation_id() {
		
		return reservation_id;
	}

	public void setReservation_id(long reservation_id) {
		this.reservation_id = reservation_id;
	}

	
}
