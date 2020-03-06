package com.deroussenicolas.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deroussenicolas.entities.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{

	@Query("from Book where id_book=?1")
	Book findById(int id);
	
}
