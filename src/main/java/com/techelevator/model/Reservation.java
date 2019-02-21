package com.techelevator.model;

import java.time.LocalDate;

public class Reservation {
	private int id, siteId;
	private String name;
	LocalDate fromDate, toDate, createDate;
	
	public Reservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {
		this.siteId = siteId;
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return siteId;
	}

	public String getName() {
		return name;
	}

	public LocalDate getFromDate() {
		return fromDate;
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
	
}
