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
import java.util.Optional;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.company.Company;

public final class PersonProperties {

	private static Optional<Integer> minimumAge = Optional.empty();
	private static Optional<Integer> maximumAge = Optional.empty();

	private PersonProperties() {
	}

	public abstract static class PersonProperty {

		public abstract void apply(PersonProvider person, BaseProducer baseProducer);

	}

	public static PersonProperty male() {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider person, BaseProducer baseProducer) {
				person.setSex(Person.Sex.MALE);
			}
		};
	}

	public static PersonProperty female() {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider person, BaseProducer baseProducer) {
				person.setSex(Person.Sex.FEMALE);
			}
		};
	}

	public static PersonProperty ageBetween(final int minAge, final int maxAge) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider person, BaseProducer baseProducer) {
				person.setAge(baseProducer.randomBetween(minAge, maxAge));
				minimumAge = Optional.of(minAge);
				maximumAge = Optional.of(maxAge);
			}
		};
	}

	public static PersonProperty minAge(final int minAge) {
		minimumAge = Optional.of(minAge);
		return ageBetween(minAge, maximumAge.orElse(PersonProvider.MAX_AGE));
	}

	public static PersonProperty maxAge(final int maxAge) {
		maximumAge = Optional.of(maxAge);
		return ageBetween(minimumAge.orElse(PersonProvider.MIN_AGE), maxAge);
	}

	public static PersonProperty telephoneFormat(final String telephoneFormat) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider person, BaseProducer baseProducer) {
				person.setTelephoneNumberFormat(telephoneFormat);
			}
		};
	}

	public static PersonProperty mobileTelephoneFormat(final String mobileTelephoneNumberFormat) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider person, BaseProducer baseProducer) {
				person.setMobileTelephoneNumberFormat(mobileTelephoneNumberFormat);
			}
		};
	}

	public static PersonProperty withCompany(final Company company) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setCompany(company);
			}
		};
	}

	public static PersonProperty withAddress(final Address address) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setAddress(address);
			}
		};
	}

	public static PersonProperty withFirstName(final String firstName) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setFirstName(firstName);
			}
		};
	}

	public static PersonProperty withMiddleName(final String middleName) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setMiddleName(middleName);
			}
		};
	}

	public static PersonProperty withLastName(final String lastName) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setLastName(lastName);
			}
		};
	}

	public static PersonProperty withEmail(final String email) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setEmail(email);
			}
		};
	}

	public static PersonProperty withUsername(final String username) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setUsername(username);
			}
		};
	}

	public static PersonProperty withTelephoneNumber(final String telephoneNumber) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setTelephoneNumber(telephoneNumber);
			}
		};
	}

	public static PersonProperty withMobileTelephoneNumber(final String mobileTelephoneNumber) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setMobileTelephoneNumber(mobileTelephoneNumber);
			}
		};
	}

	public static PersonProperty withDateOfBirth(final LocalDate dateOfBirth) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setDateOfBirth(dateOfBirth);
			}
		};
	}

	public static PersonProperty withAge(final Integer age) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setAge(age);
			}
		};
	}

	public static PersonProperty withPassword(final String password) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setPassword(password);
			}
		};
	}

	public static PersonProperty withCompanyEmail(final String companyEmail) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setCompanyEmail(companyEmail);
			}
		};
	}

	public static PersonProperty withNationalIdentityCardNumber(final String nationalIdentityCardNumber) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setNationalIdentityCardNumber(nationalIdentityCardNumber);
			}
		};
	}

	public static PersonProperty withNationalIdentificationNumber(final String nationalIdentificationNumber) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setNationalIdentificationNumber(nationalIdentificationNumber);
			}
		};
	}

	public static PersonProperty withPassportNumber(final String passportNumber) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setPassportNumber(passportNumber);
			}
		};
	}

	public static PersonProperty withOccupation(final String occupation) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setOccupation(occupation);
			}
		};
	}

	public static PersonProperty withMaritalStatus(final String maritalStatus) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setMaritalStatus(maritalStatus);
			}
		};
	}

	public static PersonProperty withBloodType(final String bloodType) {
		return new PersonProperty() {

			@Override
			public void apply(PersonProvider personProvider, BaseProducer baseProducer) {
				personProvider.setBloodType(bloodType);
			}
		};
	}

}
