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
package org.isf.generator.producer.person.locale;

import org.isf.generator.producer.person.Address;

/**
 * A base of all addresses. It carries all needed fields, but leaves most of formatting to subclasses.
 */
public abstract class AbstractAddress implements Address {

	protected final String street;
	protected final String streetNumber;
	protected final String apartmentNumber;
	protected final String postalCode;
	protected final String city;

	protected AbstractAddress(String street, String streetNumber, String apartmentNumber, String postalCode, String city) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.apartmentNumber = apartmentNumber;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public String getStreetNumber() {
		return streetNumber;
	}

	@Override
	public String getApartmentNumber() {
		return apartmentNumber;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public abstract String getAddressLine1();

	@Override
	public abstract String getAddressLine2();

	@Override
	public String toString() {
		return getAddressLine1() + System.lineSeparator() + getAddressLine2();
	}

}
