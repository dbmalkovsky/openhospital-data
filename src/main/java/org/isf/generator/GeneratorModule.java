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
import org.isf.generator.producer.company.CompanyFactory;
import org.isf.generator.producer.company.CompanyProvider;
import org.isf.generator.producer.company.DefaultCompanyProvider;
import org.isf.generator.producer.payment.DefaultIBANProvider;
import org.isf.generator.producer.payment.IBANFactory;
import org.isf.generator.producer.payment.IBANProvider;
import org.isf.generator.producer.person.DefaultPersonProvider;
import org.isf.generator.producer.person.PersonFactory;
import org.isf.generator.producer.person.PersonProvider;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public abstract class GeneratorModule extends AbstractModule {

	private final RandomGenerator randomGenerator;
	private final DataMaster dataMaster;

	protected GeneratorModule(DataMaster dataMaster, RandomGenerator randomGenerator) {
		this.dataMaster = dataMaster;
		this.randomGenerator = randomGenerator;
	}

	@Override
	protected void configure() {
		bind(DataMaster.class).toInstance(dataMaster);
		bind(RandomGenerator.class).toInstance(randomGenerator);

		install(new FactoryModuleBuilder().build(GeneratorFactory.class));
		install(new FactoryModuleBuilder().implement(PersonProvider.class, DefaultPersonProvider.class).build(PersonFactory.class));
		install(new FactoryModuleBuilder().implement(CompanyProvider.class, DefaultCompanyProvider.class).build(CompanyFactory.class));
		install(new FactoryModuleBuilder().implement(IBANProvider.class, DefaultIBANProvider.class).build(IBANFactory.class));
	}

}
