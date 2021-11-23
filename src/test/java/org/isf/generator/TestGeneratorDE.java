package org.isf.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.isf.generator.producer.person.Person;
import org.junit.Test;

public class TestGeneratorDE {

	private final int SEED = 186;
	private Generator generator = Generator.builder().withRandomSeed(SEED).withLocale(Locale.GERMAN).build();

	@Test
	public void createGermanName() {
		Person person = generator.person();
		assertThat(person.getFullName()).isEqualTo("Horstfried Scheidt");
	}

	@Test
	public void createGermanCity() {
		Person person = generator.person();
		assertThat(person.getAddress().getCity()).isEqualTo("Schlitz");
	}

}
