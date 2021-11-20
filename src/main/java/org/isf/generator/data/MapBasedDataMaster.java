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
package org.isf.generator.data;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.EnumUtils;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.util.LanguageCode;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

public class MapBasedDataMaster implements DataMaster {

	public static final String LANGUAGE_TAG = "language";
	private final BaseProducer baseProducer;
	private Map<String, Object> dataSource = new CaseInsensitiveMap();

	@Inject
	public MapBasedDataMaster(BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
	}

	/**
	 * Returns list (null safe) of elements for desired key from dataSource files
	 *
	 * @param key desired node key
	 * @return list of elements for desired key
	 * @throws IllegalArgumentException if no element for key has been found
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getStringList(String key) {
		return getData(key, List.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getValuesOfType(String dataKey, final String type, final Class<T> resultClass) {
		Map<String, List<T>> data = getData(dataKey, Map.class);

		List<T> entries = data.get(type);

		return baseProducer.randomElement(entries);
	}

	/**
	 * Returns element (null safe) for desired key from dataSource files
	 *
	 * @param key desired node key
	 * @return string element for desired key
	 * @throws IllegalArgumentException if no element for key has been found
	 */
	@Override
	public String getString(String key) {
		return getData(key, String.class);
	}

	@Override
	public String getRandomValue(String key) {
		return baseProducer.randomElement(getStringList(key));
	}

	@Override
	public LanguageCode getLanguage() {
		return EnumUtils.getEnum(LanguageCode.class, getString(LANGUAGE_TAG).toUpperCase());
	}

	@SuppressWarnings({ "unchecked", "ConstantConditions" })
		// checked by checkArgument
	<T> T getData(String key, Class<T> type) {
		checkArgument(key != null, "key cannot be null");
		checkArgument(type != null, "type cannot be null");

		Object element = dataSource.get(key);
		checkArgument(element != null, "No such key: %s", key);
		checkArgument(type.isAssignableFrom(element.getClass()), "Element under desired key has incorrect type - should be %s", type.getSimpleName());

		return (T) element;
	}

	//fixme - should be package-private
	public void readResources(String path) throws IOException {
		Enumeration<URL> resources = getClass().getClassLoader().getResources(path);

		if (!resources.hasMoreElements()) {
			throw new IllegalArgumentException(String.format("File %s was not found on classpath", path));
		}

		final LoadSettings loadSettings = LoadSettings.builder().build();

		while (resources.hasMoreElements()) {
			final Load load = new Load(loadSettings);
			final URL url = resources.nextElement();
			try (InputStream is = url.openStream()) {
				Map<String, Object> data = (Map<String, Object>) load.loadFromInputStream(is);
				appendData(data);
			}
		}
	}

	private void appendData(Map<String, Object> data) {
		dataSource.putAll(data);
	}

	private static class CaseInsensitiveMap extends HashMap<String, Object> {

		@Override
		@SuppressWarnings("unchecked")
		public Object put(String key, Object value) {
			String loweredKey = key.toLowerCase();
			Object valueToInsert = value;

			if (value instanceof Map) {
				valueToInsert = new CaseInsensitiveMap();
				((CaseInsensitiveMap) valueToInsert).putAll((Map<? extends String, ?>) value);
			}

			return super.put(loweredKey, valueToInsert);
		}

		@Override
		public void putAll(Map<? extends String, ?> map) {
			for (Map.Entry<? extends String, ?> entry : map.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public Object get(Object key) {
			return super.get(((String) key).toLowerCase());
		}

	}

}
