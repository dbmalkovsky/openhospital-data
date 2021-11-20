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

import static org.isf.generator.producer.person.Person.Sex.FEMALE;
import static org.isf.generator.producer.person.Person.Sex.MALE;

import java.time.LocalDate;

import org.isf.generator.producer.company.Company;

public class Person {

	public enum Sex {
		MALE, FEMALE
	}

	private final Address address;
	private final String firstName;
	private final String middleName;
	private final String lastName;
	private final String email;
	private final String username;
	private final String password;
	private final Sex sex;
	private final String telephoneNumber;
	private final String mobileTelephoneNumber;
	private final LocalDate dateOfBirth;
	private final Integer age;
	private final Company company;
	private final String companyEmail;
	private final String nationalIdentityCardNumber;
	private final String nationalIdentificationNumber;
	private final String passportNumber;
	private final Country nationality;
	private final String profession;
	private final String martialStatus;
	private final String bloodType;
	private final String nameOfMother;
	private final String nameOfFather;
	private final char parentsTogether;
	private final char hasInsurance;

	public Person(String firstName, String middleName, String lastName, Address address, String email, String username, String password, Sex sex,
			String telephoneNumber, String mobileTelephoneNumber, LocalDate dateOfBirth, Integer age, String nationalIdentityCardNumber,
			String nationalIdentificationNumber, String passportNumber, Company company, String companyEmail, Country nationality, String profession,
			String martialStatus, String bloodType, String nameOfMother, String nameOfFather, char parentsTogether, char hasInsurance) {
		this.nationalIdentityCardNumber = nationalIdentityCardNumber;
		this.address = address;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.telephoneNumber = telephoneNumber;
		this.mobileTelephoneNumber = mobileTelephoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.nationalIdentificationNumber = nationalIdentificationNumber;
		this.company = company;
		this.companyEmail = companyEmail;
		this.passportNumber = passportNumber;
		this.nationality = nationality;
		this.profession = profession;
		this.martialStatus = martialStatus;
		this.bloodType = bloodType;
		this.nameOfMother = nameOfMother;
		this.nameOfFather = nameOfFather;
		this.parentsTogether = parentsTogether;
		this.hasInsurance = hasInsurance;
	}

	public String getNationalIdentificationNumber() {
		return nationalIdentificationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public boolean isMale() {
		return sex == MALE;
	}

	public boolean isFemale() {
		return sex == FEMALE;
	}

	public Sex getSex() {
		return sex;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public String getMobileTelephoneNumber() {
		return mobileTelephoneNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	public String getNationalIdentityCardNumber() {
		return nationalIdentityCardNumber;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public Address getAddress() {
		return address;
	}

	public Company getCompany() {
		return company;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public Country getNationality() {
		return nationality;
	}

	public String getProfession() {
		return profession;
	}

	public String getMartialStatus() {
		return martialStatus;
	}

	public String getBloodType() {
		return bloodType;
	}

	public String getNameOfMother() {
		return nameOfMother;
	}

	public String getNameOfFather() {
		return nameOfFather;
	}

	public char getParentsTogether() {
		return parentsTogether;
	}

	public char getHasInsurance() {
		return hasInsurance;
	}

}
