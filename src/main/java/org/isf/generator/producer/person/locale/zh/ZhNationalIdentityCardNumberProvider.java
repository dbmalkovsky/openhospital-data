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
package org.isf.generator.producer.person.locale.zh;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.TimeProvider;
import org.isf.generator.producer.person.NationalIdentityCardNumberProvider;
import org.isf.generator.producer.util.ZhGeneratorUtil;

import com.google.inject.Inject;

/**
 * Chinese National Identity Card Number, total 18 digits
 */
public class ZhNationalIdentityCardNumberProvider implements NationalIdentityCardNumberProvider {

	/**
	 * The last 4 digit is an order number from 0001 to 9999
	 */
	private static final int ORDER_MAX = 9999;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;

	@Inject
	public ZhNationalIdentityCardNumberProvider(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
		this.dateProducer = new DateProducer(baseProducer, new TimeProvider());
	}

	@Override
	public String get() {
		StringBuilder idBuilder = new StringBuilder();
		idBuilder.append(baseProducer.randomElement(ZhGeneratorUtil.PROV_LIST));
		idBuilder.append(ZhGeneratorUtil.getRandomNumStr(baseProducer, ZhGeneratorUtil.CITY_MAX, 2));
		idBuilder.append(ZhGeneratorUtil.getRandomNumStr(baseProducer, ZhGeneratorUtil.DISTRICT_MAX, 2));
		idBuilder.append(getBirthDate());
		idBuilder.append(ZhGeneratorUtil.getRandomNumStr(baseProducer, ORDER_MAX, 4));
		return idBuilder.toString();
	}

	private String getBirthDate() {
		LocalDateTime birthDate = this.dateProducer.randomDateInThePast(50);
		return formatter.format(birthDate);
	}

}
