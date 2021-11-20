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
package org.isf.generator.producer;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;

public class RandomGenerator {

	private final RandomDataGenerator randomDataGenerator;

	public RandomGenerator() {
		this.randomDataGenerator = new RandomDataGenerator(new JDKRandomGenerator());
	}

	public RandomGenerator(int seed) {
		this.randomDataGenerator = new RandomDataGenerator(new JDKRandomGenerator(seed));
	}

	public boolean nextBoolean() {
		return randomDataGenerator.getRandomGenerator().nextBoolean();
	}

	public <T> List<T> shuffle(List<T> elements) {
		Collections.shuffle(elements, (Random) randomDataGenerator.getRandomGenerator());
		return elements;
	}

	public int nextInt(int min, int max) {
		if (min == max) {
			return min;
		}
		return randomDataGenerator.nextInt(min, max);
	}

	public long nextDouble(long min, long max) {
		return randomDataGenerator.nextLong(min, max);
	}

	public double nextDouble(double min, double max) {
		return randomDataGenerator.nextUniform(min, max);
	}

}
