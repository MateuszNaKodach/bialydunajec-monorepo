package org.bialydunajec.academicministry.application

import assertk.assertions.containsOnly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.bialydunajec.academicministry.application.dto.AcademicMinistryNameDto
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.ddd.application.base.testing.*
import org.junit.jupiter.api.Test

//TODO: W jaki sposób obsługiwać ExternalEvent? Wywolywac serwis aplikacyjny? Tlumaczyc na komende?
private class AcademicMinistrySpecification {

    @Test
    fun `When create new academic ministry | Then academic ministry should be created`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")

        administrateAcademicMinistries {
            whenExecute(redemptor.create) thenExpect (redemptor.created)

            thenResultOf { process(AcademicMinistryQuery.All) }.containsOnly(redemptor.dto)
            thenResultOf { process(AcademicMinistryQuery.ById(redemptor.id)) }.isEqualTo(redemptor.dto)
            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.isEmpty()
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists | When create new academic ministry Maciejowka | Then academic ministry should be created`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val maciejowka = academicMinistryGivens.getValue("Maciejowka")

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)

            whenExecute(maciejowka.create) thenExpect (maciejowka.created)

            thenResultOf { process(AcademicMinistryQuery.All) }.containsOnly(maciejowka.dto, redemptor.dto)
            thenResultOf { process(AcademicMinistryQuery.ById(maciejowka.id)) }.isEqualTo(maciejowka.dto)
            thenResultOf { process(AcademicMinistryQuery.ById(redemptor.id)) }.isEqualTo(redemptor.dto)
            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(maciejowka.id)) }.isEmpty()
            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.isEmpty()

            thenResultOf<Collection<AcademicMinistryNameDto>>(AcademicMinistryQuery.NamesForAll, byAdmin = false)
                    .containsOnly(redemptor.nameDto, maciejowka.nameDto)
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists | When assign priest to Redemptor | Then academic ministry should have one priest`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val mariuszSimonicz = academicPriestGivens.getValue("MariuszSimonicz")(redemptor.academicMinistryId)

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)

            whenExecute(mariuszSimonicz.create) thenExpect (mariuszSimonicz.assigned)

            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.containsOnly(mariuszSimonicz.dto)
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists and has assigned priest | When assign next priest to Redemptor | Then academic ministry should have two priests`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val mariuszSimonicz = academicPriestGivens.getValue("MariuszSimonicz")(redemptor.academicMinistryId)
        val bartlomiejKot = academicPriestGivens.getValue("BartlomiejKot")(redemptor.academicMinistryId)

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)
            givenAcademicPriestExists(mariuszSimonicz)

            whenExecute(bartlomiejKot.create) thenExpect (bartlomiejKot.assigned)

            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.containsOnly(mariuszSimonicz.dto, bartlomiejKot.dto)
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists and has one assigned priest | When unassign priest from Redemptor | Then academic ministry should have no priests`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val mariuszSimonicz = academicPriestGivens.getValue("MariuszSimonicz")(redemptor.academicMinistryId)

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)
            givenAcademicPriestExists(mariuszSimonicz)

            whenExecute(mariuszSimonicz.unassign) thenExpect (mariuszSimonicz.unassigned)

            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.isEmpty()
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists and has no assigned priest | When unassign priest from Redemptor | Then nothing should happen`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val mariuszSimonicz = academicPriestGivens.getValue("MariuszSimonicz")(redemptor.academicMinistryId)

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)

            whenExecute(mariuszSimonicz.unassign) thenExpect noEvents

            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.isEmpty()
        }
    }

    @Test
    fun `Given academic ministry Redemptor exists and has assigned two priest | When unassign one priest from Redemptor | Then academic ministry should have one priests`() {
        val redemptor = academicMinistryGivens.getValue("Redemptor")
        val mariuszSimonicz = academicPriestGivens.getValue("MariuszSimonicz")(redemptor.academicMinistryId)
        val bartlomiejKot = academicPriestGivens.getValue("BartlomiejKot")(redemptor.academicMinistryId)

        administrateAcademicMinistries {
            givenAcademicMinistryExists(redemptor)
            givenAcademicPriestExists(mariuszSimonicz)
            givenAcademicPriestExists(bartlomiejKot)

            whenExecute(bartlomiejKot.unassign) thenExpect (bartlomiejKot.unassigned)

            thenResultOf { process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(redemptor.id)) }.containsOnly(mariuszSimonicz.dto)
        }
    }

}


