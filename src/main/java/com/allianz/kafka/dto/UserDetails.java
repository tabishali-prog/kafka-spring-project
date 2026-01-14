package com.allianz.kafka.dto;

public class UserDetails {
	
	private int userId;
    private PersonalDetailsObject personalDetails;
    private ContactMs contact;
    private String status;
    private String department;
    
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public PersonalDetailsObject getPersonalDetails() {
		return personalDetails;
	}
	public void setPersonalDetails(PersonalDetailsObject personalDetails) {
		this.personalDetails = personalDetails;
	}
	public ContactMs getContact() {
		return contact;
	}
	public void setContact(ContactMs contact) {
		this.contact = contact;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}	

    
}
