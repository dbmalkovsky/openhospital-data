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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.isf.generator.producer.util.LanguageCode;

public enum Country {
	Poland("PL", LanguageCode.PL), UnitedKingdom("GB", LanguageCode.EN), Australia("AU", LanguageCode.EN), USA("US", LanguageCode.EN), Canada("CA",
			LanguageCode.EN, LanguageCode.FR), Spain("ES", LanguageCode.ES), France("FR", LanguageCode.FR), Georgia("GE", LanguageCode.KA), Italy("IT",
			LanguageCode.IT), Germany("DE", LanguageCode.DE), Sweden("SE", LanguageCode.SV), Uganda("UG", LanguageCode.SW), China("CN", LanguageCode.ZH);

	//	ISO 3166 code
	private final String code;
	// ISO 639-1
	private final List<LanguageCode> languages;

	Country(String code, LanguageCode... language) {
		this.code = code;
		this.languages = Arrays.asList(language);
	}

	public String getCode() {
		return code;
	}

	public static List<Country> findCountryForLanguage(LanguageCode language) {
		return Arrays.stream(Country.values()).filter(country -> country.languages.contains(language)).collect(Collectors.toList());
	}
}
