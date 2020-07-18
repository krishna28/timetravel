package com.gamesys.timetravel.domain.base;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public class BaseEntity {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime dateCreated;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime lastUpdated;

	public BaseEntity() {
		super();
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	@PrePersist
	protected void onCreate() {
		this.dateCreated = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdated = LocalDateTime.now();
	}

}