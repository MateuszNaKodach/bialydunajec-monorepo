package org.bialydunajec.academicministry.application

import assertk.Assert
import assertk.assertThat
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryAdminCommandGateway
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommand
import org.bialydunajec.academicministry.application.dto.AcademicMinistryDto
import org.bialydunajec.academicministry.application.dto.AcademicMinistryNameDto
import org.bialydunajec.academicministry.application.dto.toDto
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryAdminQueryGateway
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQueryGateway
import org.bialydunajec.academicministry.domain.AcademicMinistry
import org.bialydunajec.academicministry.domain.AcademicMinistryEvent
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.bialydunajec.academicministry.domain.entity.AcademicPriestId
import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.application.base.testing.*
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.event.DomainEventsRecorder
import org.bialydunajec.ddd.domain.base.persistence.InMemoryDomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.FacebookPage
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.SocialMedia
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.WebPage
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.*
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

val academicMinistryGivens = mapOf(
        "Redemptor" to AcademicMinistryGiven(
                officialName = "Duszpasterstwo Akademickie Redemptor",
                shortName = "Redemptor",
                logoImageUrl = Url.ExternalUrl("https://lh3.googleusercontent.com/UrJ3r2ps10B7nb6PCw7wQRu2LGwvObZGBmpgQ_stKEbPqlNsQs2cfcG1PbMLkiQK56N5E5t82FVvjraV=w219-h160-rw"),
                place = Place(
                        Address(Street("Wittiga"), HomeNumber("10"), CityName("Wrocław"), PostalCode("51-123"))
                ),
                socialMedia = SocialMedia(
                        WebPage("www.da.redemptor.pl"),
                        FacebookPage("https://www.facebook.com/da.redemptor/")
                ),
                emailAddress = EmailAddress("duszpasterz@da.redemptor.pl"),
                photoUrl = null,
                description = null
        ),
        "GoraSwAnny" to AcademicMinistryGiven(
                officialName = "Góra św. Anny",
                shortName = null,
                logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/VTnZ1lyholovO6vp0lYixUQJmP17pYKMf-ejd2GBxSqSLNVbMBkDGczDYs-emqWFApCHlg-j7oMoQw0v=w175-h128-rw"),
                place = Place(
                        Address(Street("Jana Pawła II"), HomeNumber("7"), CityName("Góra Świętej Anny"), PostalCode("47-154"))
                ),
                socialMedia = SocialMedia(
                        WebPage("http://www.swanna.org")
                ),
                emailAddress = EmailAddress("duszpasterstwo.swanna@gmail.com"),
                photoUrl = null,
                description = null
        ),
        "Maciejowka" to AcademicMinistryGiven(
                officialName = "Centralny Ośrodek Duszpasterstwa Akademickiego Maciejówka",
                shortName = null,
                logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/e1DLOGY5DO3Rg_EOPQ5GyfxvMh4GnIXwCOa4OLRBMnFm6um1oY4LCjIFPAg0rakRNWJcSLZAZYoajCdq=w175-h128-rw"),
                place = Place(
                        Address(Street("Nankiera"), HomeNumber("17a"), CityName("Wrocław"), PostalCode("50-140"))
                ),
                socialMedia = SocialMedia(
                        WebPage("http://www.maciejowka.org/")
                ),
                emailAddress = EmailAddress("ks.malinski@gmail.com"),
                photoUrl = null,
                description = null
        )
)
val academicPriestGivens = mapOf<String, (AcademicMinistryId) -> AcademicPriestGiven>(
        "MariuszSimonicz" to {
            AcademicPriestGiven(
                    academicMinistryId = it,
                    firstName = FirstName("Mariusz"),
                    lastName = LastName("Simonicz"),
                    personalTitle = PersonalTitle(name = "Ojciec", prefix = "o.", postfix = "CSSR"),
                    emailAddress = EmailAddress("duszpasterz@redemptor.pl"),
                    phoneNumber = PhoneNumber("123-123-123"),
                    description = null,
                    photoUrl = null
            )
        },
        "BartlomiejKot" to {
            AcademicPriestGiven(
                    academicMinistryId = it,
                    firstName = FirstName("Bartłomiej"),
                    lastName = LastName("Kot"),
                    personalTitle = PersonalTitle(name = "Ksiądx", prefix = "ks."),
                    emailAddress = EmailAddress("duszpasterz@maciejowka.pl"),
                    phoneNumber = PhoneNumber("321-321-321"),
                    description = null,
                    photoUrl = null
            )
        }
)

class AcademicPriestGiven(
        val academicPriestId: AcademicPriestId = AcademicPriestId(),
        val academicMinistryId: AcademicMinistryId,
        val firstName: FirstName,
        val lastName: LastName,
        val personalTitle: PersonalTitle?,
        val emailAddress: EmailAddress?,
        val phoneNumber: PhoneNumber?,
        val description: ExtendedDescription?,
        val photoUrl: Url?
) {

    val snapshot = AcademicPriestSnapshot(
            academicPriestId, firstName, lastName, personalTitle, emailAddress, phoneNumber, description, photoUrl
    )

    val create = AcademicMinistryCommand.CreateAcademicMinistryPriest(
            academicPriestId, academicMinistryId, firstName, lastName, personalTitle, emailAddress, phoneNumber, description, photoUrl
    )

    val unassign = AcademicMinistryCommand.RemoveAcademicMinistryPriest(
            academicMinistryId, academicPriestId
    )

    fun update(snapshot: AcademicPriestSnapshot) = AcademicMinistryCommand.UpdateAcademicMinistryPriest(
            academicMinistryId, snapshot
    )

    val assigned = AcademicMinistryEvent.AcademicMinistryPriestAssigned(
            academicMinistryId, snapshot
    )

    val unassigned = AcademicMinistryEvent.AcademicMinistryPriestUnassigned(
            academicMinistryId, snapshot
    )

    fun updated(snapshot: AcademicPriestSnapshot) = AcademicMinistryEvent.AcademicMinistryPriestUpdated(
            academicMinistryId, snapshot
    )

    val dto = snapshot.toDto()
}

