package com.abernathyclinic.mediscreen.service_nosql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.abernathyclinic.mediscreen.service_nosql.model.PatientNote;
import com.abernathyclinic.mediscreen.service_nosql.repository.PatientNoteRepository;

@DataMongoTest
@TestInstance(Lifecycle.PER_CLASS) // Non static BeforeAll
class PatientNoteRepositoryTest {

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	private UUID uuid = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

	@DisplayName("Store data in the embeded mongoDB ")
	@BeforeAll
	void setUpData() {
		PatientNote patientNoteEmbededData = new PatientNote(uuid, "note");
		patientNoteRepository.save(patientNoteEmbededData);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(patientNoteRepository).isNotNull();
	}

	@DisplayName("GET : /patientHistory/{uuid}")
	@Test
	void givenGettingASpecificPatientNoteUsingTheUUID_whenFindPatientNoteByUUID_thenItReturnTheRightPatientNoteFromTheDataBase() {
		assertEquals("note", patientNoteRepository.findPatientNoteByUuid(uuid).getNotes());
	}

	@DisplayName("GET : /patientHistory")
	@Test
	void givenGettingAllPatientNotes_whenGetPatientNotes_thenItReturnAllThePatientNotesFromTheDataBase() {
		assertFalse(patientNoteRepository.findAll().isEmpty());
	}

	@DisplayName("POST : /patientHistory")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() {
		UUID randomUUID = UUID.randomUUID();
		PatientNote patientNoteToSave = new PatientNote(randomUUID, "NoteSave");
		patientNoteRepository.save(patientNoteToSave);

		assertEquals("NoteSave", patientNoteRepository.findPatientNoteByUuid(randomUUID).getNotes());
	}

	@DisplayName("PUT : /patientHistory/{uuid}")
	@Test
	void givenUpdatingAPatientNote_whenUpdatePatientNote_thenItUpdateThePatientNoteInTheDataBase() {
		PatientNote patientToUpdate = patientNoteRepository.findPatientNoteByUuid(uuid);
		patientToUpdate.setNotes("NotePut");
		patientNoteRepository.save(patientToUpdate);

		assertEquals("NotePut", patientNoteRepository.findPatientNoteByUuid(uuid).getNotes());
	}

	@DisplayName("DELETE : /patientHistory/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() {
		UUID randomUUID = UUID.randomUUID();
		PatientNote patientNoteToDeleteToStoreInDB = new PatientNote(randomUUID, "DeleteNote");
		patientNoteRepository.save(patientNoteToDeleteToStoreInDB);
		patientNoteRepository.delete(patientNoteToDeleteToStoreInDB);
		assertEquals(1, patientNoteRepository.findAll().size()); // 1 = the patientNoteEmbededData
	}

}
