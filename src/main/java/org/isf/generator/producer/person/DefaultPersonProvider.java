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

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.TimeProvider;
import org.isf.generator.producer.company.Company;
import org.isf.generator.producer.company.CompanyFactory;

import com.google.inject.assistedinject.Assisted;

public class DefaultPersonProvider implements PersonProvider {

	protected Person.Sex sex;
	protected String telephoneNumberFormat;
	protected String mobileTelephoneNumberFormat;
	protected Integer age;
	protected LocalDate dateOfBirth;
	protected Company company;
	protected Address address;
	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected String email;
	protected String username;
	protected String telephoneNumber;
	protected String mobileTelephoneNumber;
	protected String password;
	protected String companyEmail;
	protected String nationalIdentityCardNumber;
	protected String nationalIdentificationNumber;
	protected String passportNumber;
	protected Country nationality;
	protected String profession;
	protected String martialStatus;
	protected String bloodType;
	protected String nameOfMother;
	protected char motherAlive;
	protected String nameOfFather;
	protected char fatherAlive;
	protected char parentsTogether;
	protected char hasInsurance;

	protected final DataMaster dataMaster;
	protected final DateProducer dateProducer;
	protected final BaseProducer baseProducer;
	protected final NationalIdentificationNumberFactory nationalIdentificationNumberFactory;
	protected final NationalIdentityCardNumberProvider nationalIdentityCardNumberProvider;
	protected final AddressProvider addressProvider;
	protected final CompanyFactory companyFactory;
	protected final TimeProvider timeProvider;
	protected final PassportNumberProvider passportNumberProvider;

	@Inject
	public DefaultPersonProvider(DataMaster dataMaster, DateProducer dateProducer, BaseProducer baseProducer,
			NationalIdentificationNumberFactory nationalIdentificationNumberFactory, NationalIdentityCardNumberProvider nationalIdentityCardNumberProvider,
			AddressProvider addressProvider, CompanyFactory companyFactory, PassportNumberProvider passportNumberProvider, TimeProvider timeProvider,
			@Assisted PersonProperties.PersonProperty... personProperties) {

		this.dataMaster = dataMaster;
		this.dateProducer = dateProducer;
		this.baseProducer = baseProducer;
		this.nationalIdentificationNumberFactory = nationalIdentificationNumberFactory;
		this.nationalIdentityCardNumberProvider = nationalIdentityCardNumberProvider;
		this.addressProvider = addressProvider;
		this.passportNumberProvider = passportNumberProvider;
		this.companyFactory = companyFactory;
		this.timeProvider = timeProvider;

		for (PersonProperties.PersonProperty personProperty : personProperties) {
			personProperty.apply(this, baseProducer);
		}
	}

	@Override
	public Person get() {

		generateSex();
		generateCompany();
		generateFirstName();
		generateMiddleName();
		generateLastName();
		generateEmail();
		generateUsername();
		generateTelephoneNumber();
		generateAge();
		generateDateOfBirth();
		generateCompanyEmail();
		generatePassword();
		generateNationalIdentityCardNumber();
		generateNationalIdentificationNumber();
		generatePassportNumber();
		generateAddress();
		generateNationality();
		generateMobileTelephoneNumber();
		generateProfession();
		generateMaritalStatus();
		generateBloodType();
		generateNameOfMother();
		generateNameOfFather();
		generateMotherAlive();
		generateFatherAlive();
		generateParentsTogether();
		generateHasInsurance();

		return new Person(firstName, middleName, lastName, address, email, username, password, sex, telephoneNumber, mobileTelephoneNumber, dateOfBirth, age,
				nationalIdentityCardNumber, nationalIdentificationNumber, passportNumber, company, companyEmail, nationality, profession, martialStatus,
				bloodType, nameOfMother, motherAlive, nameOfFather, fatherAlive, parentsTogether, hasInsurance);
	}

