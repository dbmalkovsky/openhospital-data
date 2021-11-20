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
package org.isf.generator.producer.person.locale.es;

import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;

/**
 * Spanish National Identity Card Number (known as Documento Nacional de Identidad or DNI)
 */
public class EsNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	private static final String REGEX_DNI = "^\\d{8}([-]?)[A-Z]$";
	private Pattern regexDni;

	public EsNationalIdentityCardNumberProvider() {
		this.regexDni = Pattern.compile(REGEX_DNI);
	}

	@Override
	public String get() {
		return String.format("%s-%s", RandomStringUtils.randomNumeric(8), RandomStringUtils.randomAlphabetic(1).toUpperCase());
	}

	public boolean isValid(String dni) {
		return this.regexDni.matcher(dni).matches();
	}

}
