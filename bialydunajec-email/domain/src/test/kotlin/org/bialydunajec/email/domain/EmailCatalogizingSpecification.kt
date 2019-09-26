package org.bialydunajec.email.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertTrue
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

object EmailCatalogizingSpecification : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses") {

        val EXISTING_EMAIL_ADDRESS_STRING = "existingEmail@gmail.com"
        val NEW_EMAIL_ADDRESS_STRING = "newEmail@gmail.com"

        val EXISTING_GROUP_NAME = "existingGroupName"
        val NEW_GROUP_NAME = "newGroupName"

        val OWNER_FIRST_NAME = "firstName"
        val OWNER_LAST_NAME = "lastName"


        val existingEmailAddressId: EmailAddressId by memoized {
            EmailAddressId.from(org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress(EXISTING_EMAIL_ADDRESS_STRING)) }
        val existingEmailGroupId: EmailGroupId by memoized { EmailGroupId() }

        val existingEmailAddress: EmailAddress by memoized { EmailAddress(existingEmailAddressId, EXISTING_EMAIL_ADDRESS_STRING) }
        val existingEmailGroup: EmailGroup by memoized { EmailGroup(existingEmailGroupId, EmailAddressGroup(EXISTING_GROUP_NAME)) }

        Scenario("Completely new email addresses to catalogize in new group") {

            var newEmailGroup = EmailGroup(EmailGroupId(), EmailAddressGroup(NEW_GROUP_NAME))
            var newEmailAddress = EmailAddress(
                    org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress(EXISTING_EMAIL_ADDRESS_STRING),
                    EmailAddressGroup(NEW_GROUP_NAME),
                    null)


            Given("created a new newEmailAddress and a new emailGroup objects") {
            }

            When("adding the new newEmailAddress to the emailGroup") {
                newEmailAddress.catalogizeTo(newEmailGroup,
                        EmailAddressOwner(FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME)))
            }

            Then("new EmailGroup should contain the new newEmailAddress") {
                assertTrue { newEmailAddress.belongsTo(newEmailGroup.getAggregateId()) }
            }
        }

        Scenario("Completely new email addresses to catalogize in existing group") {


            var newEmailAddress = EmailAddress(
                    org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress(EXISTING_EMAIL_ADDRESS_STRING),
                    EmailAddressGroup(NEW_GROUP_NAME),
                    null)

            Given("created a new newEmailAddress") {
            }

            When("adding the new newEmailAddress to the existing emailGroup") {
                newEmailAddress.catalogizeTo(existingEmailGroup,
                        EmailAddressOwner(FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME)))
            }

            Then("the existing EmailGroup should contain the new newEmailAddress") {
                assertTrue { newEmailAddress.belongsTo(existingEmailGroup.getAggregateId()) }
            }

        }

        Scenario("Existing email address catalogized to new group") {


            var newEmailGroup = EmailGroup(EmailGroupId(), EmailAddressGroup(NEW_GROUP_NAME))

            Given("created a new emailGroup") {
            }

            When("adding the new newEmailAddress to the existing emailGroup") {
                existingEmailAddress.catalogizeTo(
                        newEmailGroup,
                        EmailAddressOwner(FirstName(OWNER_FIRST_NAME), LastName(OWNER_LAST_NAME)))
            }

            Then("the new EmailGroup should contain the existing newEmailAddress") {
                assertTrue { existingEmailAddress.belongsTo(newEmailGroup.getAggregateId()) }
            }
        }

        Scenario("Update of existing email address") {
            /*
            val newEmailAddressField: String = "newEmaillAddress@gmail.com"

            Given("existing newEmailAddress is in existing emailGroup") {
                existingEmailAddress.catalogizeTo(existingEmailGroup.getAggregateId())
            }

            When("setting new newEmailAddress field in the existing newEmailAddress "){
                existingEmailAddress.updateAddress(newEmailAddressField)
            }

            lateinit var emailAddressInGroup: EmailAddress

            Then("Email address of existing newEmailAddress object in existing emailGroup should be updated"){
                emailAddressInGroup = existingEmailGroup.getEmailAddressById(existingEmailAddressId)
                assertEquals(newEmailAddressField, emailAddressInGroup.getNewEmailAddress().email)
            }
            */

        }

        Scenario("Update of not existing email address") {}
    }
})