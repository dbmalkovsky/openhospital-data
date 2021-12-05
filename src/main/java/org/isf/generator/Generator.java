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

import java.util.Locale;

import javax.inject.Inject;

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.DateProducer;
import org.isf.generator.producer.company.Company;
import org.isf.generator.producer.company.CompanyFactory;
import org.isf.generator.producer.company.CompanyProperties;
import org.isf.generator.producer.hospitalvisit.HospitalVisit;
import org.isf.generator.producer.hospitalvisit.HospitalVisitFactory;
import org.isf.generator.producer.hospitalvisit.HospitalVisitProperties;
import org.isf.generator.producer.net.NetworkProducer;
import org.isf.generator.producer.payment.CreditCard;
import org.isf.generator.producer.payment.CreditCardProvider;
import org.isf.generator.producer.payment.IBAN;
import org.isf.generator.producer.payment.IBANFactory;
import org.isf.generator.producer.payment.IBANProperties;
import org.isf.generator.producer.person.Person;
import org.isf.generator.producer.person.PersonFactory;
import org.isf.generator.producer.person.PersonProperties;
import org.isf.generator.producer.text.TextProducer;

import com.google.inject.Provider;

public final class Generator {

	private final TextProducer textProducer;
	private final PersonFactory personFactory;
	private final NetworkProducer networkProducer;
	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;
	private final CreditCardProvider creditCardProvider;
	private final CompanyFactory companyFactory;
	private final IBANFactory ibanFactory;
	private final HospitalVisitFactory hospitalVisitFactory;

	@Inject
	Generator(TextProducer textProducer, PersonFactory personFactory, NetworkProducer networkProducer, BaseProducer baseProducer, DateProducer dateProducer,
			CreditCardProvider creditCardProvider, CompanyFactory companyFactory, IBANFactory ibanFactory, HospitalVisitFactory hospitalVisitFactory) {
		this.textProducer = textProducer;
		this.personFactory = personFactory;
		this.networkProducer = networkProducer;
		this.baseProducer = baseProducer;
		this.dateProducer = dateProducer;
		this.creditCardProvider = creditCardProvider;
		this.companyFactory = companyFactory;
		this.ibanFactory = ibanFactory;
		this.hospitalVisitFactory = hospitalVisitFactory;
	}

	public static Generator create() {
		return Bootstrap.create();
	}

	public static Generator create(Locale locale) {
		return Bootstrap.create(locale);
	}

	public static Generator create(Provider<DataMaster> dataMasterProvider, Locale locale) {
		return Bootstrap.create(dataMasterProvider, locale);
	}

	public static Bootstrap.Builder builder() {
		return Bootstrap.builder();
	}

	/**
	 * Use this method for generating texts
	 *
	 * @return A {@link org.isf.generator.producer.text.TextProducer} instance
	 */
	public TextProducer textProducer() {
		return textProducer;
	}

	/**
	 * Use this method for fake persons
	 *
	 * @param personProperties desired person features
	 * @return A {@link org.isf.generator.producer.person.Person} instance
	 */
	public Person person(PersonProperties.PersonProperty... personProperties) {
		return personFactory.producePersonProvider(personProperties).get();
	}

	/**
	 * Use this method to generate fake company
	 *
	 * @param companyProperties desired company features
	 * @return A fake {@link org.isf.generator.producer.company.Company} instance
	 */
	public Company company(CompanyProperties.CompanyProperty... companyProperties) {
		return companyFactory.produceCompany(companyProperties).get();
	}

	/**
	 * Use this method to generate fake HospitalVisit/Admission
	 *
	 * @param hosptialVisitProperties desired hospitalVisit/admission features
	 * @return A fake {@link HospitalVisit} instance
	 */
	public HospitalVisit hospitalVisit(HospitalVisitProperties.HospitalVisitProperty... hosptialVisitProperties) {
		return hospitalVisitFactory.produceHospitalVisit(hosptialVisitProperties).get();
	}

	/**
	 * Use this method for get standard tools
	 *
	 * @return A {@link org.isf.generator.producer.BaseProducer} instance
	 */
	public BaseProducer baseProducer() {
		return baseProducer;
	}

	public DateProducer dateProducer() {
		return dateProducer;
	}

	/**
	 * Use this method for generating IBAN (International Bank Account Number)
	 *
	 * @return A {@link org.isf.generator.producer.payment.IBAN} instance
	 */
	public IBAN iban() {
		return ibanFactory.produceIBANProvider().get();
	}

	/**
	 * Use this method for generating IBAN (International Bank Account Number)
	 *
	 * @param properties desired IBAN features
	 * @return A {@link org.isf.generator.producer.payment.IBAN} instance
	 */
	public IBAN iban(IBANProperties.Property... properties) {
		return ibanFactory.produceIBANProvider(properties).get();
	}

	public CreditCard creditCard() {
		return creditCardProvider.get();
	}

	public NetworkProducer networkProducer() {
		return networkProducer;
	}

}
