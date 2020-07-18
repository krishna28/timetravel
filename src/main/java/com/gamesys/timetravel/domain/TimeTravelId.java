package com.gamesys.timetravel.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamesys.timetravel.custom.validators.PGIConstraint;

@Embeddable
public class TimeTravelId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4815634905710913038L;

	@PGIConstraint
	@Column(updatable = false, nullable = false, length = 10)
	private String personalGalacticIdentifier;

	@NotBlank(message = "Place is required")
	@Column(updatable = false, nullable = false)
	private String place;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(updatable = false, nullable = false)
	private LocalDate date;

	public TimeTravelId() {
		// TODO Auto-generated constructor stub
	}

	public TimeTravelId(String personalGalacticIdentifier, String place, LocalDate date) {
		this.personalGalacticIdentifier = personalGalacticIdentifier;
		this.place = place;
		this.date = date;
	}

	public String getPersonalGalacticIdentifier() {
		return personalGalacticIdentifier;
	}

	public void setPersonalGalacticIdentifier(String personalGalacticIdentifier) {
		this.personalGalacticIdentifier = personalGalacticIdentifier;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date2) {
		this.date = date2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((personalGalacticIdentifier == null) ? 0 : personalGalacticIdentifier.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeTravelId other = (TimeTravelId) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (personalGalacticIdentifier == null) {
			if (other.personalGalacticIdentifier != null)
				return false;
		} else if (!personalGalacticIdentifier.equals(other.personalGalacticIdentifier))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeTravelId [personalGalacticIdentifier=" + personalGalacticIdentifier + ", place=" + place + ", date="
				+ date + "]";
	}
	
	

}