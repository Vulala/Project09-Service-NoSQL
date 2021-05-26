package com.abernathyclinic.mediscreen.service_nosql.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

/**
 * Entity representing the practitioner's note about a patient. <br>
 * The id is used by the MongoDB NoSQL database while the uuid directly point to
 * a specific patient. <br>
 */
public class PatientNote {

	@Id
	private String id;
	private UUID uuid;
	private String notes;

	public PatientNote() {
	}

	public PatientNote(UUID uuid, String notes) {
		this.uuid = uuid;
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return String.format("Patient note = [id: %s, UUID: '%s', Notes: '%s']", id, uuid, notes);
	}

}
