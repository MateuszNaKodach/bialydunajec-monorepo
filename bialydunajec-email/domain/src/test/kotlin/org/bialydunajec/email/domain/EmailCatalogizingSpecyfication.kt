package org.bialydunajec.email.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object EmailCatalogizingSpecyfication : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses"){

        val EXISTING_EMAIL_ADDRESS_STRING = "existingEmail@gmail.com"
        val NEW_EMAIL_ADDRESS_STRING = "newEmail@gmail.com"

        val EXISTING_GROUP_NAME = "existingGroupName"
        val NEW_GROUP_NAME = "newGroupName"

        val existingEmailAddressId: EmailAddressId by memoized { EmailAddressId() }
        val existingEmailGroupId: EmailGroupId by memoized { EmailGroupId() }

        val existingEmailAddress: EmailAddress by memoized{EmailAddress(existingEmailAddressId, EXISTING_EMAIL_ADDRESS_STRING)}
        val existingEmailGroup: EmailGroup by memoized{EmailGroup(existingEmailGroupId, EXISTING_GROUP_NAME)}

        Scenario("Completely new email addresses to catalogize in new group"){
            var newEmailAddress = EmailAddress(EmailAddressId(), NEW_EMAIL_ADDRESS_STRING)
            var newEmailGroup =  EmailGroup(EmailGroupId(), NEW_GROUP_NAME)


            Given("created a new emailAddress and a new emailGroup objects") {
            }

            When("adding the new emailAddress to the emailGroup"){
                newEmailGroup.add(newEmailAddress)
            }

            Then("new EmailGroup should contain the new emailAddress"){
                assertTrue { newEmailGroup.contains(newEmailAddress) }
            }
        }

        Scenario("Completely new email addresses to catalogize in existing group"){

            var newEmailAddress = EmailAddress(EmailAddressId(), NEW_EMAIL_ADDRESS_STRING)

            Given("created a new emailAddress") {
            }

            When("adding the new emailAddress to the existing emailGroup"){
                existingEmailGroup.add(newEmailAddress)
            }

            Then("the existing EmailGroup should contain the new emailAddress"){
                assertTrue { existingEmailGroup.contains(newEmailAddress) }
            }

        }

        Scenario("Existing email address catalogized to new group"){

            var newEmailGroup = EmailGroup(EmailGroupId(), NEW_GROUP_NAME)

            Given("created a new emailGroup") {
            }

            When("adding the new emailAddress to the existing emailGroup"){
                newEmailGroup.add(existingEmailAddress)
            }

            Then("the new EmailGroup should contain the existing emailAddress"){
                assertTrue { newEmailGroup.contains(existingEmailAddress) }
            }

        }

        Scenario("Update of existing email address"){

            val newEmailAddressField: String = "newEmaillAddress@gmail.com"

            Given("existing emailAddress is in existing emailGroup") {
                existingEmailGroup.add(existingEmailAddress)
            }

            When("setting new emailAddress field in the existing emailAddress "){
                existingEmailAddress.updateAddress(newEmailAddressField)
            }

            lateinit var emailAddressInGroup: EmailAddress

            Then("Email address of existing emailAddress object in existing emailGroup should be updated"){
                emailAddressInGroup = existingEmailGroup.getEmailAddressById(existingEmailAddressId)
                assertEquals(newEmailAddressField, emailAddressInGroup.getEmailAddress().email)
            }

        }

        Scenario("Update of not existing email address"){}
    }
})