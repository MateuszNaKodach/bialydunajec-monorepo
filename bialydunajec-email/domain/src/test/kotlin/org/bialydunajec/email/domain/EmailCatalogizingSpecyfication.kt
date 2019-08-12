package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object EmailCatalogizingSpecyfication : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses"){
        val existingEmailAddressId: EmailAddressId by memoized { EmailAddressId() }
        val existingEmailGroupId: EmailGroupId by memoized { EmailGroupId() }

        val existingEmailAddress: EmailAddress by memoized{EmailAddress("existingEmail@gmail.com", existingEmailAddressId)}
        val existingEmailGroup: EmailGroup by memoized{EmailGroup("nameOfExistingGroup", existingEmailGroupId)}

        Scenario("Completely new email addresses to catalogize in new group"){

            var newEmailAddress: EmailAddress
            var newEmailGroup: EmailGroup


            Given("created a new emailAddress and a new emailGroup objects") {
                newEmailAddress = EmailAddress("address@gmail.com")
                newEmailGroup = EmailGroup("nameOfGroup")
            }

            When("adding the new emailAddress to the emailGroup"){
                newEmailGroup.add(newEmailAddress)
            }

            Then("new EmailGroup should contain the new emailAddress"){
                assertTrue { newEmailGroup.contains(newEmailAddress) }
            }
        }

        Scenario("Completely new email addresses to catalogize in existing group"){
            var newEmailAddress: EmailAddress

            Given("created a new emailAddress") {
                newEmailAddress = EmailAddress("address@gmail.com")
            }

            When("adding the new emailAddress to the existing emailGroup"){
                existingEmailGroup.add(newEmailAddress)
            }

            Then("the existing EmailGroup should contain the new emailAddress"){
                assertTrue { existingEmailGroup.contains(newEmailAddress) }
            }
        }

        Scenario("Existing email address catalogized to new group"){
            var newEmailGroup: EmailGroup

            Given("created a new emailGroup") {
                newEmailGroup = EmailGroup("nameOfNewEmailGroup")
            }

            When("adding the new emailAddress to the existing emailGroup"){
                newEmailGroup.add(existingEmailAddress)
            }

            Then("the new EmailGroup should contain the existing emailAddress"){
                assertTrue { newEmailGroup.contains(existingEmailGroup) }
            }
        }

        Scenario("Update of existing email address"){

            val newEmailAddressField: String = "newEmaillAddress@gmail.com"

            Given("existing emailAddress is in existing emailGroup") {
                existingEmailGroup.add(existingEmailAddress)
            }

            When("setting new emailAddress field in the existing emailAddress "){
                existingEmailAddress.email = newEmailAddressField
            }

            lateinit var emailAddressInGroup: EmailAddress

            Then("Email address of existing emailAddress object in existing emailGroup should be updated"){
                emailAddressInGroup = existingEmailGroup.getEmailAddresById(existingEmailAddressId)
                assertEquals(newEmailAddressField, emailAddressInGroup.email)
            }
        }

        Scenario("Update of not existing email address"){}
    }
})