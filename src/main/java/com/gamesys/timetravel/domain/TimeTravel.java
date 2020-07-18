package com.gamesys.timetravel.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;

import com.gamesys.timetravel.domain.base.BaseEntity;

@Entity
@Table(name = "time_travel")
public class TimeTravel extends BaseEntity {

	@Valid
	@EmbeddedId
	TimeTravelId timeTravelId;

	public TimeTravelId getTimeTravelId() {
		return timeTravelId;
	}

	public void setTimeTravelId(TimeTravelId timeTravelId) {
		this.timeTravelId = timeTravelId;
	}

	public TimeTravel() {
		super();
	}

	public TimeTravel(TimeTravelId timeTravelId) {
		this.timeTravelId = timeTravelId;
	}

	@Override
	public String toString() {
		return "TimeTravel [timeTravelId=" + timeTravelId + "]";
	}


	

}
