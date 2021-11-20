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
package org.isf.generator.producer.company.locale.sv;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.isf.generator.producer.person.NationalIdentificationNumberProperties.dateOfBirth;
import static org.isf.generator.producer.person.locale.sv.SvNationalIdentificationNumberProvider.calculateChecksum;

import java.time.LocalDate;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.VATIdentificationNumberProvider;
import org.isf.generator.producer.person.NationalIdentificationNumberFactory;
import org.isf.generator.producer.person.NationalIdentificationNumberProvider;

/**
 * Swedish VAT Identification Number (known as Momsnummer in Sweden)
 * <p>
 * https://en.wikipedia.org/wiki/VAT_identification_number
 */
public class SvVATIdentificationNumberProvider implements VATIdentificationNumberProvider {

	private static final int VAT_IDENTIFICATION_NUMBER_LENGTH = 14;
	private static final int SOLE_TRADER_UPPER_AGE_LIMIT = 16;
	private static final int SOLE_TRADER_LOWER_AGE_LIMIT = 100;
	private static final String SE = "SE";

	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;
	private final NationalIdentificationNumberFactory nationalIdentificationNumberFactory;

	@Inject
	public SvVATIdentificationNumberProvider(BaseProducer baseProducer, DateProducer dateProducer,
			NationalIdentificationNumberFactory nationalIdentificationNumberFactory) {
		this.baseProducer = baseProducer;
		this.dateProducer = dateProducer;
		this.nationalIdentificationNumberFactory = nationalIdentificationNumberFactory;
	}

	@Override
	public String get() {
		boolean isSoleTrader = baseProducer.trueOrFalse(); // Approximately 50% probablilty of a company to be of type sole trader (enskild firma)
		if (isSoleTrader) {
			return generateVatNumberForSoleTrader();
		}

		int randomGroupNumber = baseProducer.randomElement(GroupNumber.class).getValue();
		String randomNumericBetween20And99 = leftPad(valueOf(baseProducer.randomBetween(20, 99)), 2, "0");
		String organizationNumberWithoutChecksum = randomGroupNumber + randomNumeric(1) + randomNumericBetween20And99 + randomNumeric(5);
		String organizationNumber = organizationNumberWithoutChecksum + calculateChecksum(organizationNumberWithoutChecksum);

		return SE + organizationNumber + "01";
	}

	private String generateVatNumberForSoleTrader() {
		LocalDate lowerAgeLimit = LocalDate.now().minusYears(SOLE_TRADER_LOWER_AGE_LIMIT);
		LocalDate upperAgeLimit = LocalDate.now().minusYears(SOLE_TRADER_UPPER_AGE_LIMIT);
		LocalDate dateOfBirth = dateProducer.randomDateBetweenTwoDates(lowerAgeLimit, upperAgeLimit);
		NationalIdentificationNumberProvider nationalIdentificationNumberProvider = nationalIdentificationNumberFactory.produceNationalIdentificationNumberProvider(
				dateOfBirth(dateOfBirth));
		String personalIdentityNumber = nationalIdentificationNumberProvider.get().getValue();
		return SE + personalIdentityNumber.replace("-", "") + "01";
	}

	/**
	 * @param vatIdentificationNumber Swedish VAT Identification Number
	 * @return vatIdentificationNumber validity
	 */
	public static boolean isValid(String vatIdentificationNumber) {
		int length = vatIdentificationNumber.length();
		if (length != VAT_IDENTIFICATION_NUMBER_LENGTH) {
			return false;
		}

		int checksum = Integer.parseInt(Character.toString(vatIdentificationNumber.charAt(length - 3)));
		int checkDigit = calculateChecksum(vatIdentificationNumber.substring(2, vatIdentificationNumber.length() - 2));

		return checkDigit == checksum;
	}

	/**
	 * Group number used to determine the first numer in a Swedish organization number
	 * Enum is translated from Swedish wiki https://sv.wikipedia.org/wiki/Organisationsnummer
	 */
	private enum GroupNumber {
		ESTATE(1),                                              // Dödsbon
		STATE_OR_COUNTY_OR_MUNICIPALITY_OR_PARISH(2),           // Stat, landsting, kommuner, församlingar
		FOREIGN_COMPANY(3),                                     // Utländska företag som bedriver näringsverksamhet eller äger fastigheter i Sverige
		LIMITED_COMPANY(5),                                     // Aktiebolag
		PARTNERSHIP(6),                                         // Enkelt bolag
		ECONOMIC_ASSOCIATION(7),                                // Ekonomiska föreningar
		NON_PROFIT_ASSOCIATION_OR_FOUNDATION(8),                // Ideella föreningar och stiftelser
		TRADING_COMPANY_OR_LIMITED_COMPANY_OR_PARTNERSHIP(9);   // Handelsbolag, kommanditbolag och enkla bolag

		private final int value;

		GroupNumber(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

}
