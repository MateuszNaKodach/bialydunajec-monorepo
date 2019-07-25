package org.bialydunajec.campschedule

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.MongoClient
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.extensions.mongo.DefaultMongoTemplate
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.extensions.mongo.eventsourcing.eventstore.documentperevent.EventEntry
import org.axonframework.serialization.json.JacksonSerializer
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.CampEditionScheduleEvent
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.campschedule.domain.valueobject.DayActivityDetails
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.atomic.AtomicBoolean

@SpringBootApplication
class CampScheduleApplication

fun main(args: Array<String>) {
    runApplication<CampScheduleApplication>(*args)
}

private const val CAMP_EDITION_DURATION = 13L

@Configuration
class Configuration {

    class Wrapper(val jackson: JacksonSerializer)
    @Bean
    fun jackson(objectMapper: ObjectMapper): Wrapper = Wrapper(JacksonSerializer.builder().objectMapper(objectMapper).build())

    @Bean
    fun storageEngine(client: MongoClient, wrapper: Wrapper): EventStorageEngine {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build())
                //.eventSerializer(wrapper.jackson)
                .build()
    }

    // @Bean
    // fun mySnapshotTriggerDefinition(snapshotter: Snapshotter): SnapshotTriggerDefinition {
    //     return EventCountSnapshotTriggerDefinition(snapshotter, 2)
    // }

    {
        EventEntry
        EventStorageEngine
        EmbeddedEventStore
    }

}

@EnableScheduling
@Service
class DoWork(val commandGateway: CommandGateway) {

    private val campFirstDayDate = LocalDate.of(2019, 2, 20)
    private val firstDayId = CampDayId()
    private val campEdition36Id = CampEditionScheduleId(36)

    private val runned = AtomicBoolean(false)

    @Scheduled(fixedDelay = 3000)
    fun example() {
        if (runned.get()) {
            return
        }

        val campEdition36Id = commandGateway.sendAndWait<CampEditionScheduleId>(
                CampEditionScheduleCommand.StartCampEditionScheduling(
                        campEdition36Id,
                        campFirstDayDate,
                        campFirstDayDate.plusDays(CAMP_EDITION_DURATION)
                )
        )

        val campEdition37Id = commandGateway.sendAndWait<CampEditionScheduleId>(
                CampEditionScheduleCommand.StartCampEditionScheduling(
                        CampEditionScheduleId(37),
                        LocalDate.now().minusDays(2),
                        LocalDate.now().plusDays(2)
                )
        )


        commandGateway.sendAndWait<Void>(
                CampEditionScheduleCommand.ScheduleCampDay(
                        campEditionScheduleId = campEdition36Id,
                        campDayId = firstDayId,
                        date = campFirstDayDate
                )
        )

        val dayActivityId = DayActivityId()
        val dayActivityDetails = DayActivityDetails(
                "Przykładowy punkt dnia",
                LocalTime.now(),
                LocalTime.now().plusHours(1).plusMinutes(15),
                "Przykładowy opis punktu dnia"
        )

        commandGateway.sendAndWait<Void>(
                CampEditionScheduleEvent.CampDayActivityScheduled(
                        campEdition36Id,
                        firstDayId,
                        dayActivityId,
                        dayActivityDetails
                )
        )


        runned.set(true);
    }

}
