package com.techelevator.model;

public class Site {
	private int id, number, maxOccupancy, maxRVLength;
	private boolean accessible, utilities;
	
	public Site(int id, int number, int maxOccupancy, int maxRVLength, boolean accessible, boolean utilities)
	{
		this.id = id;
		this.number = number;
		this.maxOccupancy = maxOccupancy;
		this.maxRVLength = maxRVLength;
		this.accessible = accessible;
		this.utilities = utilities;
	}
	
	public int getId()
	{
		return id;
	}
	public int getNumber()
	{
		return number;
	}
	public int getMaxOccupancy()
	{
		return maxOccupancy;
	}
	public int getMaxRVLength()
	{
		return maxRVLength;
	}
	public boolean isAccessible()
	{
		return accessible;
	}
	public boolean isUtilities()
	{
		return utilities;
	}
}
