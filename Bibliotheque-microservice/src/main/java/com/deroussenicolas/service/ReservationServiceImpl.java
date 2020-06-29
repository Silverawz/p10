package com.deroussenicolas.service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.entities.Reservation;

@Service("reservationServiceImpl")
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	private Calendar calendar;
	private YearMonth yearMonthObject;
	@Override
	public Reservation saveExtendReservation(int id) {
		Reservation reservation = reservationRepository.findById(id);
		String date_end_reservation = reservation.getDate_end();
		
		String day_DateEnd_reservation = date_end_reservation.split("\\.")[0];
		String month_DateEnd_reservation = date_end_reservation.split("\\.")[1];	
		String year_DateEnd_reservation = date_end_reservation.split("\\.")[2];	
		int day_DateEnd_FromUser_asInt = Integer.parseInt(day_DateEnd_reservation);
		int month_DateEnd_FromUser_asInt = Integer.parseInt(month_DateEnd_reservation);
		int year_DateEnd_FromUser_asInt = Integer.parseInt(year_DateEnd_reservation);	
		calendar = Calendar.getInstance();
		int yearNow = calendar.get(Calendar.YEAR);
		int monthNow = calendar.get(Calendar.MONTH);
		//need to add +1 because Calendar Month Value is 0-11 and i want 1-12
		monthNow++;
		int dayNow = calendar.get(Calendar.DAY_OF_MONTH);
	
		yearMonthObject = YearMonth.of(yearNow, monthNow);
		int totalDaysInCurrentMonth = yearMonthObject.lengthOfMonth();
	
		// user year reservation ending is superior to the actual year from now
		if(year_DateEnd_FromUser_asInt > yearNow) {			
			//adding 28 days
			String date_end = add28daysToTheReservationDateEnd(day_DateEnd_FromUser_asInt, month_DateEnd_FromUser_asInt, year_DateEnd_FromUser_asInt);
			reservation.setDate_end(date_end);	
		}

		

		// user year reservation ending is inferior to the actual year from now
		else if(year_DateEnd_FromUser_asInt < yearNow) {
			
			//set the begin of the reservation to now (updating the date because it's too old to be used)
			if(dayNow < 10 && monthNow < 10) {
				reservation.setDate_begin("0"+dayNow+".0"+monthNow+"."+yearNow);	
			}
			else if(dayNow > 10 && monthNow < 10) {
				reservation.setDate_begin(dayNow+".0"+monthNow+"."+yearNow);
			}
			else if(dayNow < 10 && monthNow > 10){
				reservation.setDate_begin("0"+dayNow+"."+monthNow+"."+yearNow);	
			}
			else {
				reservation.setDate_begin(dayNow+"."+monthNow+"."+yearNow);	
			}
	
			
			//set the end of the reservation
			String dayAndMonth_end_reservation = checkIfWeStayInTheSameMonth(dayNow,monthNow,totalDaysInCurrentMonth);
			//it's december , year +1
			
			if(monthNow == 12) {
				reservation.setDate_end(dayAndMonth_end_reservation+"."+(yearNow+1));
			}
			//not december, we keep the actual year
			else if(monthNow != 12) {
				reservation.setDate_end(dayAndMonth_end_reservation+"."+yearNow);
			}		
		}

		
		
		// user year reservation ending is equal to the actual year from now
		else if(year_DateEnd_FromUser_asInt == yearNow) {		
			String date_end = add28daysToTheReservationDateEnd(day_DateEnd_FromUser_asInt, month_DateEnd_FromUser_asInt, year_DateEnd_FromUser_asInt);
			reservation.setDate_end(date_end);			
		}
		
	
		reservationRepository.save(reservation);
		return reservation;
	}


	
	public String checkIfWeStayInTheSameMonth(int dayNow, int monthNow, int totalDaysInCurrentMonth) {
		int dayUpdateWith4MoreWeeks = dayNow + 28;
		String result = "";
		
		if(dayUpdateWith4MoreWeeks <= totalDaysInCurrentMonth) {
			if(monthNow < 10) {
				result = dayUpdateWith4MoreWeeks+".0"+monthNow;
			}
			else {
				result = dayUpdateWith4MoreWeeks+"."+monthNow;
			}		
		}
		else if(dayUpdateWith4MoreWeeks > totalDaysInCurrentMonth) {
			int difference_DayNowAndDay_EndMonth = totalDaysInCurrentMonth - dayNow;
			int dayOfNextMonth = 28 - difference_DayNowAndDay_EndMonth;
			if(monthNow != 12) {
				if(monthNow+1 < 10) {
					if(dayOfNextMonth < 10) {
						result = "0"+dayOfNextMonth+".0"+(monthNow+1);	
					}
					else {
						result = dayOfNextMonth+".0"+(monthNow+1);
					}
				}
				else {
					if(dayOfNextMonth < 10) {
						result = "0"+dayOfNextMonth+"."+(monthNow+1);	
					}
					else {
						result = dayOfNextMonth+"."+(monthNow+1);
					}
				}
			}
			else if(monthNow == 12) {
				if(dayOfNextMonth < 10) {
					result = "0"+dayOfNextMonth+".01";
				}
				else {
					result = dayOfNextMonth+".01";
				}
			}		
		}
		return result;
	}
	
	
	public String add28daysToTheReservationDateEnd(int day, int month, int year) {
		String result = "";
		yearMonthObject = YearMonth.of(year, month);
		int totalDaysInFutureMonth = yearMonthObject.lengthOfMonth();
		int dayUpdateWith4MoreWeeks = day + 28;
		if(dayUpdateWith4MoreWeeks <= totalDaysInFutureMonth) {
			if(dayUpdateWith4MoreWeeks >= 10) {
				if(month < 10) {
					result = dayUpdateWith4MoreWeeks+".0"+month+"."+year;
				}
				else {
					result = dayUpdateWith4MoreWeeks+"."+month+"."+year;
				}		
			}
			else if(dayUpdateWith4MoreWeeks < 10) {
				if(month < 10) {
					result = "0"+dayUpdateWith4MoreWeeks+".0"+month+"."+year;
				}
				else {
					result = "0"+dayUpdateWith4MoreWeeks+"."+month+"."+year;
				}	
			}
		}
		else if(dayUpdateWith4MoreWeeks > totalDaysInFutureMonth) {
			int difference_DayNowAndDay_EndMonth = totalDaysInFutureMonth - day;
			int dayOfNextMonth = 28 - difference_DayNowAndDay_EndMonth;
			if(month != 12) {
				if(month < 10) {
					if(dayOfNextMonth < 10) {
						if((month+1) != 10) {
							result = "0"+dayOfNextMonth+".0"+(month+1)+"."+year;
						}
						else {
							result = "0"+dayOfNextMonth+"."+(month+1)+"."+year;
						}			
					}
					else {
						if((month+1) != 10) {
							result = dayOfNextMonth+".0"+(month+1)+"."+year;
						}
						else {
							result = dayOfNextMonth+"."+(month+1)+"."+year;
						}
					}
				}
				else {
					if(dayOfNextMonth < 10) {
						result = "0"+dayOfNextMonth+"."+(month+1)+"."+year;
					}
					else {
						result = dayOfNextMonth+"."+(month+1)+"."+year;
					}
				}		
			}
			else if(month == 12) {
				if(dayOfNextMonth < 10) {
					result = "0"+dayOfNextMonth+".01."+(year+1);
				}
				else {
					result = dayOfNextMonth+".01."+(year+1);
				}			
			}	
		}	
		return result;
	}



	@Override
	public List<Reservation> reservationListOfUser(int id_user) {
		return reservationRepository.reservationListOfUser(id_user);
	}



	@Override
	public Reservation findById(int id) {
		return reservationRepository.findById(id);
	}



	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}



	@Override
	public List<Reservation> reservationListNotArchived(boolean b) {
		return reservationRepository.reservationListNotArchived(b);
	}



	@Override
	public List<Date> lastRevervationForEachBooks() {
		List<Date> listOfTheLastReservationForeachBooksByOrder = new ArrayList<>();	
		//recuperer une liste de reservation = au nombre de livre avec leur date de retour la plus proche
		List<Reservation> allReservationsNotArchived = reservationRepository.reservationListNotArchived(false);
		for (int i = 0 ; i < bookRepository.findAll().size() ; i++) {
			List<Reservation> reservationsForCurrentBookAsIndexAsI = new ArrayList<>();
			for (Reservation reservation : allReservationsNotArchived) {
				if(reservation.getCopy().getBook().getId_book() == bookRepository.findAll().get(i).getId_book()) {
					reservationsForCurrentBookAsIndexAsI.add(reservation);
				}
			}			
			if(reservationsForCurrentBookAsIndexAsI.size() != 0) {
				listOfTheLastReservationForeachBooksByOrder.add(compareDateOfReservations(reservationsForCurrentBookAsIndexAsI));
			}
			else {
				listOfTheLastReservationForeachBooksByOrder.add(null);
			}						
		}
		
		return listOfTheLastReservationForeachBooksByOrder;
	}

	
	
	public Date compareDateOfReservations(List<Reservation> listToCompareDate) {
		List<Date> dates = new ArrayList<>();
		for (Reservation reservation : listToCompareDate) {
			try {
				dates.add(new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(reservation.getDate_end()));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		return getNearestDate(dates, new Date());
	}
	
	
	public Date getNearestDate(List<Date> dates, Date currentDate) {
		  long minDiff = -1, currentTime = currentDate.getTime();
		  Date minDate = null;
		  for (Date date : dates) {
		    long diff = Math.abs(currentTime - date.getTime());
		    if ((minDiff == -1) || (diff < minDiff)) {
		      minDiff = diff;
		      minDate = date;
		    }
		  }
		  return minDate;
	}

	

}
