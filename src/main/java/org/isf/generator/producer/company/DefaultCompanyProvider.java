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
package org.isf.generator.producer.company;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.VATIdentificationNumberProvider;
import org.isf.generator.producer.util.TextUtils;

import com.google.inject.assistedinject.Assisted;

public class DefaultCompanyProvider implements CompanyProvider {

	protected String name;
	protected String domain;
	protected String email;
	protected String vatIdentificationNumber;

	protected BaseProducer baseProducer;
	protected DataMaster dataMaster;

	protected VATIdentificationNumberProvider vatIdentificationNumberProvider;

	@Inject
	public DefaultCompanyProvider(BaseProducer baseProducer, DataMaster dataMaster, VATIdentificationNumberProvider vatIdentificationNumberProvider,
			@Assisted CompanyProperties.CompanyProperty... companyProperties) {
		this.baseProducer = baseProducer;
		this.dataMaster = dataMaster;
		this.vatIdentificationNumberProvider = vatIdentificationNumberProvider;

		for (CompanyProperties.CompanyProperty companyProperty : companyProperties) {
			companyProperty.apply(this);
		}
	}

	@Override
	public Company get() {

		generateName();
		generateDomain();
		generateEmail();
		generateVatIdentificationNumber();

		return new Company(name, domain, email, vatIdentificationNumber);
	}

	@Override
	public void generateName() {
		if (name != null) {
			return;
		}
		name = dataMaster.getRandomValue(COMPANY_NAME);
		if (baseProducer.trueOrFalse()) {
			name += " " + dataMaster.getRandomValue(COMPANY_SUFFIX);
		}
	}

	/**
	 * In case of the illegal hostname characters in company name
	 * and truncate it if it is too long (length &gt; 10) after escape
	 *
	 * It is compatible with other non-latin language and will not change the original result for latin language.
	 *
	 * P.S. Actually the best way for Chinese here is to use phonetic writing (so as Japanese or Korean)
	 */
	@Override
	public void generateDomain() {
		if (domain != null) {
			return;
		}

		String host = TextUtils.stripAccents(StringUtils.strip(StringUtils.deleteWhitespace(name.toLowerCase()), ".").replace("/", ""));
		int len1 = host.length();
		host = StringEscapeUtils.escapeJava(host).replaceAll("\\\\u", "");
		int len2 = host.length();
		if (len2 > len1 && len2 > 10) {
			host = host.substring(0, 10);
		}

		domain = host + "." + dataMaster.getRandomValue(DOMAIN);
	}

	@Override
	public void generateEmail() {
		if (email != null) {
			return;
		}
		email = dataMaster.getRandomValue(COMPANY_EMAIL);
	}

	@Override
	public void generateVatIdentificationNumber() {
		if (vatIdentificationNumber != null) {
			return;
		}
		vatIdentificationNumber = vatIdentificationNumberProvider.get();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setVatIdentificationNumber(String vatIdentificationNumber) {
		this.vatIdentificationNumber = vatIdentificationNumber;
	}

}
