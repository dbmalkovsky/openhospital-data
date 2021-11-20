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

public final class CompanyProperties {

	private CompanyProperties() {
	}

	public abstract static class CompanyProperty {

		public abstract void apply(CompanyProvider company);

		public static CompanyProperty withName(final String name) {
			return new CompanyProperty() {

				@Override
				public void apply(CompanyProvider companyProvider) {
					companyProvider.setName(name);
				}
			};
		}

		public static CompanyProperty withDomain(final String domain) {
			return new CompanyProperty() {

				@Override
				public void apply(CompanyProvider companyProvider) {
					companyProvider.setDomain(domain);
				}
			};
		}

		public static CompanyProperty withEmail(final String email) {
			return new CompanyProperty() {

				@Override
				public void apply(CompanyProvider companyProvider) {
					companyProvider.setEmail(email);
				}
			};
		}

		public static CompanyProperty withVatIdentificationNumber(final String vatIdentificationNumber) {
			return new CompanyProperty() {

				@Override
				public void apply(CompanyProvider companyProvider) {
					companyProvider.setVatIdentificationNumber(vatIdentificationNumber);
				}
			};
		}

	}

}
