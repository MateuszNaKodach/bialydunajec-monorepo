package org.bialydunajec.email.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertTrue


internal object EmailGroupHierarchySpecification : Spek({

    Feature("Email group hierarchy") {

        Scenario("Email group with parent") {

            lateinit var parent: EmailGroup
            lateinit var child: EmailGroup
            var parentIsParentOfChild = false
            var childIsNotParentOfChild = false

            Given("Email group with id CAMP-PARTICIPANT") {
                parent = EmailGroup(EmailGroupId("CAMP-PARTICIPANT"))
            }

            And("Email group with id CAMP-PARTICIPANT+EDITION_35") {
                child = EmailGroup(EmailGroupId("CAMP-PARTICIPANT+EDITION_35"))
            }

            When("Check hierarchy of the groups") {
                parentIsParentOfChild = parent.isParentOf(child)
                childIsNotParentOfChild = !child.isParentOf(parent)
            }

            Then("CAMP-PARTICIPANT should be parent of CAMP-PARTICIPANT+EDITION_35") {
                assertTrue { parentIsParentOfChild }
            }

            And("CAMP-PARTICIPANT+EDITION_35 should not be parent of CAMP-PARTICIPANT+EDITION_35") {
                assertTrue { childIsNotParentOfChild }
            }

        }

    }


})
