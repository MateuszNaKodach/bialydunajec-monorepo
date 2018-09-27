package org.bialydunajec.registrations.domain.cottage.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class CottageSpace(
        /**
         * Amount of all available spaces in the cottage, exclude any other limitations.
         * Only his value is necessary for run campers registration.
         */
        @NotNull
        val fullCapacity: Int = 0,

        /**
         * Amount of all spaces that are only available to be occupied after registration by administrator.
         * For example Cottage Boss can use it for ensure spaces for cottage cadre.
         */
        @NotNull
        val reservations: Int = 0,

        /**
         * Amount of all available spaces for women in the cottage.
         * maxFemaleTotal + maxMaleTotal =< fullCapacity
         */
        val maxFemaleTotal: Int? = null,

        /**
         * Amount of all available spaces for men in the cottage.
         * maxFemaleTotal + maxMaleTotal =< fullCapacity
         */
        val maxMaleTotal: Int? = null,

        /**
         * Amount of all available spaces for this year high school graduates in the cottage.
         * highSchoolRecentGraduatesCapacity =< fullCapacity
         */
        val highSchoolRecentGraduatesCapacity: Int? = null,

        /**
         * Amount of all available spaces for this year female high school graduates in the cottage.
         * highSchoolRecentGraduatesCapacity =< fullCapacity && maxFemaleHighSchoolRecentGraduates =< maxFemaleTotal && maxFemaleHighSchoolRecentGraduates + maxMaleHighSchoolRecentGraduates <= highSchoolRecentGraduatesCapacity
         */
        val maxFemaleHighSchoolRecentGraduates: Int? = null,

        val maxMaleHighSchoolRecentGraduates: Int? = null
) {

    fun getMaxBy(gender: Gender) = if (gender.isFemale) maxFemaleTotal else maxMaleTotal

    fun getMaxForHighSchoolRecentGraduateBy(gender: Gender) = if (gender.isFemale) maxFemaleHighSchoolRecentGraduates else maxMaleHighSchoolRecentGraduates

    fun getSpaceForCampers() = fullCapacity - reservations
}