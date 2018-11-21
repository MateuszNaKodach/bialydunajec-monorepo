package org.bialydunajec.registrations.readmodel.camper

import com.fasterxml.jackson.annotation.JsonFormat
import org.bialydunajec.registrations.dto.CamperApplicationWithCottageDto
import org.bialydunajec.registrations.dto.ParticipationStatusDto
import org.bialydunajec.registrations.dto.StayDurationDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "campParticipantReadModels")
internal data class CampParticipant(
        @Id
        val campParticipantId: String,

        val campRegistrationsEditionId: String,

        var confirmedApplication: CamperApplicationWithCottageDto?,

        var currentCamperData: CamperApplicationWithCottageDto,

        var stayDuration: StayDurationDto? = null,
        //TODO: Zmienić w modelu domenownym, na null jeśli nie określono. Wpisywanie tego przy braku okreslenia przez obozowicza może rodzić problem w ustaleniu czy on podał takie informacje, czy zostało to wpisane przez system.

        var participationStatus: ParticipationStatusDto,

        @JsonFormat(timezone="Europe/Warsaw")
        var registrationDate: Instant?,

        @JsonFormat(timezone="Europe/Warsaw")
        var confirmationDate: Instant?,

        @JsonFormat(timezone="Europe/Warsaw")
        var downPaymentPaidDate: Instant?,

        @JsonFormat(timezone="Europe/Warsaw")
        var campBusSeatPaidDate: Instant?,

        @JsonFormat(timezone="Europe/Warsaw")
        var campParticipationPaidDate: Instant?
)