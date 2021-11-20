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
package org.isf.generator.producer.text;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.replaceChars;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import java.util.List;

import javax.inject.Inject;

import org.isf.generator.data.DataMaster;
import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.util.TextUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

class TextProducerInternal {

	private static final String LOREM_IPSUM = "loremIpsum";

	private static final String TEXT = "text";

	private static final String ALPHABET = "alphabet";

	private static final int WORD_COUNT_PRECISION_IN_SENTENCE = 6;

	private final BaseProducer baseProducer;

	private final String loremIpsum;

	private final String text;

	private final List<String> words;

	private final String alphabet;

	private final int maxAlphabetIndex;

	private final List<String> latinWords;

	@Inject
	public TextProducerInternal(DataMaster dataMaster, BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
		loremIpsum = dataMaster.getString(LOREM_IPSUM);
		text = dataMaster.getString(TEXT);
		words = asList(split(text, ' '));
		latinWords = asList(split(loremIpsum, ' '));
		alphabet = dataMaster.getString(ALPHABET);
		maxAlphabetIndex = alphabet.length() - 1;
	}

	public String loremIpsum() {
		return loremIpsum;
	}

	public String rawWords(List<String> words, int count, int precision) {
		List<String> result = readRawWords(words, count, precision);
		return TextUtils.joinWithSpace(result);
	}

	public String cleanLatinWords(int count) {
		return cleanWords(latinWords, count);
	}

	public String cleanWords(int count) {
		return cleanWords(words, count);
	}

	private String cleanWords(List<String> words, int count) {
		List<String> result = newArrayList();
		for (String part : readRawWords(words, count, 0)) {
			result.add(uncapitalize(replaceChars(part, "., ", "")));
		}
		return TextUtils.joinWithSpace(result);
	}

	public String randomString(int charsCount) {
		StringBuilder sb = new StringBuilder(charsCount);
		for (int i = 0; i < charsCount; i++) {
			sb.append(alphabet.charAt(baseProducer.randomInt(maxAlphabetIndex)));
		}
		return sb.toString();
	}

	private List<String> readRawWords(List<String> words, int count, int precision) {
		return baseProducer.randomElements(words, baseProducer.randomBetween(count, count + precision));
	}

	public String text() {
		return text;
	}

	public String sentence(int wordCount) {
		return sentence(words, wordCount);
	}

	public String latinSentence(int wordCount) {
		return sentence(latinWords, wordCount);
	}

	private String sentence(List<String> words, int wordCount) {
		String randomWords = rawWords(words, wordCount, WORD_COUNT_PRECISION_IN_SENTENCE);
		List<String> sentences = newArrayList();
		for (String sentence : Splitter.on(". ").split(randomWords)) {
			sentences.add(capitalize(sentence));
		}
		String sentence = capitalize(Joiner.on(". ").join(sentences));
		sentence = removeEnd(sentence, ",");
		if (!endsWith(sentence, ".")) {
			sentence += ".";
		}
		return sentence;
	}

}
