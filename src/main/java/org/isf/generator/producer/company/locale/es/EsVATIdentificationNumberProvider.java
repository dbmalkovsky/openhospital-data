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
package org.isf.generator.producer.company.locale.es;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.regex.Pattern;

import org.isf.generator.producer.VATIdentificationNumberProvider;

/**
 * Spanish VAT Identification Number (known as Número de Identificación Fiscal (for freelancers) or Código de Identificación Fiscal (for companies)	 in Spain)
 * <p>
 * https://en.wikipedia.org/wiki/VAT_identification_number
 */
public class EsVATIdentificationNumberProvider implements VATIdentificationNumberProvider {

	private static final String REGEX_CIF = "^[A-Z][0-9]{2}[0-9]{5}([KPQSABEH]|[0-9]|[A-Z])$";

	private Pattern regexCif;

	public EsVATIdentificationNumberProvider() {
		this.regexCif = Pattern.compile(REGEX_CIF);
	}

	@Override
	public String get() {
		return String.format("%s%s%s", randomAlphabetic(1).toUpperCase(), randomNumeric(7), randomAlphanumeric(1).toUpperCase());
	}

	public boolean isValid(String cif) {
		return this.regexCif.matcher(cif).matches();
	}

}
