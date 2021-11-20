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
import static org.isf.generator.producer.person.PersonProvider.PERSONAL_EMAIL;

import org.apache.commons.lang3.StringUtils;
import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.util.TextUtils;

import com.google.inject.Provider;

public class EmailProvider implements Provider<String> {

	private final DataMaster dataMaster;
	private final BaseProducer baseProducer;
	private final String firstName;
	private final String lastName;

	public EmailProvider(DataMaster dataMaster, BaseProducer baseProducer, String firstName, String lastName) {
		this.dataMaster = dataMaster;
		this.baseProducer = baseProducer;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String get() {
		String prefix = "";
		switch (baseProducer.randomBetween(1, 3)) {
			case 1:
				prefix = StringUtils.replace(firstName + lastName, " ", "");
				break;
			case 2:
				prefix = StringUtils.replace(firstName + "." + lastName, " ", ".");
				break;
			case 3:
				prefix = StringUtils.replace(lastName, " ", "");
				break;
		}
		String email = lowerCase(prefix + '@' + dataMaster.getRandomValue(PERSONAL_EMAIL));
		return TextUtils.stripSharpS(TextUtils.stripAccents(email));
	}

}
