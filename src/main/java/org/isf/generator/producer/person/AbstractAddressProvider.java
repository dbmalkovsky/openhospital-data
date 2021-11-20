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

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;

public abstract class AbstractAddressProvider implements AddressProvider {

	private static final String POSTAL_CODE_FORMAT = "postal_code";

	private static final String CITY = "city";

	private static final String STREET = "street";

	protected final BaseProducer baseProducer;

	protected final DataMaster dataMaster;

	protected AbstractAddressProvider(DataMaster dataMaster, BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
		this.dataMaster = dataMaster;
	}

	public String getCity() {
		return dataMaster.getRandomValue(CITY);
	}

	public String getPostalCode() {
		String postalCodeFormat = dataMaster.getRandomValue(POSTAL_CODE_FORMAT);
		return baseProducer.numerify(postalCodeFormat);
	}

	public String getStreet() {
		return dataMaster.getRandomValue(STREET);
	}

	public String getStreetNumber() {
		return String.valueOf(baseProducer.randomBetween(1, 199));
	}

	public String getApartmentNumber() {
		return baseProducer.trueOrFalse() ? String.valueOf(baseProducer.randomBetween(1, 350)) : "";
	}

}
