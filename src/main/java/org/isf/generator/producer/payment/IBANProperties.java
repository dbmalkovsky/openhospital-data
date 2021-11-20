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
package org.isf.generator.producer.payment;

import java.util.HashMap;
import java.util.Map;

import org.iban4j.CountryCode;
import org.isf.generator.producer.util.LanguageCode;

public final class IBANProperties {

	private static final HashMap<LanguageCode, CountryCode> COUNTRIES = new HashMap<>();

	static {
		COUNTRIES.put(LanguageCode.PL, CountryCode.PL);
		COUNTRIES.put(LanguageCode.EN, CountryCode.GB);
		COUNTRIES.put(LanguageCode.ES, CountryCode.ES);
		COUNTRIES.put(LanguageCode.FR, CountryCode.FR);
		COUNTRIES.put(LanguageCode.KA, CountryCode.GE);
		COUNTRIES.put(LanguageCode.IT, CountryCode.IT);
		COUNTRIES.put(LanguageCode.DE, CountryCode.DE);
		COUNTRIES.put(LanguageCode.SV, CountryCode.SV);
		COUNTRIES.put(LanguageCode.ZH, CountryCode.TW);
	}

	private IBANProperties() {
	}

	public abstract static class Property {

		public abstract void apply(IBANProvider provider);

	}

	public static Property branchCode(final String branchCode) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setBranchCode(branchCode);
			}
		};
	}

	public static Property nationalCheckDigit(final String nationalCheckDigit) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setNationalCheckDigit(nationalCheckDigit);
			}
		};
	}

	public static Property accountNumber(final String accountNumber) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setAccountNumber(accountNumber);
			}
		};
	}

	public static Property country(final String country) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setCountry(country);
			}
		};
	}

	public static Property language(final String language) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setCountry(countryFromLanguage(language));
			}
		};
	}

	private static String countryFromLanguage(String lang) {
		return COUNTRIES.entrySet().stream().filter(locale -> locale.getKey().name().equals(lang)).map(Map.Entry::getValue).map(CountryCode::getAlpha2)
				.findFirst().orElse("PL");
	}

	public static Property bankCode(final String bankCode) {
		return new Property() {

			@Override
			public void apply(IBANProvider provider) {
				provider.setBankCode(bankCode);
			}
		};
	}

}
