package com.abernathyclinic.mediscreen.service_nosql.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abernathyclinic.mediscreen.service_nosql.model.Patient;

/**
 * Interface used to define <b>CRUD</b> operations with the patient document.
 * <br>
 * It extends the {@link MongoRepository} interface delivered by Spring Data
 * MongoDB.
 */
public interface PatientRepository extends MongoRepository<Patient, String> {

	public Patient findPatientByUuid(UUID uuid); // ByUUID throw a PropertyReferenceException !

}
