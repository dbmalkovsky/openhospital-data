/*
 * Open Hospital (www.open-hospital.org)
 * Copyright © 2021 Informatici Senza Frontiere (info@informaticisenzafrontiere.org)
 *
 * Open Hospital is a free and open source software for healthcare data management.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.isf.generator.producer.hospitalvisit;

import java.time.LocalDateTime;

import org.isf.admtype.model.AdmissionType;
import org.isf.disctype.model.DischargeType;
import org.isf.disease.model.Disease;
import org.isf.generator.producer.person.Person;
import org.isf.patient.model.Patient;
import org.isf.ward.model.Ward;

public class HospitalVisit {

	private Person person;
	private Patient patient;
	private Disease disease;
	private Ward ward;
	private AdmissionType admissionType;
	private LocalDateTime admissionDate;
	private LocalDateTime dischargeDate;
	private Disease diagnosis;
	private DischargeType dischargeType;
	private char deleted;
	private char type;

	public HospitalVisit(Person person, Patient patient, Disease disease, Ward ward, AdmissionType admissionType, LocalDateTime admissionDate,
			LocalDateTime dischargeDate, Disease diagnosis, DischargeType dischargeType, char deleted, char type) {
		this.person = person;
		this.patient = patient;
		this.disease = disease;
		this.ward = ward;
		this.admissionType = admissionType;
		this.admissionDate = admissionDate;
		this.dischargeDate = dischargeDate;
		this.diagnosis = diagnosis;
		this.dischargeType = dischargeType;
		this.deleted = deleted;
		this.type = type;
	}

	public Person getPerson() {
		return person;
	}

	public Patient getPatient() {
		return patient;
	}

	public Disease getDisease() {
		return disease;
	}

	public Ward getWard() {
		return ward;
	}

	public AdmissionType getAdmissionType() {
		return admissionType;
	}

	public LocalDateTime getAdmissionDate() {
		return admissionDate;
	}

	public LocalDateTime getDischargeDate() {
		return dischargeDate;
	}

	public Disease getDiagnosis() {
		return diagnosis;
	}

	public DischargeType getDischargeType() {
		return dischargeType;
	}

	public char getDeleted() {
		return deleted;
	}

	public char getType() {
		return type;
	}

}
