package com.abernathyclinic.mediscreen.service_nosql.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service_nosql.repository.PatientNoteRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class PatientNoteControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	private UUID uuidOfThePatientNoteInDB = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

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
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory/b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("GET : /patientHistory")
	@Test
	void givenGettingAllPatientsNotes_whenFindAllPatientsNotes_thenItReturnAllThePatientsNotesFromTheDataBase()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /patientHistory")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patientHistory").contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"noteSave\"}"))
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
		mockMvc.perform(post("/patientHistory").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uuid\":\"b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9\",\"note\": \"noteUpdate\"}"))
				.andDo(print());
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patientHistory/" + uuidOfThePatientNoteInDB)
				.contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"notePut\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("Patient's notes successfully updated", response.getContentAsString());
	}

	@DisplayName("DELETE : /patientHistory/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		mockMvc.perform(post("/patientHistory").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uuid\":\"b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9\",\"note\": \"noteDelete\"}"))
				.andDo(print());
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patientHistory/" + uuidOfThePatientNoteInDB).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient's notes has been successfully deleted in the database.",
				response.getContentAsString());
	}

}
