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

import com.google.inject.Provider;

public interface HospitalVisitProvider extends Provider<HospitalVisit> {

	@Override
	HospitalVisit get();

	void generateDisease();

	void generateWard();

	void generateAdmissionType();

	void generateAdmissionDate();

	void generateDischargeDate();

	void generateDiagnosis();

	void generateDischargeType();

	void setDisease(Disease disease);

	void setWard(Ward ward);

	void setAdmissionType(AdmissionType admissionType);

	void setPatient(Patient patient);

	void setPerson(Person person);

	void setAdmissionDate(LocalDateTime date);

	void setDiagnosis(Disease disease);

	void setDischargeType(DischargeType dischargeType);

	// extra info for generation
	void setDischargePercentage(int dischargePercentage);

	void isDischarged(boolean isDischarged);

}
