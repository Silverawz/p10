package com.deroussenicolas.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Reservation")
public class Reservation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id_reservation;
	@NotNull
	@Size(min = 10, max = 10)
	private String date_begin;
	@NotNull
	@Size(min = 10, max = 10)
	private String date_end;
	@NotNull
	private boolean is_archived;
	@ManyToOne
	private Copy copy;
	@ManyToOne
	private User user;
	
	public Reservation() {
		super();
	}

	public int getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(int id_reservation) {
		this.id_reservation = id_reservation;
	}

	public String getDate_begin() {
		return date_begin;
	}

	public void setDate_begin(String date_begin) {
		this.date_begin = date_begin;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public boolean isIs_archived() {
		return is_archived;
	}

	public void setIs_archived(boolean is_archived) {
		this.is_archived = is_archived;
	}

	@JsonBackReference
	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Reservation [id_reservation=" + id_reservation + ", date_begin=" + date_begin + ", date_end=" + date_end
				+ ", is_archived=" + is_archived + ", copy=" + copy + ", user=" + user + "]";
	}
	

}
