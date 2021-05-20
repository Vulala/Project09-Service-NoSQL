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

import com.abernathyclinic.mediscreen.service_nosql.model.Patient;
import com.abernathyclinic.mediscreen.service_nosql.repository.PatientRepository;

/**
 * Main controller of the application, it provide CRUD mapping allowing the user
 * to communicate with a MongoDB database. <br>
 */
@RestController
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping("/")
	public String index() {
		return "Welcome on the Service-NoSQL REST API, targeted to be used as a micro-service to communicate with a MongoDB database.";
	}

	/**
	 * GET mapping to retrieve the history of a {@link Patient} from the database by
	 * using the UUID. <br>
	 * 
	 * @param UUID : of the patient to retrieve
	 * @return the patient if present in the database
	 */
	@GetMapping("/patientHistory/{uuid}")
	public Patient getPatientByUUID(@PathVariable("uuid") UUID uuid) {
		return patientRepository.findPatientByUuid(uuid);

	}

	/**
	 * GET mapping to retrieve the history of all {@link Patient} from the database.
	 * <br>
	 * 
	 * @return all the patients' history present in the database
	 */
	@GetMapping("/patientHistory")
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	/**
	 * POST mapping to save the history of a {@link Patient} in the database. <br>
	 * 
	 * @param patient : to save
	 * @return a success message
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/patientHistory")
	public String savePatient(@RequestBody Patient patient) {
		patientRepository.save(patient);
		return "Patient's notes sucessfully saved";
	}

	/**
	 * PUT mapping to update an existing {@link Patient}'s history in the database.
	 * <br>
	 * 
	 * @param uuid    : of the patient to update
	 * @param patient : the notes to update
	 * @return a success message
	 */
	@PutMapping("/patientHistory/{uuid}")
	public String updatePatient(@PathVariable("uuid") UUID uuid, @RequestBody Patient patient) {
		Patient patientToUpdate = patientRepository.findPatientByUuid(uuid);
		patientToUpdate.setNotes(patient.getNotes());
		patientRepository.save(patientToUpdate);

		return "Patient's notes successfully updated";
	}

	/**
	 * DELETE mapping to delete a {@link Patient}'s history. <br>
	 * 
	 * @param uuid : of the patient to delete the notes
	 * @return a success message
	 */
	@DeleteMapping("/patientHistory/{uuid}")
	public String deletePatient(@PathVariable("uuid") UUID uuid) {
		Patient patient = patientRepository.findPatientByUuid(uuid);
		patientRepository.delete(patient);

		return "The patient's notes has been successfully deleted in the database.";
	}
}
