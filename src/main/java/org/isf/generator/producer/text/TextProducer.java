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

import static org.apache.commons.lang3.StringUtils.left;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.isf.generator.producer.BaseProducer;
import org.isf.generator.producer.util.TextUtils;

public class TextProducer {

	private static final int DEFAULT_WORD_COUNT = 3;

	private static final int DEFAULT_WORD_COUNT_IN_SENTENCE = 3;

	private static final int DEFAULT_SENTENCE_COUNT = 3;

	private static final int SENTENCE_COUNT_PRECISION_MIN = 1;

	private static final int SENTENCE_COUNT_PRECISION_MAX = 3;

	private final TextProducerInternal textProducerInternal;

	private final BaseProducer baseProducer;

	private int limit = 0;

	@Inject
	public TextProducer(TextProducerInternal textProducerInternal, BaseProducer baseProducer) {
		this.textProducerInternal = textProducerInternal;
		this.baseProducer = baseProducer;
	}

	public TextProducer limitedTo(int limit) {
		this.limit = limit;
		return this;
	}

	public String result(String result) {
		if (limit > 0) {
			return left(result, limit);
		}
		return result;
	}

	public String loremIpsum() {
		return result(textProducerInternal.loremIpsum());
	}

	public String text() {
		return result(textProducerInternal.text());
	}

	public String word() {
		return result(word(DEFAULT_WORD_COUNT));
	}

	public String word(int count) {
		return result(textProducerInternal.cleanWords(count));
	}

	public String latinWord() {
		return result(latinWord(DEFAULT_WORD_COUNT));
	}

	public String latinWord(int count) {
		return result(textProducerInternal.cleanLatinWords(count));
	}

	public String latinSentence() {
		return result(latinSentence(DEFAULT_SENTENCE_COUNT));
	}

	public String latinSentence(int wordCount) {
		return result(textProducerInternal.latinSentence(wordCount));
	}

	public String sentence() {
		return result(sentence(DEFAULT_WORD_COUNT_IN_SENTENCE));
	}

	public String sentence(int wordCount) {
		return result(textProducerInternal.sentence(wordCount));
	}

	private List<String> sentences(int sentenceCount) {
		List<String> sentences = new ArrayList<>(sentenceCount);
		for (int i = 0; i < sentenceCount; i++) {
			sentences.add(sentence());
		}
		return sentences;
	}

	public String paragraph() {
		return result(paragraph(DEFAULT_SENTENCE_COUNT));
	}

	public String paragraph(int sentenceCount) {
		return result(
				TextUtils.joinWithSpace(sentences(sentenceCount + baseProducer.randomBetween(SENTENCE_COUNT_PRECISION_MIN, SENTENCE_COUNT_PRECISION_MAX))));
	}

	/**
	 * Generates random string with desired length
	 *
	 * @param charsCount string length
	 * @return random string
	 */
	public String randomString(int charsCount) {
		return textProducerInternal.randomString(charsCount);
	}

}
