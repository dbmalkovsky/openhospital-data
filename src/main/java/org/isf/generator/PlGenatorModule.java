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
package org.isf.generator;

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.RandomGenerator;
import org.isf.generator.producer.VATIdentificationNumberProvider;
import org.isf.generator.producer.company.locale.pl.PlVATIdentificationNumberProvider;
import org.isf.generator.producer.person.AddressProvider;
import org.isf.generator.producer.person.NationalIdentificationNumberFactory;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;
import org.isf.generator.producer.person.PassportNumberProvider;
import org.isf.generator.producer.person.locale.pl.PlAddressProvider;
import org.isf.generator.producer.person.locale.pl.PlNationalIdentificationNumberFactory;
import org.isf.generator.producer.person.locale.pl.PlNationalIdentityCardNumberProvider;
import org.isf.generator.producer.person.locale.pl.PlPassportNumberProvider;

public class PlGenatorModule extends GeneratorModule {

	public PlGenatorModule(DataMaster dataMaster, RandomGenerator randomGenerator) {
		super(dataMaster, randomGenerator);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(NationalIdentificationNumberFactory.class).to(PlNationalIdentificationNumberFactory.class);
		bind(NationalIdentityCardNumberProvider.class).to(PlNationalIdentityCardNumberProvider.class);
		bind(VATIdentificationNumberProvider.class).to(PlVATIdentificationNumberProvider.class);
		bind(AddressProvider.class).to(PlAddressProvider.class);
		bind(PassportNumberProvider.class).to(PlPassportNumberProvider.class);
	}

}
