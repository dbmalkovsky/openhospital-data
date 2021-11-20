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
package org.isf.generator.producer.util;

import static java.lang.String.valueOf;

import java.util.List;

import com.google.common.collect.Lists;

public final class AlphaNumberSystem {

	private static final char[] ALPHABET_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	public static List<String> generateAlphabetList() {
		List<String> alphabetList = Lists.newArrayList();
		for (char letter : ALPHABET_CHARS) {
			alphabetList.add(valueOf(letter));
		}
		return alphabetList;
	}

	private AlphaNumberSystem() {
	}

	public static String convertToString(final int numberToConvert, final int base) {
		int number = numberToConvert;
		final char[] buffer = new char[(numberToConvert / ALPHABET_CHARS.length) + 1];
		int charPosition = buffer.length - 1;

		if (number == 0) {
			buffer[charPosition--] = ALPHABET_CHARS[number];
		} else {
			while (number > 0) {
				buffer[charPosition--] = ALPHABET_CHARS[number % base];
				number /= base;
			}
		}
		return new String(buffer, charPosition + 1, buffer.length - charPosition - 1);
	}

}
