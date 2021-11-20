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

import static com.google.common.base.Joiner.on;

import java.util.List;

public final class TextUtils {

	private TextUtils() {
	}

	public static String joinWithSpace(List<String> result) {
		return on(" ").join(result);
	}

	public static String stripAccents(String s) {
		// Replace polish character ł since bug https://issues.apache.org/jira/browse/LANG-1120
		return org.apache.commons.lang3.StringUtils.stripAccents(s).replaceAll("ł", "l").replaceAll("Ł", "L");
	}

	public static String stripSharpS(String s) {
		return s.replace("\u00DF", "ss");
	}

}