class AcademicMinistryGiven(
        val academicMinistryId: AcademicMinistryId = AcademicMinistryId(),
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: Url?,
        val place: Place?,
        val socialMedia: SocialMedia,
        val emailAddress: EmailAddress?,
        val photoUrl: Url?,
        val description: ExtendedDescription?
) {

    private val snapshot = AcademicMinistrySnapshot(
            academicMinistryId = academicMinistryId,
            officialName = officialName,
            shortName = shortName,
            logoImageUrl = logoImageUrl,
            place = place,
            socialMedia = socialMedia,
            emailAddress = emailAddress,
            photoUrl = photoUrl,
            description = description
    )

    val create = AcademicMinistryCommand.CreateAcademicMinistry(
            academicMinistryId = academicMinistryId,
            officialName = officialName,
            shortName = shortName,
            logoImageUrl = logoImageUrl,
            place = place,
            socialMedia = socialMedia,
            emailAddress = emailAddress,
            photoUrl = photoUrl,
            description = description
    )

    val created = AcademicMinistryEvent.AcademicMinistryCreated(snapshot = snapshot)

    val dto = AcademicMinistryDto.from(snapshot)

    val nameDto = AcademicMinistryNameDto.from(snapshot)

    val id = academicMinistryId.toString()

}

fun administrateAcademicMinistries(block: (AcademicMinistryAdminTestFixtureScope.() -> Unit)? = null): AcademicMinistryAdminTestFixtureScope {
    val externalEvents = anExternalEventPublisher()
    val domainEvents = anDomainEventBus()
    val repository = InMemoryAcademicMinistryRepository(domainEvents)
    val configuration = AcademicMinistryConfiguration(repository, externalEvents)
    val commandGateway = configuration.academicMinistryAdminCommandGateway()
    val adminQueryGateway = configuration.academicMinistryAdminQueryGateway()
    val queryGateway = configuration.academicMinistryQueryGateway()
    val fixture = AcademicMinistryAdminTestFixtureScope(commandGateway, adminQueryGateway, domainEvents, queryGateway)
    block?.invoke(fixture)
    return fixture
}

class AcademicMinistryAdminTestFixtureScope(
        override val commandGateway: AcademicMinistryAdminCommandGateway,
        override val queryGateway: AcademicMinistryAdminQueryGateway,
        domainEvents: DomainEventsRecorder,
        val nonAdminQueryGateway: AcademicMinistryQueryGateway,
) : WhenCommandExecute<AcademicMinistryCommand, AcademicMinistryAdminCommandGateway, AcademicMinistryTestFixtureExpect>,
        ThenEventPublished<AcademicMinistryAdminTestFixtureScope>(domainEvents), ThenQueryResult<AcademicMinistryQuery, AcademicMinistryAdminQueryGateway> {

    override val brokenRules = mutableListOf<DomainRuleViolationException>()

    operator fun invoke(block: (AcademicMinistryAdminTestFixtureScope.() -> Unit)): AcademicMinistryAdminTestFixtureScope = apply { block(this) }

    override fun fixtureScope(): AcademicMinistryAdminTestFixtureScope = this

    override fun fixtureExpect() = AcademicMinistryTestFixtureExpect(queryGateway, domainEvents)

    infix fun givenAcademicMinistryExists(given: AcademicMinistryGiven): AcademicMinistryAdminTestFixtureScope = apply {
        domainEvents.withoutRecording {
            commandGateway.process(given.create)
        }
    }

    fun givenAcademicPriestExists(priest: AcademicPriestGiven): AcademicMinistryAdminTestFixtureScope = apply {
        domainEvents.withoutRecording {
            commandGateway.process(priest.create)
        }
    }

    fun <R> thenResultOf(query: AcademicMinistryQuery, byAdmin:Boolean = true): Assert<R> {
        val queryResult = if (byAdmin) queryGateway.process(query) else nonAdminQueryGateway.process(query)
        @Suppress("UNCHECKED_CAST")
        return assertThat(queryResult as R)
    }


}

class AcademicMinistryTestFixtureExpect(
        queryGateway: AcademicMinistryAdminQueryGateway,
        domainEvents: DomainEventsRecorder,
) : TestFixtureExpect<AcademicMinistryQuery, AcademicMinistryAdminQueryGateway>(queryGateway, domainEvents)

class InMemoryAcademicMinistryRepository(domainEventBus: DomainEventBus)
    : InMemoryDomainRepository<AcademicMinistryId, AcademicMinistry>(domainEventBus = domainEventBus), AcademicMinistryRepository
