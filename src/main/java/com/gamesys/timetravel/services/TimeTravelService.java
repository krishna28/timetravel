package com.gamesys.timetravel.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamesys.timetravel.domain.TimeTravel;
import com.gamesys.timetravel.domain.TimeTravelId;
import com.gamesys.timetravel.repositories.TimeTravelRepository;

@Service
public class TimeTravelService {

	@Autowired
	TimeTravelRepository timeTravelRepository;

	public TimeTravel save(TimeTravel timeTravel) {

		return timeTravelRepository.save(timeTravel);

	}

	public Iterable<TimeTravel> getAll() {

		return timeTravelRepository.findAll();
	}

	public Optional<TimeTravel> getByPgiAndPlaceAndDate(String pgi, String place, LocalDate date) {

		return timeTravelRepository.findById(new TimeTravelId(pgi, place, date));

	}

	public List<TimeTravel> getAllByPgi(String pgi) {

		return timeTravelRepository.findByTimeTravelIdPersonalGalacticIdentifier(pgi);
	}

	public List<TimeTravel> getAllByDate(LocalDate date) {

		return timeTravelRepository.findByTimeTravelIdDate(date);
	}

	public void delete(String pgi, String place, LocalDate date) {
		timeTravelRepository.deleteById(new TimeTravelId(pgi, place, date));
	}

	public boolean exists(TimeTravel timeTravel) {
		return timeTravelRepository.existsById(timeTravel.getTimeTravelId());

	}

	public void deleteAll() {
		timeTravelRepository.deleteAll();
	}

}
