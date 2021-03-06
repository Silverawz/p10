package com.deroussenicolas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.WaitingListReservation;

@Service("WaitingListServiceImpl")
public class WaitingListReservationServiceImpl implements WaitingListService {

	@Autowired
	private WaitingListReservationRepository waitingListRepository;
	@Autowired
	private BookService bookService;
	
	@Override
	public List<WaitingListReservation> findAll() {
		return waitingListRepository.findAll();
	}

	@Override
	public List<Boolean> checkIfUserHasMadeAReservationForEchBooks(List<Boolean> checkingList, int user_id) {
		List<Boolean> resultList = checkingList;
		List<WaitingListReservation> waitingListReservationOfUser = waitingListRepository.waitingListReservationOfUserWithParams(user_id, false, false);
		List<Book> listAllBooks = bookService.findAll();
		
		for (int i = 0; i < listAllBooks.size(); i++) {
			A : for (WaitingListReservation waitingListReservation : waitingListReservationOfUser) {
				if(listAllBooks.get(i).getId_book() == waitingListReservation.getBook().getId_book()) {
					resultList.set(i, true);
					break A;
				}
			}				
			
		}
		return resultList;
	}

	@Override
	public List<WaitingListReservation> waitingListReservationOfBookWithParams(int book_id, boolean is_archived, boolean is_canceled) {
		return waitingListRepository.waitingListReservationOfBookWithParams(book_id, is_archived, is_canceled);
	}

	@Override
	public WaitingListReservation waitingListReservationOfUserBookWithParamsForASingleObject(int user_id, int book_id,
			boolean is_archived, boolean is_canceled) {
		return waitingListRepository.waitingListReservationOfUserBookWithParamsForASingleObject(user_id, book_id, is_archived, is_canceled);
	}

	@Override
	public WaitingListReservation waitingListReservationById(int id_waiting_list_reservation) {
		return waitingListRepository.waitingListReservationById(id_waiting_list_reservation);
	}

	@Override
	public void save(WaitingListReservation waitingListReservation) {
		waitingListRepository.save(waitingListReservation);	
	}

	@Override
	public List<WaitingListReservation> waitingListReservationOfUserWithParamsArchived(int user_id, boolean b) {
		return waitingListRepository.waitingListReservationOfUserWithParamsArchived(user_id, b);
	}
	
	
}
