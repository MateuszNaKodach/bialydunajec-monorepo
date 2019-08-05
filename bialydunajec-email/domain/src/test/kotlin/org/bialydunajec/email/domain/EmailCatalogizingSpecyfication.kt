package org.bialydunajec.email.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId

object EmailCatalogizingSpecyfication : Spek({

    Feature("Emails catalogizing in order to simplify searching for email addresses"){
        val emailAddressId: EmailAddressId by memoized { EmailAddressId() }
        val emailsGroupId: EmailGroupId by memoized { EmailGroupId() }

        Scenario("Completely new email addresses to catalogize in new group"){}

        Scenario("Completely new email addresses to catalogize in existing group"){}

        Scenario("Existing email address catalogized to new group"){}

        Scenario("Update of existing email address"){}

        Scenario("Update of not existing email address"){}
    }
})