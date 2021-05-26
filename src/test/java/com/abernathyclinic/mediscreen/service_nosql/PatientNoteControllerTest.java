package com.abernathyclinic.mediscreen.service_nosql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service_nosql.controller.PatientNoteController;
import com.abernathyclinic.mediscreen.service_nosql.model.PatientNote;
import com.abernathyclinic.mediscreen.service_nosql.repository.PatientNoteRepository;

@WebMvcTest(PatientNoteController.class)
class PatientNoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientNoteRepository patientNoteRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(patientNoteRepository).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void givenGettingTheIndex_whenIndex_thenItReturnTheIndex() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);
	}

	@DisplayName("GET : /patientHistory/{uuid}")
	@Test
	void givenGettingASpecificPatientNoteUsingTheUUID_whenFindPatientNoteByUUID_thenItReturnTheRightPatientNoteFromTheDataBase()
			throws Exception {
		// ARRANGE
		when(patientNoteRepository.findPatientNoteByUuid(any(UUID.class))).thenReturn(new PatientNote());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory/b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
		verify(patientNoteRepository, times(1)).findPatientNoteByUuid(any(UUID.class));
	}

	@DisplayName("GET : /patientHistory")
	@Test
	void givenGettingAllPatientsNotes_whenFindAllPatientsNotes_thenItReturnAllThePatientsNotesFromTheDatabase()
			throws Exception {
		// ARRANGE
		when(patientNoteRepository.findAll()).thenReturn(new ArrayList<PatientNote>());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /patientHistory")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		PatientNote patientNoteToSave = new PatientNote(UUID.randomUUID(), "Note");
		when(patientNoteRepository.save(patientNoteToSave)).thenReturn(patientNoteToSave);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(
						post("/patientHistory").contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"note\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
		assertEquals("Patient's notes sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /patientHistory/{uuid}")
	@Test
	void givenUpdatingAPatientNote_whenUpdatePatientNote_thenItUpdateThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		PatientNote patientNoteToUpdate = new PatientNote(UUID.randomUUID(), "Note");
		UUID randomUUID = UUID.randomUUID();
		when(patientNoteRepository.findPatientNoteByUuid(randomUUID)).thenReturn(patientNoteToUpdate);
		when(patientNoteRepository.save(patientNoteToUpdate)).thenReturn(patientNoteToUpdate);

		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patientHistory/" + randomUUID)
				.contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"note\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("Patient's notes successfully updated", response.getContentAsString());
		verify(patientNoteRepository, times(1)).findPatientNoteByUuid(randomUUID);
		verify(patientNoteRepository, times(1)).save(patientNoteToUpdate);
	}

	@DisplayName("DELETE : /patientHistory/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		PatientNote patientNoteToDelete = new PatientNote(UUID.randomUUID(), "Note");
		UUID randomUUID = UUID.randomUUID();
		when(patientNoteRepository.findPatientNoteByUuid(randomUUID)).thenReturn(patientNoteToDelete);
		doNothing().when(patientNoteRepository).delete(patientNoteToDelete);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patientHistory/" + randomUUID).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient's notes has been successfully deleted in the database.",
				response.getContentAsString());
		verify(patientNoteRepository, times(1)).findPatientNoteByUuid(randomUUID);
		verify(patientNoteRepository, times(1)).delete(patientNoteToDelete);
	}

}
