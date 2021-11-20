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

import java.io.IOException;
import java.util.Locale;

import org.isf.generator.data.DataMaster;
import org.isf.generator.data.DataMasterModule;
import org.isf.generator.data.MapBasedDataMaster;
import org.isf.generator.producer.RandomGenerator;
import org.isf.generator.producer.util.LanguageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * <p>Using a {@link #builder()}, you can configure the following fields:</p>
 * <ul>
 * <li><tt>locale</tt>: Specifies the locale for the random data file.</li>
 * <li><tt>filePrefix</tt>: Specifies the file prefix.
 * (So if you specify "seed" here and English for Locale, the data file will be
 * "seed_en.yml" under the classpath.)
 * </li>
 * <li><tt>random</tt>: The Random object to use.</li>
 * <li><tt>randomSeed</tt>: A specific random seed to use. Use this if you want the resulting
 * data to be <strong>deterministic</strong> based on it, such as if you want the same test
 * ID in a database to always result in the same fake name.
 * </li>
 * </ul>
 * Obviously, don't set both <tt>random</tt> and <tt>randomSeed</tt>, only the last one you set will
 * actually take effect.
 */
public class Bootstrap {

	private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

	private static final String DATA_FILE_PREFIX = "seed";

	private Bootstrap() {

	}

	public static Generator createGenerator(DataMaster dataMaster, Locale locale, RandomGenerator randomGenerator) {

		GeneratorModule generatorModule = getGeneratorModuleForLocale(dataMaster, locale, randomGenerator);

		Injector injector = Guice.createInjector(generatorModule);

		GeneratorFactory generatorFactory = injector.getInstance(GeneratorFactory.class);

		return generatorFactory.createGenerator();
	}

	private static void fillDefaultDataMaster(MapBasedDataMaster dataMaster, Locale locale, String filePrefix) {
		try {
			dataMaster.readResources(filePrefix + ".yml");
			dataMaster.readResources(filePrefix + "_" + locale.getLanguage() + ".yml");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Creates a Builder that will let you configure a Generator's fields one by one.
	 *
	 * @return a Builder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Use this factory method to create dataset containing default seed.yml and seed_{langCode}.yml files
	 * merged with custom files with the same name
	 *
	 * @return Generator instance
	 */
	public static Generator create() {
		return builder().build();
	}

	/**
	 * Use this factory method to create dataset containing default seed.yml and seed_{langCode}.yml files
	 * merged with custom files with the same name
	 *
	 * @param locale will be used to assess langCode for data file
	 * @return Generator instance
	 */
	public static Generator create(Locale locale) {
		return builder().withLocale(locale).build();
	}

	/**
	 * Use this factory method to create your own dataset overriding bundled one
	 *
	 * @param locale will be used to assess langCode for data file
	 * @param dataFilePrefix prefix of the data file - final pattern will be seed.yml and dataFilePrefix_{langCode}.yml
	 * @return Generator instance
	 */
	public static Generator create(Locale locale, String dataFilePrefix) {
		return builder().withLocale(locale).withFilePrefix(dataFilePrefix).build();
	}

	public static Generator create(Provider<DataMaster> dataMaster, Locale locale) {
		return builder().withDataMasterProvider(dataMaster).withLocale(locale).build();
	}

	/**
	 * Support customized language config
	 *
	 * @param dataMaster master of the config
	 * @param locale The Locale to set.
	 * @param randomGenerator specific random generator
	 * @return FariyModule instance in accordance with locale
	 */
	private static GeneratorModule getGeneratorModuleForLocale(DataMaster dataMaster, Locale locale, RandomGenerator randomGenerator) {

		LanguageCode code;
		try {
			code = LanguageCode.valueOf(locale.getLanguage().toUpperCase());
		} catch (IllegalArgumentException e) {
			LOG.warn("Unknown locale {}", locale);
			code = LanguageCode.EN;
		}

		switch (code) {
			case SW:
				return new SwGenatorModule(dataMaster, randomGenerator);
			case PL:
				return new PlGenatorModule(dataMaster, randomGenerator);
			case EN:
				return new EnGeneratorModule(dataMaster, randomGenerator);
			case ES:
				return new EsGeneratorModule(dataMaster, randomGenerator);
			case FR:
				return new FrGeneratorModule(dataMaster, randomGenerator);
			case SV:
				return new SvGenatorModule(dataMaster, randomGenerator);
			case ZH:
				return new ZhGneratorModule(dataMaster, randomGenerator);
			case DE:
				return new DeGeneatorModule(dataMaster, randomGenerator);
			case KA:
				return new KaGenatorModule(dataMaster, randomGenerator);
			default:
				LOG.info("No data for your language - using EN");
				return new EnGeneratorModule(dataMaster, randomGenerator);
		}
	}

	public static class Builder {

		private Locale locale = Locale.ENGLISH;
		private String filePrefix = DATA_FILE_PREFIX;
		private RandomGenerator randomGenerator = new RandomGenerator();
		private DataMaster dataMaster;

		private MapBasedDataMaster getDefaultDataMaster() {
			Injector injector = Guice.createInjector(new DataMasterModule(randomGenerator));
			return injector.getInstance(MapBasedDataMaster.class);
		}

		private Builder() {

		}

		/**
		 * Sets the locale for the resulting Generator.
		 *
		 * @param locale The Locale to set.
		 * @return the same Builder (for chaining).
		 */
		public Builder withLocale(Locale locale) {
			this.locale = locale;
			return this;
		}

		/**
		 * Sets the data file prefix for the resulting Generator.
		 *
		 * @param filePrefix The prefix of the file (such as "seed" for "seed_en.yml").
		 * @return the same Builder (for chaining).
		 */
		public Builder withFilePrefix(String filePrefix) {
			this.filePrefix = filePrefix;
			return this;
		}

		/**
		 * Sets the random seed to use to pick things randomly. (If you set this, you will always
		 * get the same result when you generate things.)
		 *
		 * @param randomSeed The random seed to use.
		 * @return the same Builder (for chaining).
		 */
		public Builder withRandomSeed(int randomSeed) {
			this.randomGenerator = new RandomGenerator(randomSeed);
			return this;
		}

		/**
		 * Sets a custom DataMaster implementation.
		 *
		 * @param dataMasterProvider The random seed to use.
		 * @return the same Builder (for chaining).
		 */
		public Builder withDataMasterProvider(Provider<DataMaster> dataMasterProvider) {
			this.dataMaster = dataMasterProvider.get();
			return this;
		}

		/**
		 * Returns the completed Generator.
		 *
		 * @return Generator instance
		 */
		public Generator build() {
			if (dataMaster == null) {
				dataMaster = getDefaultDataMaster();
				fillDefaultDataMaster((MapBasedDataMaster) dataMaster, locale, filePrefix);
			}
			return createGenerator(dataMaster, locale, randomGenerator);
		}

	}

}