	@Override
	public void generateSex() {
		if (sex != null) {
			return;
		}
		sex = baseProducer.trueOrFalse() ? Person.Sex.MALE : Person.Sex.FEMALE;
	}

	@Override
	public void generateCompany() {
		if (company != null) {
			return;
		}
		company = companyFactory.produceCompany().get();
	}

	@Override
	public void generateFirstName() {
		if (firstName != null) {
			return;
		}
		firstName = dataMaster.getValuesOfType(FIRST_NAME, sex.name(), String.class);
	}

	@Override
	public void generateMiddleName() {
		if (middleName != null) {
			return;
		}
		middleName = baseProducer.trueOrFalse() ? dataMaster.getValuesOfType(FIRST_NAME, sex.name(), String.class) : "";
	}

	@Override
	public void generateLastName() {
		if (lastName != null) {
			return;
		}
		lastName = dataMaster.getValuesOfType(LAST_NAME, sex.name(), String.class);
	}

	@Override
	public void generateEmail() {
		if (email != null) {
			return;
		}
		EmailProvider emailProvider = new EmailProvider(dataMaster, baseProducer, firstName, lastName);
		email = emailProvider.get();
	}

	@Override
	public void generateUsername() {
		if (username != null) {
			return;
		}
		if (baseProducer.trueOrFalse()) {
			username = lowerCase(stripAccents(firstName.charAt(0) + lastName));
		} else {
			username = lowerCase(stripAccents(firstName + lastName.charAt(0)));
		}
	}

	@Override
	public void generateTelephoneNumber() {
		if (telephoneNumber != null) {
			return;
		}
		if (telephoneNumberFormat == null) {
			telephoneNumberFormat = dataMaster.getRandomValue(TELEPHONE_NUMBER_FORMATS);
		}
		telephoneNumber = baseProducer.numerify(telephoneNumberFormat);
	}

	@Override
	public void generateMobileTelephoneNumber() {
		if (mobileTelephoneNumber != null) {
			return;
		}
		if (mobileTelephoneNumberFormat == null) {
			mobileTelephoneNumberFormat = dataMaster.getRandomValue(TELEPHONE_NUMBER_FORMATS);
		}
		mobileTelephoneNumber = baseProducer.numerify(mobileTelephoneNumberFormat);
	}

	@Override
	public void generateAge() {
		if (dateOfBirth != null) {
			age = (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDateTime.now());
		} else {
			if (age != null) {
				return;
			}
			age = baseProducer.randomBetween(MIN_AGE, MAX_AGE);
		}
	}

	@Override
	public void generateDateOfBirth() {
		if (dateOfBirth != null) {
			return;
		}
		LocalDate maxDate = timeProvider.getCurrentDate().minusYears(age);
		LocalDate minDate = maxDate.minusYears(1).plusDays(1);
		dateOfBirth = dateProducer.randomDateBetweenTwoDates(minDate, maxDate);
	}

	@Override
	public void generateCompanyEmail() {
		if (companyEmail != null) {
			return;
		}
		CompanyEmailProvider companyEmailProvider = new CompanyEmailProvider(firstName, lastName, company);
		companyEmail = companyEmailProvider.get();
	}

	@Override
	public void generatePassword() {
		if (password != null) {
			return;
		}
		// FIXME: Replace this with baseProducer
		password = RandomStringUtils.randomAlphanumeric(8);
	}

	@Override
	public void generateNationalIdentityCardNumber() {
		if (nationalIdentityCardNumber != null) {
			return;
		}
		nationalIdentityCardNumber = nationalIdentityCardNumberProvider.get();
	}

	@Override
	public void generateNationalIdentificationNumber() {
		if (nationalIdentificationNumber != null) {
			return;
		}
		nationalIdentificationNumber = nationalIdentificationNumberFactory.produceNationalIdentificationNumberProvider(
				NationalIdentificationNumberProperties.dateOfBirth(dateOfBirth), NationalIdentificationNumberProperties.sex(sex)).get().getValue();
	}

