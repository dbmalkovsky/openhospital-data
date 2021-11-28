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
import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;

import org.isf.admtype.manager.AdmissionTypeBrowserManager;
import org.isf.admtype.model.AdmissionType;
import org.isf.disctype.manager.DischargeTypeBrowserManager;
import org.isf.disctype.model.DischargeType;
import org.isf.disease.manager.DiseaseBrowserManager;
import org.isf.disease.model.Disease;
import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.TimeProvider;
import org.isf.generator.producer.person.Person;
import org.isf.menu.manager.Context;
import org.isf.patient.model.Patient;
import org.isf.utils.exception.OHServiceException;
import org.isf.ward.manager.WardBrowserManager;
import org.isf.ward.model.Ward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;

public class DefaultHospitalVisitProvider implements HospitalVisitProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHospitalVisitProvider.class);

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

	private AdmissionTypeBrowserManager admissionTypeBrowserManager;
	private DiseaseBrowserManager diseaseBrowserManager;
	private WardBrowserManager wardBrowserManager;
	private DischargeTypeBrowserManager dischargeTypeBrowserManager;

	private Ward childrenWard;
	private Ward femaleWard;
	private Ward maleWard;
	private Ward maternityWard;

	private List<Disease> allOtherDiseases;
	private List<Disease> maternalDiseases;
	private List<Disease> nonCommunicableDiseases;
	private List<Disease> notifiableDiseases;
	private List<Disease> infectiousCommunicableDiseases;

	private List<AdmissionType> admissionTypes;

	private List<DischargeType> dischargeTypes;

	protected BaseProducer baseProducer;
	protected DataMaster dataMaster;
	protected TimeProvider timeProvider;
	protected DateProducer dateProducer;

	protected int dischargePercentage;
	protected boolean isDischarged;

	@Inject
	public DefaultHospitalVisitProvider(BaseProducer baseProducer, DataMaster dataMaster, TimeProvider timeProvider, DateProducer dateProducer,
			@Assisted HospitalVisitProperties.HospitalVisitProperty... hospitalVisitProperties) {
		this.baseProducer = baseProducer;
		this.dataMaster = dataMaster;
		this.timeProvider = timeProvider;
		this.dateProducer = dateProducer;

		for (HospitalVisitProperties.HospitalVisitProperty hospitalVisitProperty : hospitalVisitProperties) {
			hospitalVisitProperty.apply(this);
		}

		diseaseBrowserManager = Context.getApplicationContext().getBean(DiseaseBrowserManager.class);
		try {
			allOtherDiseases = diseaseBrowserManager.getDisease("AO");
			maternalDiseases = diseaseBrowserManager.getDisease("MP");

			nonCommunicableDiseases = diseaseBrowserManager.getDisease("NC");
			notifiableDiseases = diseaseBrowserManager.getDisease("ND");
			infectiousCommunicableDiseases = diseaseBrowserManager.getDisease("OC");
		} catch (OHServiceException ohServiceException) {
			LOGGER.error("Issues with getting diseases.", ohServiceException);
		}

		wardBrowserManager = Context.getApplicationContext().getBean(WardBrowserManager.class);
		try {
			childrenWard = wardBrowserManager.findWard("C");
			femaleWard = wardBrowserManager.findWard("F");
			maleWard = wardBrowserManager.findWard("I");
			maternityWard = wardBrowserManager.findWard("M");
		} catch (OHServiceException ohServiceException) {
			LOGGER.error("Issues with getting wards.", ohServiceException);
		}

		admissionTypeBrowserManager = Context.getApplicationContext().getBean(AdmissionTypeBrowserManager.class);
		try {
			admissionTypes = admissionTypeBrowserManager.getAdmissionType();
		} catch (OHServiceException ohServiceException) {
			LOGGER.error("Issues with getting admission types.", ohServiceException);
		}

		dischargeTypeBrowserManager = Context.getApplicationContext().getBean(DischargeTypeBrowserManager.class);
		try {
			dischargeTypes = dischargeTypeBrowserManager.getDischargeType();
		} catch (OHServiceException ohServiceException) {
			LOGGER.error("Issues with getting discharge types.", ohServiceException);
		}
	}

	@Override
	public HospitalVisit get() {
		generateDisease();
		generateWard();
		generateAdmissionType();
		generateAdmissionDate();
		generateDischargeDate();
		if (isDischarged) {
			generateDiagnosis();
			generateDischargeType();
		}
		deleted = 'N';    // flag record deleted ; values are 'Y' OR 'N' default is 'N'
		type = 'N';         // values are 'N'(normal)  or 'M' (malnutrition)  default 'N'
		return new HospitalVisit(person, patient, disease, ward, admissionType, admissionDate, dischargeDate, diagnosis, dischargeType, deleted, type);
	}

	@Override
	public void generateDisease() {
		if (disease != null) {
			return;
		}
		if (person.isFemale() && baseProducer.trueOrFalse()) {
			disease = baseProducer.randomElement(maternalDiseases);
		}
		switch (baseProducer.randomBetween(0, 2)) {
			case 0:
				disease = baseProducer.randomElement(nonCommunicableDiseases);
				break;
			case 1:
				disease = baseProducer.randomElement(notifiableDiseases);
				break;
			default:
				disease = baseProducer.randomElement(infectiousCommunicableDiseases);
		}
	}

	@Override
	public void generateWard() {
		if (ward != null) {
			return;
		}
		if (person.getAge() <= 12) {
			ward = childrenWard;
		} else {
			if (person.isMale()) {
				ward = maleWard;
			} else {
				if (person.getAge() >= 18 && person.getAge() <= 39) {
					ward = maternityWard;
				} else {
					ward = femaleWard;
				}
			}
		}
	}

	@Override
	public void generateAdmissionType() {
		if (admissionType != null) {
			return;
		}
		admissionType = baseProducer.randomElement(admissionTypes);
	}

	@Override
	public void generateAdmissionDate() {
		if (admissionDate != null) {
			return;
		}
		int days = baseProducer.randomBetween(4, 20);
		LocalDateTime maxDate = timeProvider.getCurrentTime().minusDays(days);
		LocalDateTime minDate = maxDate.minusDays(days - 1);
		admissionDate = dateProducer.randomDateBetweenTwoDates(minDate, maxDate).with(LocalTime.MIN);
	}

	@Override
	public void generateDischargeDate() {
		if (admissionDate == null) {
			isDischarged = false;
			return; // not admitted so no discharge
		}
		if (baseProducer.randomBetween(1, 100) > dischargePercentage) {
			isDischarged = false;
			return;
		}
		isDischarged = true;
		dischargeDate = dateProducer.randomDateBetweenTwoDates(admissionDate.plusDays(1), timeProvider.getCurrentTime()).with(LocalTime.MIN);
	}

	@Override
	public void generateDiagnosis() {
		if (!isDischarged || diagnosis != null) {
			return;
		}
		if (person.isFemale() && baseProducer.trueOrFalse()) {
			diagnosis = baseProducer.randomElement(maternalDiseases);
		}
		switch (baseProducer.randomBetween(0, 2)) {
			case 0:
				diagnosis = baseProducer.randomElement(nonCommunicableDiseases);
				break;
			case 1:
				diagnosis = baseProducer.randomElement(notifiableDiseases);
				break;
			default:
				diagnosis = baseProducer.randomElement(infectiousCommunicableDiseases);
		}
	}

	@Override
	public void generateDischargeType() {
		if (!isDischarged || dischargeType != null) {
			return;
		}
		dischargeType = baseProducer.randomElement(dischargeTypes);
	}

	@Override
	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	@Override
	public void setWard(Ward ward) {
		this.ward = ward;
	}

	@Override
	public void setAdmissionType(AdmissionType admissionType) {
		this.admissionType = admissionType;
	}

	@Override
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public void setAdmissionDate(LocalDateTime date) {
		this.admissionDate = date;
	}

	@Override
	public void setDischargePercentage(int dischargePercentage) {
		this.dischargePercentage = dischargePercentage;
	}

	@Override
	public void isDischarged(boolean isDischarged) {
		this.isDischarged = isDischarged;
	}

	@Override
	public void setDiagnosis(Disease diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public void setDischargeType(DischargeType dischargeType) {
		this.dischargeType = dischargeType;
	}

}
