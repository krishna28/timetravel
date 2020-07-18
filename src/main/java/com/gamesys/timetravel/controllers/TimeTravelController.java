package com.gamesys.timetravel.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamesys.timetravel.domain.TimeTravel;
import com.gamesys.timetravel.services.TimeTravelService;
import com.gamesys.timetravel.services.ValidationErrorService;

@RestController
@RequestMapping("/api/timetravel")
public class TimeTravelController {

	@Autowired
	TimeTravelService timeTravelService;

	@Autowired
	ValidationErrorService validationErrorService;

	Logger logger = LoggerFactory.getLogger(TimeTravelController.class);

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody TimeTravel timeTravel, BindingResult result) {

		logger.info("Save request received {}", timeTravel.toString());

		ResponseEntity<Map<String, String>> errors = validationErrorService.validate(result);

		if (errors != null) {

			return errors;
		}
		if (timeTravelService.exists(timeTravel)) {
			return new ResponseEntity<>("Request already Exists!", HttpStatus.BAD_REQUEST);
		}
		TimeTravel savedEntity = timeTravelService.save(timeTravel);
		if (savedEntity != null) {
			return new ResponseEntity<TimeTravel>(savedEntity, HttpStatus.CREATED);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public ResponseEntity<Iterable<TimeTravel>> getAll() {
		
		logger.info("get request received");

		return new ResponseEntity<Iterable<TimeTravel>>(timeTravelService.getAll(), HttpStatus.OK);

	}

	@GetMapping("/{pgi}/{place}/{date}")
	public ResponseEntity<TimeTravel> getByPgiAndPlaceAndDate(@PathVariable String pgi, @PathVariable String place,
			@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {

		logger.info("get request received {} ,{} , {}", pgi, place, date);

		Optional<TimeTravel> timeTravel = timeTravelService.getByPgiAndPlaceAndDate(pgi, place, date);

		if (timeTravel.isPresent()) {
			return new ResponseEntity<TimeTravel>(timeTravel.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/allByDate/{date}")
	public ResponseEntity<List<TimeTravel>> getAllByDate(
			@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
		
		logger.info("get allByDate request received {}", date);

		List<TimeTravel> timeTravel = timeTravelService.getAllByDate(date);
		return new ResponseEntity<List<TimeTravel>>(timeTravel, HttpStatus.OK);
	}

	@GetMapping("/allByPGI/{pgi}")
	public ResponseEntity<List<TimeTravel>> getAllByPgi(@PathVariable String pgi) {
		
		logger.info("get allByPGI request received {}", pgi);

		List<TimeTravel> timeTravels = timeTravelService.getAllByPgi(pgi);

		return new ResponseEntity<List<TimeTravel>>(timeTravels, HttpStatus.OK);

	}

	@DeleteMapping("/{pgi}/{place}/{date}")
	public ResponseEntity<?> delete(@PathVariable String pgi, @PathVariable String place,
			@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {

		logger.info("delete  request received {} , {}, {}", pgi, place, date);
		
		timeTravelService.delete(pgi, place, date);

		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
