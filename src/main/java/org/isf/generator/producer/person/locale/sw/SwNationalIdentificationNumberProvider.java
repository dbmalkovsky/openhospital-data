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
package org.isf.generator.producer.person.locale.sw;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.person.NationalIdentificationNumber;
import org.isf.generator.producer.person.NationalIdentificationNumberProperties;
import org.isf.generator.producer.person.NationalIdentificationNumberProvider;
import org.isf.generator.producer.person.Person;

import com.google.inject.assistedinject.Assisted;

/**
 * Uganda National Identification Number
 */
public class SwNationalIdentificationNumberProvider implements NationalIdentificationNumberProvider {

	private static final int NATIONAL_IDENTIFICATION_NUMBER_LENGTH = 11;
	private static final int VALIDITY_IN_YEARS = 15;

	private final DateProducer dateProducer;
	LocalDate dateOfBirth;
	private Person.Sex sex;

	@Inject
	public SwNationalIdentificationNumberProvider(DateProducer dateProducer, BaseProducer baseProducer,
			@Assisted NationalIdentificationNumberProperties.Property... properties) {
		this.dateProducer = dateProducer;
		with(properties);
	}

	public void with(NationalIdentificationNumberProperties.Property[] properties) {
		for (NationalIdentificationNumberProperties.Property property : properties) {
			property.apply(this);
		}
	}

	@Override
	public NationalIdentificationNumber get() {
		if (dateOfBirth == null) {
			dateOfBirth = dateProducer.randomDateInThePast(VALIDITY_IN_YEARS).toLocalDate();
		}
		// Have to be at least 16 to get the code
		if ((int) ChronoUnit.YEARS.between(dateOfBirth, LocalDateTime.now()) > 15) {
			return new NationalIdentificationNumber(RandomStringUtils.randomNumeric(NATIONAL_IDENTIFICATION_NUMBER_LENGTH));
		}
		return new NationalIdentificationNumber("");
	}

	@Override
	public void setIssueDate(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public void setSex(Person.Sex sex) {
		this.sex = sex;
	}

}
