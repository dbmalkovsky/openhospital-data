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
package org.isf.generator.producer.person.locale.zh;

import org.isf.generator.producer.person.locale.AbstractAddress;

public class ZhAddress extends AbstractAddress {

	private static final String CITY = "市";
	private static final String NUMBER = "号";
	private static final String ROOM = "房";
	private static final String POSTCODE = "邮编";

	public ZhAddress(String streetNumber, String street, String apartmentNumber, String city, String postalCode) {
		super(street, streetNumber, apartmentNumber, postalCode, city);
	}

	@Override
	public String getAddressLine1() {
		String line = city + CITY + street + streetNumber + NUMBER;
		if (apartmentNumber.length() > 0) {
			return line + " " + apartmentNumber + ROOM;
		}
		return line;
	}

	@Override
	public String getAddressLine2() {
		return POSTCODE + " " + postalCode;
	}

}
