package com.deroussenicolas.beans;

import java.io.Serializable;

import java.util.List;



public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_user;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private boolean active;
	private List <ReservationBean> reservation_list;
	
	public UserBean() {
		super();
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public List<ReservationBean> getReservation_list() {
		return reservation_list;
	}

	public void setReservation_list(List<ReservationBean> reservation_list) {
		this.reservation_list = reservation_list;
	}

	@Override
	public String toString() {
		return "UserBean [id_user=" + id_user + ", firstname=" + firstname + ", lastname=" + lastname + ", email="
				+ email + ", password=" + password + ", active=" + active + ", reservation_list=" + reservation_list
				+ ", getId_user()=" + getId_user() + ", getFirstname()=" + getFirstname() + ", getLastname()="
				+ getLastname() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword() + ", isActive()="
				+ isActive() + ", getReservation_list()=" + getReservation_list() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