	@Override
	public void generateAddress() {
		if (address != null) {
			return;
		}
		address = addressProvider.get();
	}

	@Override
	public void generatePassportNumber() {
		if (passportNumber != null) {
			return;
		}
		passportNumber = passportNumberProvider.get();
	}

	@Override
	public void generateProfession() {
		if (profession != null) {
			return;
		}
		if (age <= 19) {
			profession = "unknown";
			return;
		}
		profession = dataMaster.getValuesOfType(PROFESSION, sex.name(), String.class);
	}

	@Override
	public void generateMaritalStatus() {
		if (martialStatus != null) {
			return;
		}
		if (age <= 19) {
			martialStatus = "unknown";
			return;
		}
		martialStatus = dataMaster.getRandomValue(MARITAL_STATUS);
	}

	@Override
	public void generateBloodType() {
		bloodType = dataMaster.getRandomValue(BLOOD_TYPE);
	}

	@Override
	public void generateNameOfMother() {
		if (nameOfMother != null) {
			return;
		}
		nameOfMother = dataMaster.getValuesOfType(FIRST_NAME, Person.Sex.FEMALE.toString(), String.class);
	}

	@Override
	public void generateMotherAlive() {
		motherAlive = dataMaster.getRandomValue(PARENTS_ALIVE).charAt(0);
	}

	@Override
	public void generateNameOfFather() {
		if (nameOfFather != null) {
			return;
		}
		nameOfFather = dataMaster.getValuesOfType(FIRST_NAME, Person.Sex.MALE.toString(), String.class);
	}

	@Override
	public void generateFatherAlive() {
		fatherAlive = (char) dataMaster.getRandomValue(PARENTS_ALIVE).charAt(0);
	}

	@Override
	public void generateParentsTogether() {
		if (parentsTogether != 0) {
			return;
		}
		if (motherAlive == 'U' || fatherAlive == 'U') {
			parentsTogether = 'U';
		} else if (motherAlive == 'D' || fatherAlive == 'D') {
			parentsTogether = 'N';
		} else {
			parentsTogether = baseProducer.trueOrFalse() ? 'Y' : 'N';
		}
	}

	@Override
	public void generateHasInsurance() {
		if (hasInsurance != 0) {
			return;
		}
		hasInsurance = baseProducer.trueOrFalse() ? 'Y' : 'N';
	}

	private void generateNationality() {
		List<Country> countries = Country.findCountryForLanguage(dataMaster.getLanguage());
		nationality = !countries.isEmpty() ? baseProducer.randomElement(countries) : Country.Uganda;
	}

	@Override
	public void setTelephoneNumberFormat(String telephoneFormat) {
		telephoneNumberFormat = telephoneFormat;
	}

	@Override
	public void setMobileTelephoneNumberFormat(String mobileTelephoneNumberFormat) {
		this.mobileTelephoneNumberFormat = mobileTelephoneNumberFormat;
	}

	@Override
	public void setSex(Person.Sex sex) {
		this.sex = sex;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	@Override
	public void setMobileTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	@Override
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	@Override
	public void setNationalIdentityCardNumber(String nationalIdentityCardNumber) {
		this.nationalIdentityCardNumber = nationalIdentityCardNumber;
	}

	@Override
	public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
		this.nationalIdentificationNumber = nationalIdentificationNumber;
	}

	@Override
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
	public void setMaritalStatus(String maritalStatus) {
		this.martialStatus = maritalStatus;
	}

	@Override
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Override
	public void setNameOfMother(String nameOfMother) {
		this.nameOfMother = nameOfMother;
	}

	@Override
	public void setNameOfFather(String nameOfFather) {
		this.nameOfFather = nameOfFather;
	}

	@Override
	public void setParentsTogether(char parentsTogether) {
		this.parentsTogether = parentsTogether;
	}

}
