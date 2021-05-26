package com.abernathyclinic.mediscreen.service_nosql.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.abernathyclinic.mediscreen.service_nosql.model.PatientNote;
import com.abernathyclinic.mediscreen.service_nosql.repository.PatientNoteRepository;

/**
 * Important note : This test class is working but nothing is rolledback after
 * each tests. <br>
 * So it might work the first time you launch the test if all the data for the
 * test are present in the DB, but will then not anymore, unless you populate
 * the database again with the value deleted (and delete the data posted). <br>
 */
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PatientNoteRepositoryIT {

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	private UUID uuid = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(patientNoteRepository).isNotNull();
	}

	@DisplayName("GET : /patient/{uuid}")
	@Test
	void givenGettingASpecificPatientNoteUsingTheUUID_whenFindPatientNoteByUUID_thenItReturnTheRightPatientNoteFromTheDataBase() {
		assertEquals("notes", patientNoteRepository.findPatientNoteByUuid(uuid).getNotes());
	}

	@DisplayName("GET : /patient")
	@Test
	void givenGettingAllPatientsNotes_whenFindAllPatientsNotes_thenItReturnAllThePatientsNotesFromTheDataBase() {
		assertFalse(patientNoteRepository.findAll().isEmpty());
	}

	@DisplayName("POST : /patient")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() {
		UUID randomUUID = UUID.randomUUID();
		PatientNote patientToSave = new PatientNote(randomUUID, "notesSave");
		patientNoteRepository.save(patientToSave);

		assertEquals("notesSave", patientNoteRepository.findPatientNoteByUuid(randomUUID).getNotes());
	}

	@DisplayName("PUT : /patient/{uuid}")
	@Test
	void givenUpdatingAPatientNote_whenUpdatePatientNote_thenItUpdateThePatientNoteInTheDataBase() {
		PatientNote patientToUpdate = patientNoteRepository
				.findPatientNoteByUuid(UUID.fromString("7798a960-ee17-4b83-b355-fc3549322cc6"));
		patientToUpdate.setNotes("notesPutted");
		patientNoteRepository.save(patientToUpdate);

		assertEquals("notesPutted", patientNoteRepository
				.findPatientNoteByUuid(UUID.fromString("7798a960-ee17-4b83-b355-fc3549322cc6")).getNotes());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() {
		patientNoteRepository.delete(
				patientNoteRepository.findPatientNoteByUuid(UUID.fromString("097252bc-12c4-41ac-b831-8b9b8e5bba59")));
		assertEquals(6, patientNoteRepository.findAll().size());
	}

}
