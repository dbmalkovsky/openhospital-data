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

import com.google.inject.Provider;

public interface CompanyProvider extends Provider<Company> {

	String DOMAIN = "domains";
	String COMPANY_SUFFIX = "companySuffixes";
	String COMPANY_NAME = "companyNames";
	String COMPANY_EMAIL = "companyEmails";

	@Override
	Company get();

	void generateName();

	void generateDomain();

	void generateEmail();

	void generateVatIdentificationNumber();

	void setName(String name);

	void setDomain(String domain);

	void setEmail(String email);

	void setVatIdentificationNumber(String vatIdentificationNumber);

}
