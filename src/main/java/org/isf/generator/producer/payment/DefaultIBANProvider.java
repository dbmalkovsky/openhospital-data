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

import java.util.IllegalFormatCodePointException;
import java.util.List;

import javax.inject.Inject;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.iban4j.UnsupportedCountryException;
import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.person.Country;

import com.google.common.base.Strings;
import com.google.inject.assistedinject.Assisted;

public class DefaultIBANProvider implements IBANProvider {

	protected DataMaster dataMaster;
	protected BaseProducer baseProducer;
	protected CountryCode countryCode;
	protected String accountNumber;
	protected String bankCode;
	protected String branchCode;
	protected String nationalCheckDigit;

	@Inject
	public DefaultIBANProvider(BaseProducer baseProducer, DataMaster dataMaster, @Assisted IBANProperties.Property... properties) {

		this.dataMaster = dataMaster;
		this.baseProducer = baseProducer;
		for (IBANProperties.Property property : properties) {
			property.apply(this);
		}
	}

	@Override
	public IBAN get() {
		try {

			fillCountryCode();

			try {

				Iban.Builder builder = new Iban.Builder().countryCode(countryCode).bankCode(bankCode).branchCode(branchCode)
						.nationalCheckDigit(nationalCheckDigit);
				if (!Strings.isNullOrEmpty(accountNumber)) {
					builder.accountNumber(accountNumber);
				}
				Iban iban = builder.buildRandom();

				String identificationNumber = iban.getIdentificationNumber();
				String checkDigit = iban.getCheckDigit();
				String accountType = iban.getAccountType();
				String bban = iban.getBban();
				String ownerAccountType = iban.getOwnerAccountType();
				String ibanNumber = iban.toString();

				return new IBAN(accountNumber, identificationNumber, branchCode, checkDigit, accountType, bankCode, bban, countryCode.getName(),
						nationalCheckDigit, ownerAccountType, ibanNumber);
			} catch (UnsupportedCountryException e) {
				return null;
			}
		} catch (IllegalFormatCodePointException e) {
			throw new IllegalArgumentException("Invalid iban " + e.getMessage(), e);
		}
	}

	@Override
	public void fillCountryCode() {
		if (countryCode == null) {
			List<Country> countries = Country.findCountryForLanguage(dataMaster.getLanguage());
			Country country = baseProducer.randomElement(countries);
			countryCode = CountryCode.valueOf(country.getCode());
		}
	}

	@Override
	public void setNationalCheckDigit(String nationalCheckDigit) {
		this.nationalCheckDigit = nationalCheckDigit;
	}

	@Override
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Override
	public void setCountry(String country) {
		this.countryCode = CountryCode.valueOf(country);
	}

	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
