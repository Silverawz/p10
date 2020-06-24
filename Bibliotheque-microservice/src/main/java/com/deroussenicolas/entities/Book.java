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
@Table(name="Book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id_book;
	@NotNull
	@Size(min = 3, max = 35)
	private String book_name;
	@NotNull
	@Size(min = 3, max = 35)
	private String book_author;
	@NotNull
	@Size(min = 3, max = 35)
	private String book_editor;
	@OneToMany(mappedBy="book",cascade = CascadeType.ALL)
	private List <Copy> copy_list;
	@OneToMany(mappedBy="book",cascade = CascadeType.ALL)
	private List <WaitingListReservation> waiting_list_reservation;
	
	public Book() {
		super();
	}

	public Book( @NotNull @Size(min = 3, max = 35) String book_name,
			@NotNull @Size(min = 3, max = 35) String book_author,
			@NotNull @Size(min = 3, max = 35) String book_editor) {
		super();
		this.book_name = book_name;
		this.book_author = book_author;
		this.book_editor = book_editor;
	}

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	
	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public String getBook_editor() {
		return book_editor;
	}

	public void setBook_editor(String book_editor) {
		this.book_editor = book_editor;
	}

	@JsonManagedReference
	public List<Copy> getCopy_list() {
		return copy_list;
	}

	public void setCopy_list(List<Copy> copy_list) {
		this.copy_list = copy_list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@JsonManagedReference
	public List<WaitingListReservation> getWaiting_list_reservation() {
		return waiting_list_reservation;
	}

	public void setWaiting_list_reservation(List<WaitingListReservation> waiting_list_reservation) {
		this.waiting_list_reservation = waiting_list_reservation;
	}

	
}
