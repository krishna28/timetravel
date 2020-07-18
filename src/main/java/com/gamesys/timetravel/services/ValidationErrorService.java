package com.gamesys.timetravel.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidationErrorService {

	public ResponseEntity<Map<String, String>> validate(BindingResult result) {

		if (result.hasErrors()) {

			Map<String, String> errorMessages = new HashMap<>();

			for (FieldError fieldError : result.getFieldErrors()) {
				errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(errorMessages, HttpStatus.BAD_REQUEST);
		}
		return null;

	}

}
