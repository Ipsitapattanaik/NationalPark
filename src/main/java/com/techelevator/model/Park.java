package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	private int id, area, visitors;
	private String name, location, description;
	private LocalDate dayEstablished;
	
	public Park(int id, int area, int visitors, String name, String location, String description, LocalDate dayEstablished)
	{
		this.id = id;
		this.area = area;
		this.visitors = visitors;
		this.name = name;
		this.location = location;
		this.description = description;
		this.dayEstablished = dayEstablished;
	}

	public int getId()
	{
		return id;
	}

	public int getArea()
	{
		return area;
	}

	public int getVisitors()
	{
		return visitors;
	}

	public String getName()
	{
		return name;
	}

	public String getLocation()
	{
		return location;
	}

	public String getDescription()
	{
		return description;
	}

	public LocalDate getDayEstablished()
	{
		return dayEstablished;
	}
}

