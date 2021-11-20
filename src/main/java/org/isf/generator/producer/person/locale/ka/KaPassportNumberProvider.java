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
package org.isf.generator.producer.person.locale.ka;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.person.PassportNumberProvider;

public class KaPassportNumberProvider implements PassportNumberProvider {

	private final BaseProducer baseProducer;

	@Inject
	public KaPassportNumberProvider(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
	}

	// District codes are not reachable at the moment.
	private String getDistrictCode() {
		return baseProducer.numerify("##");
	}

	// A system of Ministry of Inferior Office codes is not known at the moment.
	private String getStateOfficeCode(String district) {
		return baseProducer.numerify("###");
	}

	private String getNationalPersonalIdentificationNumber() {
		return baseProducer.numerify("######");
	}

	@Override
	public String get() {
		String districtCode = getDistrictCode();
		return districtCode + getStateOfficeCode(districtCode) + getNationalPersonalIdentificationNumber();
	}

}
