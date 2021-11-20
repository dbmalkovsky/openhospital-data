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
package org.isf.generator.producer.net;

import javax.inject.Inject;

public class NetworkProducer {

	private final IPNumberProducer ipNumberProducer;

	@Inject
	public NetworkProducer(IPNumberProducer ipNumberProducer) {
		this.ipNumberProducer = ipNumberProducer;
	}

	public String ipAddress() {
		return ipNumberProducer.generate();
	}

	/**
	 * Add a simple url generator
	 * Example: networkProducer.url(baseProducer.trueOrFalse())
	 *
	 * @param isHttps is https or not
	 * @return A faked url.
	 */
	public String url(boolean isHttps) {
		String mergedIP = ipAddress().replaceAll("\\.", "");
		char[] domainChars = mergedIP.toCharArray();
		for (int i = 0; i < domainChars.length; i++) {
			char c = domainChars[i];
			domainChars[i] = (char) (c + 97);
		}

		String domain = String.valueOf(domainChars);
		if (isHttps) {
			return "https://" + domain + ".com";
		}
		return "http://" + domain + ".com";
	}

}
