package org.isf.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.isf.generator.producer.person.Person;
import org.junit.Test;

public class TestGeneratorKA {

	private final int SEED = 2;
	private Locale geLocale = new Locale.Builder().setLanguage("ka").setRegion("ge").setScript("Geor").build();
	private Generator generator = Generator.builder().withRandomSeed(SEED).withLocale(geLocale).build();

	@Test
	public void createGeorgianName() {
		Person person = generator.person();
		assertThat(person.getFullName()).isEqualTo("ბაადურ აბრამიძე");  // Baadur Abramidze
	}

	@Test
	public void createGeorgianStreet() {
		Person person = generator.person();
		assertThat(person.getAddress().getStreet()).isEqualTo("აგლაძის ქუჩა");  // Agladze Street
	}

	@Test
	public void createGeorgianCity() {
		Person person = generator.person();
		assertThat(person.getAddress().getCity()).isEqualTo("ზესტაფონი");  // Sagarejo
	}

	@Test
	public void createGeorgianIDCard() {
		Person person = generator.person();
		assertThat(person.getNationalIdentityCardNumber()).isEqualTo("Nლ4757825");
	}

}
