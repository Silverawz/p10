package com.deroussenicolas.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deroussenicolas.entities.Copy;

@Repository
public interface CopyRepository extends JpaRepository <Copy, Long>{
	
	@Query("from Copy where id_copy=?1")
	Copy findById(int id);
	
	@Query("from Copy where status=?1")
	List<Copy> findCopiesAvailable(char status);
	
	@Query("from Copy where status=?1 and book_id_book=?2")
	List<Copy> findCopiesAvailableByBookId(char status, int book_id);	
}
