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
import java.time.Period;

import javax.inject.Inject;

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;

import com.google.inject.Provider;

public class CreditCardProvider implements Provider<CreditCard> {

	private static final Period DEFAULT_VALIDITY = Period.ofMonths(36);
	private static final String DATA_KEY = "creditCardPrefixes";
	private static final String CARD_VENDOR = "Visa";

	private final DataMaster dataMaster;
	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;

	@Inject
	public CreditCardProvider(DataMaster dataMaster, BaseProducer baseProducer, DateProducer dateProducer) {
		this.dataMaster = dataMaster;
		this.baseProducer = baseProducer;
		this.dateProducer = dateProducer;
	}

	@Override
	public CreditCard get() {
		String randomNumber = generateNumber();

		LocalDateTime expiryDate = dateProducer.randomDateBetweenNowAndFuturePeriod(DEFAULT_VALIDITY);
		return new CreditCard(CARD_VENDOR, randomNumber, baseProducer.numerify("###"), expiryDate);
	}

	private String generateNumber() {
		Integer prefix = dataMaster.getValuesOfType(DATA_KEY, CARD_VENDOR, Integer.class);
		String stringPrefix = String.valueOf(prefix);
		StringBuilder builder = new StringBuilder(stringPrefix);
		for (int i = stringPrefix.length(); i < 15; i++) {
			builder.append("#");
		}
		return completeNumber(baseProducer.numerify(builder.toString()));
	}

	private String completeNumber(String creditCardNumber) {
		int sum = 0;
		for (int i = 0; i < creditCardNumber.length(); i++) {
			int n = Character.getNumericValue(creditCardNumber.charAt(i));
			if (i % 2 == 0) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
		}
		return creditCardNumber + sum * 9 % 10;
	}

}
