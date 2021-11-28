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
package org.isf.generator.producer.hospitalvisit;

import org.isf.disease.model.Disease;
import org.isf.generator.producer.person.Person;
import org.isf.patient.model.Patient;
import org.isf.ward.model.Ward;

public class HospitalVisitProperties {

	private HospitalVisitProperties() {
	}

	public abstract static class HospitalVisitProperty {

		public abstract void apply(HospitalVisitProvider company);

		public static HospitalVisitProperty withName(final Person person) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setPerson(person);
				}
			};
		}

		public static HospitalVisitProperty withPatient(final Patient patient) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setPatient(patient);
				}
			};
		}

		public static HospitalVisitProperty withPerson(final Person person) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setPerson(person);
				}
			};
		}

		public static HospitalVisitProperty withDisease(final Disease disease) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setDisease(disease);
				}
			};
		}

		public static HospitalVisitProperty withWard(final Ward ward) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setWard(ward);
				}
			};
		}

		public static HospitalVisitProperty withDischargePercentage(final int dischargePercentage) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.setDischargePercentage(dischargePercentage);
				}
			};
		}

		public static HospitalVisitProperty withDischarged(final boolean isDischarged) {
			return new HospitalVisitProperty() {

				@Override
				public void apply(HospitalVisitProvider hospitalVisitProvider) {
					hospitalVisitProvider.isDischarged(isDischarged);
				}
			};
		}

	}

}
