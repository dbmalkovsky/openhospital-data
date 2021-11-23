package org.isf.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.isf.generator.producer.company.Company;
import org.isf.generator.producer.person.Person;
import org.junit.Test;

public class TestGeneratorZH {

	private final int SEED = 1;
	private Generator generator = Generator.builder().withRandomSeed(SEED).withLocale(Locale.CHINA).build();

	@Test
	public void createChineseName() {
		Person person = generator.person();
		assertThat(person.getFullName()).isEqualTo("国富 钱");
	}

	@Test
	public void createChineseIDCard() {
		Person person = generator.person();
		assertThat(person.getNationalIdentityCardNumber()).hasSize(18);
	}

	@Test
	public void createChineseAddress() {
		Person person = generator.person();
		assertThat(person.getAddress().getAddressLine1()).isEqualTo("杭州市内环路45号");
	}

	@Test
	public void createChineseCompanyName() {
		Company company = generator.company();
		assertThat(company.getName()).isEqualTo("后海金融");
	}

	@Test
	public void createChineseCompanyURL() {
		Company company = generator.company();
		assertThat(company.getUrl()).isEqualTo("http://www.540E6D7791.cn");
	}

	@Test
	public void createChineseCompanyVat() {
		Company company = generator.company();
		assertThat(company.getVatIdentificationNumber()).hasSize(15);
	}

}
