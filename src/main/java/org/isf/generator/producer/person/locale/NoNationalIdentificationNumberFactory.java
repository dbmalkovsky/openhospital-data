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

import java.time.LocalDate;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.person.NationalIdentificationNumber;
import org.isf.generator.producer.person.NationalIdentificationNumberFactory;
import org.isf.generator.producer.person.NationalIdentificationNumberProperties;
import org.isf.generator.producer.person.NationalIdentificationNumberProvider;
import org.isf.generator.producer.person.Person;

import com.google.inject.assistedinject.Assisted;

public class NoNationalIdentificationNumberFactory implements NationalIdentificationNumberFactory {

	@Inject
	public NoNationalIdentificationNumberFactory(BaseProducer baseProducer, DateProducer dateProducer) {
	}

	@Override
	public NoNationalIdentificationNumberProvider produceNationalIdentificationNumberProvider(NationalIdentificationNumberProperties.Property... properties) {
		return new NoNationalIdentificationNumberProvider(null, null, properties);
	}

	public static class NoNationalIdentificationNumberProvider implements NationalIdentificationNumberProvider {

		@Inject
		public NoNationalIdentificationNumberProvider(DateProducer dateProducer, BaseProducer baseProducer,
				@Assisted NationalIdentificationNumberProperties.Property... properties) {
		}

		@Override
		public NationalIdentificationNumber get() {
			return new NationalIdentificationNumber("");
		}

		@Override
		public void setIssueDate(LocalDate dateOfBirth) {
		}

		@Override
		public void setSex(Person.Sex sex) {
		}

	}

}
