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
package org.isf.generator.producer.person.locale.de;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;

/**
 * German Identity Card (known as Personalausweis)
 * <p>
 * https://en.wikipedia.org/wiki/German_identity_card
 */
public class DeNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	private static final String[] ID_CARD_TYPE_LETTERS = { "L", "M", "N", "P", "R", "T", "V", "W", "X", "Y" };

	private static final String VALID_NUMBER_PATTERN = "^[LMNPRTVWXY][0-9]{8}$";

	private final BaseProducer baseProducer;

	@Inject
	public DeNationalIdentityCardNumberProvider(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
	}

	@Override
	public String get() {
		return baseProducer.randomElement(ID_CARD_TYPE_LETTERS) + randomNumeric(8);
	}

	public boolean isValid(String nationalIdentityCardNumber) {
		return nationalIdentityCardNumber.matches(VALID_NUMBER_PATTERN);
	}

}
