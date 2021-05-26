package com.abernathyclinic.mediscreen.service_nosql.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.service_nosql.model.PatientNote;
import com.abernathyclinic.mediscreen.service_nosql.repository.PatientNoteRepository;

/**
 * Main controller of the application, it provide CRUD mapping allowing the user
 * to communicate with a MongoDB database. <br>
 */
@RestController
public class PatientNoteController {

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	@GetMapping("/")
	public String index() {
		return "Welcome on the Service-NoSQL REST API, targeted to be used as a micro-service to communicate with a MongoDB database.";
	}

	/**
	 * GET mapping to retrieve the history of a {@link PatientNote} from the
	 * database by using the UUID. <br>
	 * 
	 * @param UUID : of the patient to retrieve
	 * @return the patient if present in the database
	 */
	@GetMapping("/patientHistory/{uuid}")
	public PatientNote getPatientNoteByUUID(@PathVariable("uuid") UUID uuid) {
		return patientNoteRepository.findPatientNoteByUuid(uuid);

	}

	/**
	 * GET mapping to retrieve the history of all {@link PatientNote} from the
	 * database. <br>
	 * 
	 * @return all the patients' history present in the database
	 */
	@GetMapping("/patientHistory")
	public List<PatientNote> getAllPatientsNotes() {
		return patientNoteRepository.findAll();
	}

	/**
	 * POST mapping to save the history of a {@link PatientNote} in the database.
	 * <br>
	 * 
	 * @param patientNote : to save
	 * @return a success message
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/patientHistory")
	public String savePatientNote(@RequestBody PatientNote patientNote) {
		patientNoteRepository.save(patientNote);
		return "Patient's notes sucessfully saved";
	}

	/**
	 * PUT mapping to update an existing {@link PatientNote}'s history in the
	 * database. <br>
	 * 
	 * @param uuid        : of the patient to update
	 * @param patientNote : the notes to update
	 * @return a success message
	 */
	@PutMapping("/patientHistory/{uuid}")
	public String updatePatientNote(@PathVariable("uuid") UUID uuid, @RequestBody PatientNote patientNote) {
		PatientNote patientNoteToUpdate = patientNoteRepository.findPatientNoteByUuid(uuid);
		patientNoteToUpdate.setNotes(patientNote.getNotes());
		patientNoteRepository.save(patientNoteToUpdate);

		return "Patient's notes successfully updated";
	}

	/**
	 * DELETE mapping to delete a {@link PatientNote}'s history. <br>
	 * 
	 * @param uuid : of the patient to delete the notes
	 * @return a success message
	 */
	@DeleteMapping("/patientHistory/{uuid}")
	public String deletePatientNote(@PathVariable("uuid") UUID uuid) {
		PatientNote patientNote = patientNoteRepository.findPatientNoteByUuid(uuid);
		patientNoteRepository.delete(patientNote);

		return "The patient's notes has been successfully deleted in the database.";
	}
}
