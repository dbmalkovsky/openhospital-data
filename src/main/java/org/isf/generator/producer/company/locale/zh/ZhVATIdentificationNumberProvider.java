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
package org.isf.generator.producer.company.locale.zh;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.RandomGenerator;
import org.isf.generator.producer.VATIdentificationNumberProvider;
import org.isf.generator.producer.util.ZhGeneratorUtil;

/**
 * org.isf.generator.producer.company.locale.zh.ZhVATIdentificationNumberProvider
 *
 * 6 digit for area location, 9 char (0-9A-Z) for organization code (2 char for business type, 2 char for industry type, 5 char for order number), total 15 digit
 */
public class ZhVATIdentificationNumberProvider implements VATIdentificationNumberProvider {

	private static BaseProducer baseProducer = new BaseProducer(new RandomGenerator());

	@Override
	public String get() {
		StringBuilder vatBuilder = new StringBuilder();
		vatBuilder.append(baseProducer.randomElement(ZhGeneratorUtil.PROV_LIST));
		vatBuilder.append(ZhGeneratorUtil.getRandomNumStr(baseProducer, ZhGeneratorUtil.CITY_MAX, 2));
		vatBuilder.append(ZhGeneratorUtil.getRandomNumStr(baseProducer, ZhGeneratorUtil.DISTRICT_MAX, 2));
		vatBuilder.append(getChars(9));
		return vatBuilder.toString();
	}

	private char getChar() {
		int rndNum = baseProducer.randomBetween(0, 35);
		if (rndNum < 10) {
			return (char) (49 + rndNum);
		}
		return (char) (65 + rndNum - 10);
	}

	private String getChars(int paddingSize) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < paddingSize; i++) {
			sb.append(getChar());
		}
		return sb.toString();
	}

}
