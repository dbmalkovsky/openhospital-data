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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreditCard {

	private final String cardVendor;
	private final String cardNumber;
	private final String cvv;
	private final LocalDateTime expiryDate;

	public CreditCard(String cardVendor, String cardNumber, String cvv, LocalDateTime expiryDate) {
		this.cardVendor = cardVendor;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
	}

	public String getVendor() {
		return cardVendor;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public String getExpiryDateAsString() {
		return String.format("%02d/%s", expiryDate.getMonthValue(), DateTimeFormatter.ofPattern("uu").format(expiryDate));
	}

}
