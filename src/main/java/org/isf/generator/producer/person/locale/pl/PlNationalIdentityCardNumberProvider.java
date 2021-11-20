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

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.copyValueOf;
import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.time.LocalDate;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;
import org.isf.generator.producer.util.AlphaNumberSystem;

import com.google.common.annotations.VisibleForTesting;

/**
 * Polish Identity Card Number
 * <p>
 * http://en.wikipedia.org/wiki/Polish_identity_card
 */
public class PlNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	@VisibleForTesting
	static final int ISSUING_BEGIN = 2000;

	private static final int[] WEIGHTS = { 7, 3, 1, 0, 7, 3, 1, 7, 3 };
	private static final int CHECKSUM_INDEX = 3;

	private static final int DIGITS_PART_SIZE = 5;

	@VisibleForTesting
	static final int MAX_DIGITS_PART_VALUE = 99999;

	@VisibleForTesting
	static final int LETTER_WEIGHT = 45;
	private static final int LETTERS_PART_SIZE = 3;
	private static final int LETTER_VALUE_MODIFIER = 10;
	private static final int ALPHABET_SIZE = 26;

	private static final int BASE_TEN = 10;

	private final DateProducer dateProducer;
	private final BaseProducer baseProducer;

	@Inject
	public PlNationalIdentityCardNumberProvider(DateProducer dateProducer, BaseProducer baseProducer) {
		this.dateProducer = dateProducer;
		this.baseProducer = baseProducer;
	}

	@Override
	public String get() {

		LocalDate dateTime = dateProducer.randomDateBetweenYearAndNow(ISSUING_BEGIN).toLocalDate();

		return get(dateTime);
	}

	public String get(LocalDate date) {

		checkArgument(date.getYear() >= ISSUING_BEGIN, "Polish ID was introduced in 2000");

		char[] id = new char[WEIGHTS.length];

		fillLettersPart(date.getYear(), id);
		fillDigitsPart(id);

		char checksum = calculateChecksum(id);

		id[CHECKSUM_INDEX] = checksum;

		return copyValueOf(id);

	}

	public boolean isValid(String id) {
		int checksum = calculateChecksum(id.toCharArray());

		return id.charAt(CHECKSUM_INDEX) == checksum;
	}

	private char calculateChecksum(char[] id) {
		int index = 0;
		int checksum = 0;

		for (int weight : WEIGHTS) {
			int value = 0;
			if (index < CHECKSUM_INDEX) {
				value = id[index] - 'A' + LETTER_VALUE_MODIFIER;
			} else if (index > CHECKSUM_INDEX) {
				value = id[index] - '0';
			}
			index++;
			checksum += weight * value;
		}

		return valueOf(checksum % BASE_TEN).charAt(0);
	}

	private void fillDigitsPart(char[] id) {
		String num = valueOf(baseProducer.randomInt(MAX_DIGITS_PART_VALUE));
		char[] digits = leftPad(num, DIGITS_PART_SIZE, '0').toCharArray();
		arraycopy(digits, 0, id, CHECKSUM_INDEX + 1, digits.length);
	}

	private void fillLettersPart(int year, char[] id) {
		int maxPrefix = (year - ISSUING_BEGIN) * LETTER_WEIGHT;
		int range = baseProducer.randomBetween(maxPrefix, maxPrefix + LETTER_WEIGHT);
		String prefix = AlphaNumberSystem.convertToString(range, ALPHABET_SIZE);
		char[] charArray = leftPad(prefix, LETTERS_PART_SIZE, 'A').toCharArray();
		arraycopy(charArray, 0, id, 0, charArray.length);
	}

}
