package org.bialydunajec.email.infrastructure

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isSuccess
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.domain.*
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

/*
// TODO: Clean database after each test
// May help: https://brightinventions.pl/blog/clear-database-in-spring-boot-tests/
*/
@DataJpaTest(includeFilters = [ComponentScan.Filter(classes = [EmailJpaRepository::class])])
@ContextConfiguration(classes = [DataJpaTestConfiguration::class, EmailRepositorySpecification.TestConfig::class])
@DisplayName("EmailRepository (JPA implementation) - Emails catagolizing in order to simplify searching for email addresses")
internal class EmailRepositorySpecification {

    @Autowired
    lateinit var emailRepository: EmailRepository

    @Nested
    @DisplayName("Given no catagolized emails")
    inner class GivenNoCatagolizedEmails {

        @Nested
        @DisplayName("When catagolize new email to new group")
        inner class WhenCatagolizeNewEmail {

            private val emailGroup = EmailGroup(EmailGroupId("WhenCatagolizeNewEmail"))
            private val email = Email(
                emailGroup.getAggregateId(),
                EmailAddress("email@gmail.com"),
                EmailAddressOwner(FirstName("Jan"), LastName("Kowalski"))
            )

            @Test
            fun `then the email should be saved`() {
                assertThat {
                    emailRepository.save(email)
                }.isSuccess()
            }

        }


        @Nested
        @DisplayName("When catagolize the same email (but with different owner) to the same group twice")
        inner class WhenCatagolizeTheSameEmailToTheSameGroup {

            private val emailGroup = EmailGroup(EmailGroupId("WhenCatagolizeTheSameEmailToTheSameGroup"))
            private val email1 = Email(
                emailGroup.getAggregateId(),
                EmailAddress("email@gmail.com"),
                EmailAddressOwner(FirstName("Jan"), LastName("Kowalski"))
            )
            private val email2 = Email(
                emailGroup.getAggregateId(),
                EmailAddress("email@gmail.com"),
                EmailAddressOwner(FirstName("Grzegorz"), LastName("Brzęszczyszczykiewicz"))
            )

            @Test
            fun `then the email should be saved only once`() {
                assertThat {
                    emailRepository.save(email1)
                }.isSuccess()

                assertThat {
                    emailRepository.save(email2)
                }.isFailure().isEqualTo(DomainRuleViolationException.of(EmailDomainRule.EMAIL_ADDRESS_CAN_BE_CATAGOLIZED_ONLY_ONCE_AT_THE_SAME_GROUP))
            }

        }

        @Nested
        @DisplayName("When catagolize the same emai to the different groups")
        inner class WhenCatagolizeTheSameEmailToDifferentGroups {

            private val group1 = EmailGroup(EmailGroupId("GROUP_1"))
            private val group2 = EmailGroup(EmailGroupId("GROUP_2"))
            private val emailInGroup1 = Email(
                group1.getAggregateId(),
                EmailAddress("email@gmail.com"),
                EmailAddressOwner(FirstName("Jan"), LastName("Kowalski"))
            )
            private val emailInGroup2 = Email(
                group2.getAggregateId(),
                EmailAddress("email@gmail.com"),
                EmailAddressOwner(FirstName("Jan"), LastName("Kowalski"))
            )


            @Test
            fun `then the email should be saved to two groups`() {
                assertThat {
                    emailRepository.save(emailInGroup1)
                }.isSuccess()

                assertThat {
                    emailRepository.save(emailInGroup2)
                }.isSuccess()
            }

        }


    }


    @Configuration
    class TestConfig {

        @Autowired
        lateinit var emailJpaRepository: EmailJpaRepository

        @Bean
        fun emailRepository(): EmailRepository = EmailRepositoryImpl(emailJpaRepository, mock(DomainEventBus::class.java))

    }


}
