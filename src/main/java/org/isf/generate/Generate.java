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

import static org.isf.generator.producer.person.PersonProperties.ageBetween;
import static org.isf.generator.producer.person.PersonProperties.female;
import static org.isf.generator.producer.person.PersonProperties.male;

import java.util.Locale;

import org.isf.generator.Generator;
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

	private PatientBrowserManager patientManager;
	private Generator generator;

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
		User myUser = new User(ADMIN_STR, new UserGroup(ADMIN_STR, ""), ADMIN_STR, "");
		MDC.put("OHUser", myUser.getUserName());
		MDC.put("OHUserGroup", myUser.getUserGroupName().getCode());

		try {
			new Generate().doGenerator();
		} catch (Exception e) {
			LOGGER.error("caught exception: ", e);
		}
	}

	private void doGenerator() throws OHServiceException {
		patientManager = Context.getApplicationContext().getBean(PatientBrowserManager.class);
		generator = Generator.create(Locale.forLanguageTag("SW"));
		generatePatient(1, male(), 0, 0);
		generatePatient(1, female(), 0, 0);

		generatePatient(1, male(), 1, 5);
		generatePatient(1, female(), 1, 5);

		generatePatient(1, male(), 6, 12);
		generatePatient(1, female(), 6, 12);

		generatePatient(1, male(), 13, 24);
		generatePatient(1, female(), 13, 24);

		generatePatient(1, male(), 25, 59);
		generatePatient(1, female(), 25, 59);

		generatePatient(1, male(), 60, 99);
		generatePatient(1, female(), 60, 99);
	}

	private void generatePatient(int numberOfPatients, PersonProperties.PersonProperty sex, int minAge, int maxAge) throws OHServiceException {

		Person person;
		Patient patient;

		for (int counter = 0; counter < numberOfPatients; counter++) {
			person = generator.person(sex, ageBetween(minAge, maxAge));
			patient = new Patient();
			patient.setFirstName(person.getFirstName());
			patient.setSecondName(person.getLastName());
			patient.setAge(person.getAge());
			patient.setSex(person.getSex() == Person.Sex.MALE ? 'M' : 'F');
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

			patientManager.savePatient(patient);
		}
	}

}
