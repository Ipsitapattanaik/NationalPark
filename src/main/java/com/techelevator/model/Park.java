package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	private long id;
	private int area, visitors;
	private String name, location, description;
	private LocalDate dayEstablished;
	
	public Park(){
		
	}
	
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

	public void setId(long l) {
		this.id = l;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDayEstablished(LocalDate dayEstablished) {
		this.dayEstablished = dayEstablished;
	}

	public long getId()
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

