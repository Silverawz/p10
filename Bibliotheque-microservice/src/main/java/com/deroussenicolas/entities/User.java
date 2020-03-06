package com.deroussenicolas.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id_user;
	@NotNull
	@Size(min = 3, max = 30)
	private String firstname;
	@NotNull
	@Size(min = 3, max = 30)
	private String lastname;
	@NotNull
	private String email;
	@NotNull
	@Size(min = 5, max = 255)
	private String password;
	@NotNull
	private boolean active;
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List <Reservation> reservation_list;
	
	public User() {
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

	@JsonManagedReference
	public List<Reservation> getReservation_list() {
		return reservation_list;
	}

	public void setReservation_list(List<Reservation> reservation_list) {
		this.reservation_list = reservation_list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", active=" + active + ", reservation_list=" + reservation_list + "]";
	}

}
