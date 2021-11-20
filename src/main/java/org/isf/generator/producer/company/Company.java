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
package org.isf.generator.producer.company;

public class Company {

	private final String name;
	private final String domain;
	private final String email;
	private final String vatIdentificationNumber;

	public Company(String name, String domain, String email, String vatIdentificationNumber) {
		this.name = name;
		this.domain = domain;
		this.email = email;
		this.vatIdentificationNumber = vatIdentificationNumber;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return "http://www." + domain;
	}

	public String getEmail() {
		return email + "@" + domain;
	}

	public String getDomain() {
		return domain;
	}

	public String getVatIdentificationNumber() {
		return vatIdentificationNumber;
	}

}
