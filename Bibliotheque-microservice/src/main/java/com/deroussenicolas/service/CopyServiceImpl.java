package com.deroussenicolas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.entities.Copy;

@Service("CopyServiceImpl")
public class CopyServiceImpl implements CopyService {

	@Autowired
	private CopyRepository copyRepository;
	
	@Override
	public List<Copy> findAll() {
		return copyRepository.findAll();
	}

	@Override
	public Copy findById(int id) {
		return copyRepository.findById(id);
	}

	@Override
	public List<Copy> findCopiesAvailable(char c) {
		return copyRepository.findCopiesAvailable(c);
	}

	@Override
	public void save(Copy copyOfTheReservation) {
		copyRepository.save(copyOfTheReservation);	
	}

}
