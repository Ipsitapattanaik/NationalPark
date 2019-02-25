package com.techelevator.model;

import java.time.LocalDate;

public class Reservation {
	private long id, siteId;
	private String name;
	LocalDate fromDate, toDate, createDate;
	
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

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
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
