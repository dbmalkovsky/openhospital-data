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

import static org.apache.commons.lang3.StringUtils.lowerCase;

import org.isf.generator.producer.company.Company;
import org.isf.generator.producer.util.TextUtils;

import com.google.inject.Provider;

public class CompanyEmailProvider implements Provider<String> {

	private final String firstName;
	private final String lastName;
	private final Company company;

	public CompanyEmailProvider(String firstName, String lastName, Company company) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
	}

	@Override
	public String get() {
		String email = lowerCase(firstName + '.' + lastName + '@' + company.getDomain()).replaceAll(" ", ".");
		return TextUtils.stripSharpS(TextUtils.stripAccents(email));
	}

}
