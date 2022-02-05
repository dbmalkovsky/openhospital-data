package org.isf.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.person.Person;
import org.isf.generator.producer.util.LanguageCode;
import org.junit.Test;

import com.google.inject.Provider;

public class TestGenerator {

	private static final String CUSTOM_STRING = "Custom Data Master";

	@Test
	public void secondPersonDifferentWithoutGeneratorInstance() {
		Person person = Generator.create().person();
		Person person2 = Generator.create().person();
		assertThat(person.getFullName()).isNotEqualTo(person2.getFullName());
	}

	@Test
	public void secondPersonDifferentWithOneGeneratorInstance() {
		Generator generator = Generator.create();
		Person person = generator.person();
		Person person2 = generator.person();
		assertThat(person.getFullName()).isNotEqualTo(person2.getFullName());
	}

	@Test
	public void secondPersonSamewWthSameRandomSeed() {
		Generator generator1 = Generator.builder().withRandomSeed(10).build();
		Generator generator2 = Generator.builder().withRandomSeed(10).build();
		Person person1 = generator1.person();
		Person person2 = generator2.person();
		Person person3 = generator1.person();
		Person person4 = generator2.person();
		assertThat(person1.getFullName()).isEqualTo(person2.getFullName());
		assertThat(person3.getFullName()).isEqualTo(person4.getFullName());
		assertThat(person1.getFullName()).isNotEqualTo(person3.getFullName());
	}

	@Test
	public void secondPersonSameDifferentWithDifferentRandomSeed() {
		Generator generator1 = Generator.builder().withRandomSeed(10).build();
		Generator generator2 = Generator.builder().withRandomSeed(20).build();
		Person person1 = generator1.person();
		Person person2 = generator2.person();
		assertThat(person1.getFullName()).isNotEqualTo(person2.getFullName());
	}

	@Test
	public void useCustomDataMasterWhenProvided() {
		Generator generator = Generator.create(new CustomDataMasterProvider(), Locale.ENGLISH);
		Person person = generator.person();
		assertThat(person.getFirstName()).isEqualTo(CUSTOM_STRING);
	}

	class CustomDataMaster implements DataMaster {

		@Override
		public String getString(String key) {
			return CUSTOM_STRING;
		}

		@Override
		public List<String> getStringList(String key) {
			return Collections.singletonList(CUSTOM_STRING);
		}

		@Override
		public <T> T getValuesOfType(String dataKey, String type, Class<T> resultClass) {
			return (T) CUSTOM_STRING;
		}

		@Override
		public String getRandomValue(String key) {
			return CUSTOM_STRING;
		}

		@Override
		public LanguageCode getLanguage() {
			return null;
		}

	}

	public class CustomDataMasterProvider implements Provider<DataMaster> {

		@Override
		public DataMaster get() {
			return new CustomDataMaster();
		}

	}

}
