package com.techelevator.model;

public class Site {
	private long id,campground;
	private int number, maxOccupancy, maxRVLength;
	private boolean accessible, utilities;

	public long getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public int getMaxRVLength() {
		return maxRVLength;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public boolean isUtilities() {
		return utilities;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

	public long getCampground() {
		return campground;
	}

	public void setCampground(long campground) {
		this.campground = campground;
	}

}
