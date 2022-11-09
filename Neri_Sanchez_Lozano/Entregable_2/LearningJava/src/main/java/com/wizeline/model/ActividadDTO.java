package com.wizeline.model;

public class ActividadDTO {
	
	private String activity;
	private String type;
	private int participants;
	private int price;
	private String link;
	private String key;
	private Float accessibility;
	
	//Contructor default
	public ActividadDTO() {};
	//Contructor usando campos
	public ActividadDTO(String activity, String type, int participants, int price, String link, String key,
			Float accessibility) {
		this.activity = activity;
		this.type = type;
		this.participants = participants;
		this.price = price;
		this.link = link;
		this.key = key;
		this.accessibility = accessibility;
	}
	
	//Constructor sobrecargado
	public ActividadDTO(String activity, String type, int participants) {
		this.activity = activity;
		this.type = type;
		this.participants = participants;
	}
	
	
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getParticipants() {
		return participants;
	}
	public void setParticipants(int participants) {
		this.participants = participants;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Float getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(Float accessibility) {
		this.accessibility = accessibility;
	}
	
	
	

}
