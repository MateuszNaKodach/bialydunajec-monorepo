package org.bialydunajec.email.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertTrue
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName

object EmailCatalogizingSpecification : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses") {

        val EXISTING_EMAIL_ADDRESS_STRING = "existingEmail@gmail.com"
        val NEW_EMAIL_ADDRESS_STRING = "newEmail@gmail.com"

        val EXISTING_GROUP_NAME = "existingGroupName"
        val NEW_GROUP_NAME = "newGroupName"

        val OWNER_FIRST_NAME = "firstName"
        val OWNER_LAST_NAME = "lastName"


        val existingEmailAddressId: EmailAddressId by memoized { EmailAddressId() }
        val existingEmailGroupId: EmailGroupId by memoized { EmailGroupId() }

        val existingEmailAddress: EmailAddress by memoized { EmailAddress(existingEmailAddressId, EXISTING_EMAIL_ADDRESS_STRING) }
        val existingEmailGroup: EmailGroup by memoized { EmailGroup(existingEmailGroupId, EXISTING_GROUP_NAME) }

        Scenario("Completely new email addresses to catalogize in new group") {
            var newEmailAddress = EmailAddress(EmailAddressId(), NEW_EMAIL_ADDRESS_STRING)
            var newEmailGroup = EmailGroup(EmailGroupId(), NEW_GROUP_NAME)


            Given("created a new emailAddress and a new emailGroup objects") {
            }

            When("adding the new emailAddress to the emailGroup") {
                newEmailAddress.addTo(newEmailGroup.getAggregateId(), FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME))
            }

            Then("new EmailGroup should contain the new emailAddress") {
                assertTrue { newEmailAddress.doesBelongsTo(newEmailGroup.getAggregateId()) }
            }
        }

        Scenario("Completely new email addresses to catalogize in existing group") {

            var newEmailAddress = EmailAddress(EmailAddressId(), NEW_EMAIL_ADDRESS_STRING)

            Given("created a new emailAddress") {
            }

            When("adding the new emailAddress to the existing emailGroup") {
                newEmailAddress.addTo(existingEmailGroup.getAggregateId(), FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME))
            }

            Then("the existing EmailGroup should contain the new emailAddress") {
                assertTrue { newEmailAddress.doesBelongsTo(existingEmailGroup.getAggregateId()) }
            }

        }

        Scenario("Existing email address catalogized to new group") {

            var newEmailGroup = EmailGroup(EmailGroupId(), NEW_GROUP_NAME)

            Given("created a new emailGroup") {
            }

            When("adding the new emailAddress to the existing emailGroup") {
                existingEmailAddress.addTo(newEmailGroup.getAggregateId(),  FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME))
            }

            Then("the new EmailGroup should contain the existing emailAddress") {
                assertTrue { existingEmailAddress.doesBelongsTo(newEmailGroup.getAggregateId()) }
            }

        }

        Scenario("Update of existing email address") {
            /*
            val newEmailAddressField: String = "newEmaillAddress@gmail.com"

            Given("existing emailAddress is in existing emailGroup") {
                existingEmailAddress.addTo(existingEmailGroup.getAggregateId())
            }

            When("setting new emailAddress field in the existing emailAddress "){
                existingEmailAddress.updateAddress(newEmailAddressField)
            }

            lateinit var emailAddressInGroup: EmailAddress

            Then("Email address of existing emailAddress object in existing emailGroup should be updated"){
                emailAddressInGroup = existingEmailGroup.getEmailAddressById(existingEmailAddressId)
                assertEquals(newEmailAddressField, emailAddressInGroup.getEmailAddress().email)
            }
            */

        }

        Scenario("Update of not existing email address") {}
    }
})