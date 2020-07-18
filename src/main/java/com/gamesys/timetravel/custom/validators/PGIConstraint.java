package com.gamesys.timetravel.custom.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PGIValidator.class)
public @interface PGIConstraint {
	String message() default "Invalid PGI number, should start with letter and range should be between 5 and 10";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
