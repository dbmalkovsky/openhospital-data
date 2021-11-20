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
package org.isf.generator.producer.person.locale.pl;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.person.NationalIdentificationNumber;
import org.isf.generator.producer.person.NationalIdentificationNumberProperties;
import org.isf.generator.producer.person.NationalIdentificationNumberProvider;
import org.isf.generator.producer.person.Person;

import com.google.inject.assistedinject.Assisted;

/**
 * Spanish National Identification Number (known as PESEL or Polish Powszechny Elektroniczny System Ewidencji Ludności)
 * <p>
 * Universal Electronic System for Registration of the Population)
 * More info: http://en.wikipedia.org/wiki/PESEL
 */
public class PlNationalIdentificationNumberProvider implements NationalIdentificationNumberProvider {

	private static final int NATIONAL_IDENTIFICATION_NUMBER_LENGTH = 11;
	private static final int VALIDITY_IN_YEARS = 10;

	private static final int[] PERIOD_WEIGHTS = { 80, 0, 20, 40, 60 };
	private static final int PERIOD_FACTOR = 100;
	private static final int BEGIN_YEAR = 1800;

	private static final int[] WEIGHTS = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
	private static final int MAX_SERIAL_NUMBER = 999;
	private static final int TEN = 10;

	private static final int[] SEX_FIELDS = { 0, 2, 4, 6, 8 };

	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;
	private LocalDate issueDate;
	private Person.Sex sex;

	@Inject
	public PlNationalIdentificationNumberProvider(DateProducer dateProducer, BaseProducer baseProducer,
			@Assisted NationalIdentificationNumberProperties.Property... properties) {
		this.dateProducer = dateProducer;
		this.baseProducer = baseProducer;

		with(properties);

	}

	public void with(NationalIdentificationNumberProperties.Property[] properties) {
		for (NationalIdentificationNumberProperties.Property property : properties) {
			property.apply(this);
		}
	}

	@Override
	public NationalIdentificationNumber get() {

		if (issueDate == null) {
			issueDate = dateProducer.randomDateInThePast(VALIDITY_IN_YEARS).toLocalDate();
		}
		if (sex == null) {
			sex = baseProducer.trueOrFalse() ? Person.Sex.MALE : Person.Sex.FEMALE;
		}

		return new NationalIdentificationNumber(generate());
	}

	private String generate() {
		int month = calculateMonth(issueDate.getMonthValue(), issueDate.getYear());
		int day = issueDate.getDayOfMonth();
		int serialNumber = baseProducer.randomInt(MAX_SERIAL_NUMBER);
		int sexCode = calculateSexCode(sex);

		String nationalIdentificationNumber = format("%s%02d%02d%03d%d", DateTimeFormatter.ofPattern("uu").format(issueDate), month, day, serialNumber,
				sexCode);

		return nationalIdentificationNumber + calculateChecksum(nationalIdentificationNumber);
	}

	@Override
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public void setSex(Person.Sex sex) {
		this.sex = sex;
	}

	/**
	 * @param nationalIdentificationNumber National Identification Number (PESEL)
	 * @return nationalIdentificationNumber validity
	 */
	public static boolean isValid(String nationalIdentificationNumber) {
		int size = nationalIdentificationNumber.length();
		if (size != NATIONAL_IDENTIFICATION_NUMBER_LENGTH) {
			return false;
		}

		int checksum = Integer.parseInt(nationalIdentificationNumber.substring(size - 1));
		int checkDigit = calculateChecksum(nationalIdentificationNumber);

		return checkDigit == checksum;

	}

	private int calculateMonth(int month, int year) {
		return month + PERIOD_WEIGHTS[(year - BEGIN_YEAR) / PERIOD_FACTOR];
	}

	// This should be tested
	private int calculateSexCode(Person.Sex sex) {
		return SEX_FIELDS[baseProducer.randomInt(SEX_FIELDS.length - 1)] + (sex == Person.Sex.MALE ? 1 : 0);
	}

	private static int calculateChecksum(String nationalIdentificationNumber) {
		int sum = 0;
		int i = 0;
		for (int weight : WEIGHTS) {
			int digit = Character.digit(nationalIdentificationNumber.charAt(i++), 10);
			sum += digit * weight;
		}
		int checksum = sum % TEN;
		if (0 == checksum) {
			return 0;
		}
		return TEN - checksum;
	}

}
