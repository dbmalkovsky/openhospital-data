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
package org.isf.generator.producer.person;

import java.time.LocalDate;

import org.isf.generator.producer.company.Company;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provider;

public interface PersonProvider extends Provider<Person> {

	int MIN_AGE = 1;
	int MAX_AGE = 100;
	@VisibleForTesting
	String FIRST_NAME = "firstNames";
	@VisibleForTesting
	String LAST_NAME = "lastNames";
	@VisibleForTesting
	String PERSONAL_EMAIL = "personalEmails";
	@VisibleForTesting
	String TELEPHONE_NUMBER_FORMATS = "telephone_number_formats";
	String PROFESSION = "profession";
	String MARITAL_STATUS = "maritalStatus";
	String BLOOD_TYPE = "bloodType";

	@Override
	Person get();

	void generateSex();

	void generateCompany();

	void generateFirstName();

	void generateMiddleName();

	void generateLastName();

	void generateEmail();

	void generateUsername();

	void generateTelephoneNumber();

	void generateMobileTelephoneNumber();

	void generateAge();

	void generateDateOfBirth();

	void generateCompanyEmail();

	void generatePassword();

	void generateNationalIdentityCardNumber();

	void generateNationalIdentificationNumber();

	void generateAddress();

	void generatePassportNumber();

	void generateProfession();

	void generateMaritalStatus();

	void generateBloodType();

	void generateNameOfMother();

	void generateNameOfFather();

	void generateParentsTogether();

	void generateHasInsurance();

	void setTelephoneNumberFormat(String telephoneFormat);

	void setMobileTelephoneNumberFormat(String telephoneFormat);

	void setSex(Person.Sex sex);

	void setAge(int age);

	void setCompany(Company company);

	void setFirstName(String firstName);

	void setMiddleName(String middleName);

	void setLastName(String lastName);

	void setEmail(String email);

	void setUsername(String username);

	void setTelephoneNumber(String telephoneNumber);

	void setMobileTelephoneNumber(String telephoneNumber);

	void setDateOfBirth(LocalDate dateOfBirth);

	void setPassword(String password);

	void setAddress(Address address);

	void setCompanyEmail(String companyEmail);

	void setNationalIdentityCardNumber(String nationalIdentityCardNumber);

	void setNationalIdentificationNumber(String nationalIdentificationNumber);

	void setPassportNumber(String passportNumber);

	void setProfession(String profession);

	void setMaritalStatus(String maritalStatus);

	void setBloodType(String bloodType);

	void setNameOfMother(String nameOfMother);

	void setNameOfFather(String nameOfFather);

	void setParentsTogether(char parentsTogether);

}


