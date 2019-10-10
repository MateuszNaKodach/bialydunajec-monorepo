package org.bialydunajec.email.messages.command

import assertk.assertThat
import assertk.assertions.containsExactly
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertTrue

internal object EmailExternalCommandSpecification : Spek({

    describe("Feature: Creating many CatalogizeEmail commands for same email but different groups") {

        context("Given CatalogizeEmail command") {

            val command = EmailExternalCommand.CatalogizeEmail(
                "sampleemail@email.com",
                "FirstName",
                "LastName",
                "GROUP_1"
            )

            describe("When create command for also group GROUP_2") {

                val result = command.alsoWithGroup("GROUP_2")

                it("Then result should contains two commands with equals emailAddress, emailOwnerName, emailOwnerLastName") {
                    assertTrue {
                        result.all {
                            it.emailAddress == command.emailAddress
                            it.emailOwnerName == command.emailOwnerName
                            it.emailOwnerLastName == command.emailOwnerLastName
                        }
                    }
                }

                it("Then result should contains one command with GROUP_1 and one command with GROUP_2") {
                    assertThat(result.map { it.emailGroupId }).containsExactly("GROUP_1", "GROUP_2")
                }

            }

        }

    }

})
