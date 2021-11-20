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

import static java.lang.Character.getNumericValue;
import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.List;
import java.util.Map;

import org.isf.generator.producer.person.PassportNumberProvider;
import org.isf.generator.producer.util.AlphaNumberSystem;

import com.google.common.collect.Maps;

public class PlPassportNumberProvider implements PassportNumberProvider {

	private static final int CHECKSUM_INDEX = 2;

	private static final int[] WEIGHTS = { 7, 3, 9, 1, 7, 3, 1, 7, 3 };

	private List<String> alphabet;

	private static Map<String, Integer> letterDigits = null;

	public PlPassportNumberProvider() {
		alphabet = AlphaNumberSystem.generateAlphabetList();
		int baseNum = 10;
		letterDigits = Maps.newHashMap();
		for (String letter : alphabet) {
			letterDigits.put(letter, baseNum++);
		}
	}

	@Override
	public String get() {
		char[] passport = new char[9];

		fillSeries(passport);

		fillDigits(passport);

		fillChecksum(passport);

		return valueOf(passport);
	}

	private void fillChecksum(char[] passport) {
		int checkSum = 0;
		for (int i = 0; i < 2; i++) {
			Integer checkSumValue = letterDigits.get(valueOf(passport[i]));
			checkSum += checkSumValue * WEIGHTS[i];
		}
		for (int i = 3; i < 9; i++) {
			int checkSumValue = getNumericValue(passport[i]);
			checkSum += checkSumValue * WEIGHTS[i];
		}
		passport[CHECKSUM_INDEX] = Integer.toString(checkSum % 10).charAt(0);
	}

	private void fillSeries(char[] passport) {
		char[] randomSeries = randomAlphabetic(2).toUpperCase().toCharArray();
		arraycopy(randomSeries, 0, passport, 0, randomSeries.length);
	}

	private void fillDigits(char[] passport) {
		char[] randomDigits = randomNumeric(6).toCharArray();
		arraycopy(randomDigits, 0, passport, 3, randomDigits.length);
	}

	public static Boolean passportCheckSumIsValid(String passportNumber) {
		char[] passport = passportNumber.toCharArray();
		int checkSum = 0;
		for (int i = 0; i < 2; i++) {
			Integer checkSumValue = letterDigits.get(valueOf(passport[i]));
			checkSum += checkSumValue * WEIGHTS[i];
		}
		for (int i = 2; i < 9; i++) {
			int checkSumValue = getNumericValue(passport[i]);
			checkSum += checkSumValue * WEIGHTS[i];
		}
		return checkSum % 10 == 0;
	}

}
