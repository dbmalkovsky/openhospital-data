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
package org.isf.generate;

import static org.isf.generator.producer.hospitalvisit.HospitalVisitProperties.HospitalVisitProperty.withDischargePercentage;
import static org.isf.generator.producer.hospitalvisit.HospitalVisitProperties.HospitalVisitProperty.withPatient;
import static org.isf.generator.producer.hospitalvisit.HospitalVisitProperties.HospitalVisitProperty.withPerson;
import static org.isf.generator.producer.person.PersonProperties.ageBetween;
import static org.isf.generator.producer.person.PersonProperties.female;
import static org.isf.generator.producer.person.PersonProperties.male;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.isf.admission.manager.AdmissionBrowserManager;
import org.isf.admission.model.Admission;
import org.isf.generator.Generator;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.hospitalvisit.HospitalVisit;
import org.isf.generator.producer.person.Person;
import org.isf.generator.producer.person.PersonProperties;
import org.isf.menu.manager.Context;
import org.isf.menu.model.User;
import org.isf.menu.model.UserGroup;
import org.isf.patient.manager.PatientBrowserManager;
import org.isf.patient.model.Patient;
import org.isf.utils.exception.OHServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Generate {

	private static final Logger LOGGER = LoggerFactory.getLogger(Generate.class);
	public static final String ADMIN_STR = "admin";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private PatientBrowserManager patientManager;
	private AdmissionBrowserManager admissionManager;

	private Generator generator;
	private BaseProducer baseProducer;

	private static int yProg;

	private static User theUser;

	public static void main(String[] args) {
		ApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			LOGGER.error("Fatal: fail to load application context. {}", e.getMessage(), e);
			System.exit(1);
		}
		Context.setApplicationContext(context);

		LOGGER.info("Logging: Single User mode.");
		theUser = new User(ADMIN_STR, new UserGroup(ADMIN_STR, ""), ADMIN_STR, "");
		MDC.put("OHUser", theUser.getUserName());
		MDC.put("OHUserGroup", theUser.getUserGroupName().getCode());

		try {
			new Generate().doGenerator();
		} catch (Exception e) {
			LOGGER.error("caught exception: ", e);
		}
	}

	private void doGenerator() throws OHServiceException {
		patientManager = Context.getApplicationContext().getBean(PatientBrowserManager.class);
		admissionManager = Context.getApplicationContext().getBean(AdmissionBrowserManager.class);

		generator = Generator.create(Locale.forLanguageTag("SW"));

		baseProducer = generator.baseProducer();

		generatePatient(1, male(), 0, 0, 50, 25);
		generatePatient(1, female(), 0, 0, 50, 25);

		generatePatient(1, male(), 1, 5, 50, 25);
		generatePatient(1, female(), 1, 5, 50, 25);

		generatePatient(1, male(), 6, 12, 50, 25);
		generatePatient(1, female(), 6, 12, 50, 25);

		generatePatient(1, male(), 13, 24, 50, 25);
		generatePatient(1, female(), 13, 24, 50, 25);

		generatePatient(3, male(), 25, 59, 50, 25);
		generatePatient(4, female(), 25, 59, 50, 25);

		generatePatient(2, male(), 60, 99, 50, 25);
		generatePatient(3, female(), 60, 99, 50, 25);
	}

	private void generatePatient(int numberOfPatients, PersonProperties.PersonProperty sex, int minAge, int maxAge, int percentAdmissions, int percentDischarge)
			throws OHServiceException {

		Person person = null;
		Patient patient;
		Admission admission;
		int admittedCount = 0;
		int dischargedCount = 0;

		LOGGER.error("");
		LOGGER.error(">>>>> Number of Patients={}, minAge={}, maxAge={}, admission={}%, discharge={}%", numberOfPatients, minAge, maxAge, percentAdmissions,
				percentDischarge);
		for (int counter = 0; counter < numberOfPatients; counter++) {
			person = generator.person(sex, ageBetween(minAge, maxAge));
			patient = new Patient();
			patient.setFirstName(person.getFirstName());
			patient.setSecondName(person.getLastName());
			patient.setAge(person.getAge());
			patient.setSex(person.isMale() ? 'M' : 'F');
			patient.setAddress(person.getAddress().getStreetNumber() + ' ' + person.getAddress().getStreet());
			patient.setCity(person.getAddress().getCity());
			patient.setNextKin("");
			patient.setTelephone(person.getTelephoneNumber());
			patient.setNote("");
			patient.setBirthDate(person.getDateOfBirth());
			patient.setAgetype("");
			patient.setMotherName(person.getNameOfMother());
			patient.setMother(person.getParentsTogether() == 'Y' ? 'A' : 'U');
			patient.setFatherName(person.getNameOfFather());
			patient.setFather(person.getParentsTogether() == 'Y' ? 'A' : 'U');
			patient.setBloodType(person.getBloodType());
			patient.setHasInsurance(person.getHasInsurance());
			patient.setParentTogether(person.getParentsTogether());
			patient.setMaritalStatus(person.getMartialStatus());
			patient.setProfession(person.getProfession());

			Patient savedPatient = patientManager.savePatient(patient);
			LOGGER.error("Patient saved: {} {}", savedPatient.getFirstName(), savedPatient.getSecondName());

			// only admit a percentage of patients
			if (baseProducer.randomBetween(1, 100) > percentAdmissions) {
				continue;
			}

			admittedCount++;
			HospitalVisit hospitalVisit = generator.hospitalVisit(withPatient(patient), withPerson(person), withDischargePercentage(percentDischarge));

			admission = new Admission();
			admission.setPatient(patient);
			admission.setAdmDate(hospitalVisit.getAdmissionDate());
			admission.setAdmType(hospitalVisit.getAdmissionType());
			admission.setWard(hospitalVisit.getWard());
			admission.setDeleted(String.valueOf(hospitalVisit.getDeleted()));    // flag record deleted ; values are 'Y' OR 'N' default is 'N'
			admission.setType(String.valueOf(hospitalVisit.getType()));          // values are 'N'(normal)  or 'M' (malnutrition)  default 'N'
			admission.setDiseaseIn(hospitalVisit.getDisease());
			admission.setAdmitted(1);         // values are 0 or 1, default 0 (not admitted)
			admission.setYProg(yProg++);

			if (hospitalVisit.getDischargeDate() != null) {
				admission.setDisDate(hospitalVisit.getDischargeDate());
				admission.setDiseaseOut1(hospitalVisit.getDiagnosis());
				admission.setDisType(hospitalVisit.getDischargeType());
				dischargedCount++;
			}

			admission.setUserID(theUser.getUserName());

			int code = admissionManager.newAdmissionReturnKey(admission);
			Admission retrievedAdmission = admissionManager.getAdmission(code);
			LOGGER.error("Admission saved: {} {}", retrievedAdmission.getPatient().getFirstName(), retrievedAdmission.getPatient().getSecondName());
			if (retrievedAdmission.getDisDate() != null) {
				LOGGER.error("                 admitted={} and discharged={}", retrievedAdmission.getAdmDate().format(formatter),
						retrievedAdmission.getDisDate().format(formatter));
			}
		}
		LOGGER.error("Summary: {} {} patients created; admitted={}, discharged={}\n", numberOfPatients, person.isMale() ? "male" : "female", admittedCount,
				dischargedCount);
	}

}
