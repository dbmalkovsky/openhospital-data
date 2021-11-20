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

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneOffset;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.annotations.VisibleForTesting;

@Singleton
public class DateProducer {

	@VisibleForTesting
	static final int SECONDS_BEFORE_TO_BE_IN_THE_PAST = 1;

	private final BaseProducer baseProducer;
	private final TimeProvider timeProvider;

	@Inject
	public DateProducer(BaseProducer baseProducer, TimeProvider timeProvider) {
		this.baseProducer = baseProducer;
		this.timeProvider = timeProvider;
	}

	public LocalDateTime randomDateInThePast(int maxYearsEarlier) {
		checkArgument(maxYearsEarlier >= 0, "%s has to be >= 0", maxYearsEarlier);
		LocalDateTime currentDate = timeProvider.getCurrentTime();
		LocalDateTime latestDateInThePast = currentDate.minusSeconds(SECONDS_BEFORE_TO_BE_IN_THE_PAST);
		LocalDateTime maxYearsEarlierDate = currentDate.minusYears(maxYearsEarlier);
		return randomDateBetweenTwoDates(maxYearsEarlierDate, latestDateInThePast);
	}

	public LocalDateTime randomDateBetweenYearAndNow(int fromYear) {
		int actualYear = timeProvider.getCurrentYear();
		return randomDateInThePast(actualYear - fromYear);
	}

	public LocalDate randomDateBetweenTwoDates(LocalDate from, LocalDate to) {
		long epochDay = baseProducer.randomBetween(from.toEpochDay(), to.toEpochDay());
		return LocalDate.ofEpochDay(epochDay);
	}

	public LocalDateTime randomDateBetweenTwoDates(LocalDateTime from, LocalDateTime to) {
		long between = baseProducer.randomBetween(from.toInstant(ZoneOffset.UTC).toEpochMilli(), to.toInstant(ZoneOffset.UTC).toEpochMilli());
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(between), ZoneOffset.UTC);
	}

	public LocalDateTime randomDateBetweenYears(int fromYear, int toYear) {
		checkArgument(fromYear <= toYear, "%s has to be <= %s", fromYear, toYear);
		LocalDateTime fromDate = getDateForFirstDayForGivenYear(fromYear);
		LocalDateTime toDate = getDateForLastDayForGivenYear(toYear);
		return randomDateBetweenTwoDates(fromDate, toDate);
	}

	private LocalDateTime getDateForLastDayForGivenYear(int year) {
		return LocalDateTime.of(year, Month.JANUARY, 1, 23, 59).with(lastDayOfYear());
	}

	private LocalDateTime getDateForFirstDayForGivenYear(int year) {
		return LocalDateTime.of(year, Month.JANUARY, 1, 0, 0).with(firstDayOfYear());
	}

	public LocalDateTime randomDateBetweenNowAndFuturePeriod(Period futurePeriod) {
		LocalDateTime now = timeProvider.getCurrentTime();
		return randomDateBetweenTwoDates(now, now.plus(futurePeriod));
	}

	public LocalDateTime randomDateInTheFuture(int years) {
		return randomDateBetweenNowAndFuturePeriod(Period.ofYears(years));
	}

	public LocalDateTime randomDateInTheFuture() {
		return randomDateInTheFuture(100);
	}

}
