package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.Credentials
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import io.mockk.*
import org.junit.Test

class GooglePhotosGalleryProviderTest {


    @Test
    fun getPhotosInAlbum() {
        //given
        val credentials = mockk<Credentials>(relaxed = true)
        val photosLibrarySettings = PhotosLibrarySettings
                .newBuilder()
                .setCredentialsProvider(
                        FixedCredentialsProvider.create(credentials)
                ).build()
        val photosLibraryClient = mockk<PhotosLibraryClient>(relaxed = true)
        mockkObject(GooglePhotosCredentialService.Companion)
        every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
        val albumId = "albumId"
        val googlePhotosGalleryProvider = GooglePhotosGalleryProvider()

        //when
        googlePhotosGalleryProvider.getPhotosInAlbum(albumId)

        //then
        verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
        confirmVerified(photosLibraryClient)
    }

    @Test
    fun getAlbumListByCampEdition() {
    }

    @Test
    fun getAlbumList() {
    }

    @Test
    fun createAlbum() {
    }
}


/*
object EventSourcedSeatRepositorySpecification : Spek({

    Feature("time traveling with event sourcing") {

        Scenario("events up to specific point in the past") {
            val timeProvider: TestClockTimeProvider
                    by memoized { TestClockTimeProvider.withFixedTime(LocalTime.of(10, 0)) }
            val repository: SeatRepository by memoized { InMemorySeatEventSourcedRepository(timeProvider) }
            val campBusCourseId: BusCourseId by memoized { BusCourseId() }
            val seatId: SeatId by memoized { SeatId() }
            val passengerId: PassengerId by memoized { PassengerId() }
            val initialSeat: Seat by memoized { Seat.newInstance { timeProvider.instant }.handle(SeatCommand.AddSeatForCourse(seatId, campBusCourseId)) }

            Given("current time is 10:00") {
                timeProvider.timeTravelTo(LocalTime.of(10, 0))
                println(timeProvider.instant)
            }

            And("seat is saved") {
                repository.save(initialSeat)
            }

            When("we reserve the seat at 10:10") {
                timeProvider.timeTravelTo(LocalTime.of(10, 10))
                println(timeProvider.instant)
                reserveBusSeat(repository, seatId, passengerId)
            }

            Then("if we load seat data with time set to 10:11 next reservation should fail") {
                timeProvider.timeTravelTo(LocalTime.of(10, 11))
                println(timeProvider.instant)
                assertThat(reserveBusSeat(repository, seatId, passengerId)).hasClass(SeatEvent.SeatReservationFailed::class)
            }

            And("if we load seat data with time set to 10:01 reservation should be possible") {
                timeProvider.timeTravelTo(LocalTime.of(10, 1))
                assertThat {
                    reserveBusSeat(repository, seatId, passengerId)
                }.doesNotThrowAnyException()
            }


        }

    }
})

private fun reserveBusSeat(repository: SeatRepository, seatId: SeatId, passengerId: PassengerId): SeatEvent {
    val seat = repository.findById(seatId)!!.handle(SeatCommand.ReserveSeat(seatId, passengerId))
    val lastEvent = seat.changes.last()
    repository.save(seat)
    return lastEvent
}

 */
