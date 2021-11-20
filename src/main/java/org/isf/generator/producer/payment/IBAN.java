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

public class IBAN {

	private final String accountNumber;
	private final String identificationNumber;
	private final String branchCode;
	private final String checkDigit;
	private final String accountType;
	private final String bankCode;
	private final String bban;
	private final String country;
	private final String nationalCheckDigit;
	private final String ownerAccountType;
	private final String ibanNumber;

	public IBAN(String accountNumber, String identificationNumber, String branchCode, String checkDigit, String accountType, String bankCode, String bban,
			String country, String nationalCheckDigit, String ownerAccountType, String ibanNumber) {
		this.accountNumber = accountNumber;
		this.identificationNumber = identificationNumber;
		this.branchCode = branchCode;
		this.checkDigit = checkDigit;
		this.accountType = accountType;
		this.bankCode = bankCode;
		this.bban = bban;
		this.country = country;
		this.nationalCheckDigit = nationalCheckDigit;
		this.ownerAccountType = ownerAccountType;
		this.ibanNumber = ibanNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getCheckDigit() {
		return checkDigit;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBban() {
		return bban;
	}

	public String getCountry() {
		return country;
	}

	public String getNationalCheckDigit() {
		return nationalCheckDigit;
	}

	public String getOwnerAccountType() {
		return ownerAccountType;
	}

	public String getIbanNumber() {
		return ibanNumber;
	}

}
