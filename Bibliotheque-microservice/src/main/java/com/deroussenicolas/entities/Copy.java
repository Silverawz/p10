package com.deroussenicolas.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="Copy")
public class Copy implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id_copy;
	@NotNull
	private char status;
	@ManyToOne
	private Book book;
	@OneToMany(mappedBy="copy",cascade = CascadeType.ALL)
	private List <Reservation> reservation_list;
	
	public Copy() {
		super();
	}

	public int getId_copy() {
		return id_copy;
	}

	public void setId_copy(int id_copy) {
		this.id_copy = id_copy;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	@JsonBackReference
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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
		return "Copy [id_copy=" + id_copy + ", status=" + status + ", book=" + book + ", reservation_list="
				+ reservation_list + "]";
	}
	
}
