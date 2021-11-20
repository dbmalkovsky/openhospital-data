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
package org.isf.generator.producer.person.locale.en;

import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.util.List;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * English National Identity Card Number (known as Social Security Number)
 */
public class EnNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	private static final int SSN_LENGTH = 11;
	private static final int AREA_NUMBER_LENGTH = 3;
	private static final int GROUP_NUMBER_LENGTH = 2;
	private static final int GROUP_NUMBER_INDEX = 4;
	private static final List<Integer> HYPHEN_INDEXES = Lists.newArrayList(3, 6);
	private static final int SERIAL_NUMBER_LENGTH = 4;
	private static final int SERIAL_NUMBER_INDEX = 7;

	private final BaseProducer baseProducer;

	@Inject
	public EnNationalIdentityCardNumberProvider(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
	}

	@Override
	public String get() {
		char[] ssn = new char[SSN_LENGTH];

		fillHyphens(ssn);

		fillAreaNumber(ssn);

		fillGroupNumber(ssn);

		fillSerialNumber(ssn);

		return valueOf(ssn);
	}

	private void fillHyphens(char[] ssn) {
		for (Integer index : HYPHEN_INDEXES) {
			ssn[index] = '-';
		}
	}

	private void fillAreaNumber(char[] ssn) {
		String number;
		do {
			number = valueOf(baseProducer.randomBetween(1, 899));
		} while (number.equals("666"));
		char[] digits = leftPad(number, AREA_NUMBER_LENGTH, "0").toCharArray();
		arraycopy(digits, 0, ssn, 0, digits.length);
	}

	private void fillGroupNumber(char[] ssn) {
		String number = valueOf(baseProducer.randomBetween(1, 99));
		char[] digits = leftPad(number, GROUP_NUMBER_LENGTH, "0").toCharArray();
		arraycopy(digits, 0, ssn, GROUP_NUMBER_INDEX, digits.length);
	}

	private void fillSerialNumber(char[] ssn) {
		String number = valueOf(baseProducer.randomBetween(1, 9999));
		char[] digits = leftPad(number, SERIAL_NUMBER_LENGTH, "0").toCharArray();
		arraycopy(digits, 0, ssn, SERIAL_NUMBER_INDEX, digits.length);
	}

}

