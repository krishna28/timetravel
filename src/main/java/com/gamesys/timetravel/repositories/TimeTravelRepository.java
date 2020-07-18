package com.gamesys.timetravel.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gamesys.timetravel.domain.TimeTravel;
import com.gamesys.timetravel.domain.TimeTravelId;

public interface TimeTravelRepository extends CrudRepository<TimeTravel, TimeTravelId> {

	List<TimeTravel> findByTimeTravelIdPersonalGalacticIdentifier(String personalGalacticIdentifier);

	List<TimeTravel> findByTimeTravelIdDate(LocalDate date);

}
