package com.gamesys.timetravel.custom.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PGIValidator implements ConstraintValidator<PGIConstraint, String> {

	@Override
	public void initialize(PGIConstraint pgi) {
	}

	@Override
	public boolean isValid(String pgi, ConstraintValidatorContext cxt) {

		String regex = "^[^0-9][a-zA-Z0-9]+$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pgi);

		return pgi != null && matcher.matches() && (pgi.length() >= 5) && (pgi.length() <= 10);
	}

}