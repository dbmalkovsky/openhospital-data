package org.isf.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.isf.generator.producer.person.Person;
import org.junit.Test;

public class TestGeneratorFR {

	private final int SEED = 17;
	private Generator generator = Generator.builder().withRandomSeed(SEED).withLocale(Locale.FRENCH).build();

	@Test
	public void createFrenchName() {
		Person person = generator.person();
		assertThat(person.getFullName()).isEqualTo("Axel Gicquel");
	}

	@Test
	public void createFrenchCity() {
		Person person = generator.person();
		assertThat(person.getAddress().getCity()).isEqualTo("Chambéry");
	}

}
