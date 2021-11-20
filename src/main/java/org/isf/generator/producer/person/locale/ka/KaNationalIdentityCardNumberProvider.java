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
package org.isf.generator.producer.person.locale.ka;

import java.util.function.Supplier;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;

public class KaNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	private static class OldCardNumberProvider implements NationalIdentityCardNumberProvider {

		private static char[] georgianChar = "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰ".toCharArray();

		private final BaseProducer baseProducer;

		public OldCardNumberProvider(BaseProducer baseProducer) {
			this.baseProducer = baseProducer;
		}

		@Override
		public String get() {
			char geChar = georgianChar[baseProducer.randomInt(georgianChar.length - 1)];
			return "N" + geChar + baseProducer.numerify("#######");
		}

	}

	private static class NewCardNumberProvider implements NationalIdentityCardNumberProvider {

		private static final String NEW_CARD_MASK = "##??#####";

		private final BaseProducer baseProducer;

		public NewCardNumberProvider(BaseProducer baseProducer) {
			this.baseProducer = baseProducer;
		}

		@Override
		public String get() {
			return baseProducer.bothify(NEW_CARD_MASK).toUpperCase();
		}

	}

	private final Supplier<NationalIdentityCardNumberProvider> formatPicker;

	@Inject
	public KaNationalIdentityCardNumberProvider(BaseProducer baseProducer) {
		NationalIdentityCardNumberProvider oldCardNumberProvider = new OldCardNumberProvider(baseProducer);
		NationalIdentityCardNumberProvider newCardNumberProvider = new NewCardNumberProvider(baseProducer);
		formatPicker = () -> baseProducer.trueOrFalse() ? oldCardNumberProvider : newCardNumberProvider;
	}

	@Override
	public String get() {
		NationalIdentityCardNumberProvider numberProvider = formatPicker.get();
		return numberProvider.get();
	}

}
