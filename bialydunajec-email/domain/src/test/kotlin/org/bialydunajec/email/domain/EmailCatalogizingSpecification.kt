package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.bialydunajec.email.domain.valueobject.EmailGroupName
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertTrue

const val EXISTING_EMAIL_ADDRESS_STRING = "existingEmail@gmail.com"
const val NEW_EMAIL_ADDRESS_STRING = "newEmail@gmail.com"
const val EXISTING_GROUP_NAME = "existingGroupName"
const val NEW_GROUP_NAME = "newGroupName"
const val OWNER_FIRST_NAME = "firstName"
const val OWNER_LAST_NAME = "lastName"

object EmailCatalogizingSpecification : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses") {

        val existingEmailAddressId: EmailId by memoized {
            EmailId.from(org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress(EXISTING_EMAIL_ADDRESS_STRING)) }
        val existingEmailGroupId: EmailGroupId by memoized { EmailGroupId() }

        val existingEmailAddress: Email by memoized { Email(existingEmailAddressId, EXISTING_EMAIL_ADDRESS_STRING) }
        val existingEmailGroup: EmailGroup by memoized { EmailGroup(existingEmailGroupId, EmailGroupName(EXISTING_GROUP_NAME)) }

        Scenario("Completely new email addresses to catalogize in new group") {

            val newEmailGroup = EmailGroup(EmailGroupId(), EmailGroupName(NEW_GROUP_NAME))
            val newEmailAddress = Email(NEW_EMAIL_ADDRESS_STRING)


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

            val newEmailAddress = Email(NEW_EMAIL_ADDRESS_STRING)

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

            val newEmailGroup = EmailGroup(EmailGroupId(), EmailGroupName(NEW_GROUP_NAME))

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

    }
})
