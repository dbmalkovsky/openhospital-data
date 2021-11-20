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
package org.isf.generator.producer.util;

import org.isf.generator.producer.BaseProducer;

public class ZhGeneratorUtil {

	private ZhGeneratorUtil() {
	}

	/**
	 * Codes of China provinces
	 */
	public static final String[] PROV_LIST = { "11",    // Beijing
			"12",    // Tianjin
			"13",    // Hebei
			"14",    // Shanxi
			"15",    // Neimenggu
			"21",    // Liaoning
			"22",    // Jilin
			"23",    // Heilongjiang
			"31",    // Shanghai
			"32",    // Jiangsu
			"33",    // Zhejiang
			"34",    // Anhui
			"35",    // Fujian
			"36",    // Jiangxi
			"41",    // Henan
			"42",    // Hubei
			"43",    // Hunan
			"44",    // Guangdong
			"45",    // Guangxi
			"46",    // Hainan
			"50",    // Chongqing
			"51",    // Sichuan
			"52",    // Guizhou
			"53",    // Yunnan
			"54",    // Xizang
			"61",    // Shaanxi
			"62",    // Gansu
			"63",    // Qinghai
			"64",    // Ningxia
			"65",    // Xinjiang
			"71",    // Taiwan
			"81",    // Hong Kong
			"82",    // Macau
	};

	/**
	 * Max code of city
	 */
	public static final int CITY_MAX = 30;
	/**
	 * Max code of city district
	 */
	public static final int DISTRICT_MAX = 12;

	/**
	 * Get random number from 1 to max in 0 leading string format.
	 *
	 * @param baseProducer BaseProducer
	 * @param max upper bound of number
	 * @param paddingSize padding size
	 * @return A number smaller than #max with #paddingSize digits in string format
	 */
	public static String getRandomNumStr(BaseProducer baseProducer, int max, int paddingSize) {
		int rndNum = baseProducer.randomBetween(1, max);
		String numStr = "" + rndNum;
		while (numStr.length() < paddingSize) {
			numStr = "0" + numStr;
		}
		return numStr;
	}

}
