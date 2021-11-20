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
package org.isf.generator.producer.company.locale.en;

import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.util.Set;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.VATIdentificationNumberProvider;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * American VAT Identification Number (known as Employer Identification Number or EIN in the United States)
 * <p>
 * https://en.wikipedia.org/wiki/Employer_Identification_Number
 */
public class EnVATIdentificationNumberProvider implements VATIdentificationNumberProvider {

	private static final int EIN_LENGTH = 10;
	private static final int HYPHEN_INDEX = 2;
	private static final int SERIAL_NUMBER_LENGTH = 7;
	private static final int SERIAL_NUMBER_INDEX = 3;
	private static final int AREA_NUMBER_LENGTH = 2;

	private BaseProducer baseProducer;
	private static Set<Integer> excludedNumbers = Sets.newHashSet(7, 8, 9, 17, 18, 19, 28, 29, 41, 47, 49, 69, 70, 79, 89, 96, 97);

	@Inject
	public EnVATIdentificationNumberProvider(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
	}

	@Override
	public String get() {
		char[] ein = new char[EIN_LENGTH];

		fillHyphen(ein);

		fillAreaNumber(ein);

		fillSerialNumber(ein);

		return valueOf(ein);
	}

	private void fillSerialNumber(char[] ein) {
		String number = valueOf(baseProducer.randomBetween(1, 9999));
		char[] digits = leftPad(number, SERIAL_NUMBER_LENGTH, "0").toCharArray();
		arraycopy(digits, 0, ein, SERIAL_NUMBER_INDEX, digits.length);
	}

	private void fillAreaNumber(char[] ein) {
		int number;
		do {
			number = baseProducer.randomBetween(0, 99);
		} while (excludedNumbers.contains(number));
		char[] digits = leftPad(Integer.toString(number), AREA_NUMBER_LENGTH, "0").toCharArray();
		arraycopy(digits, 0, ein, 0, digits.length);

	}

	private void fillHyphen(char[] ein) {
		ein[HYPHEN_INDEX] = '-';
	}

}
