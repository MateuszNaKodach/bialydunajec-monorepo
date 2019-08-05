package org.bialydunajec.application.campbus

import org.bialydunajec.campbus.domain.SeatCommand
import org.bialydunajec.campbus.domain.SeatRepository

internal class ReserveSeatApplicationService(val seatRepository: SeatRepository) {


    /*
    TODO: Check permission and find entity - synchronous, Async handle command etc. Or use Mono/ Completable Future!?
     */
    fun execute(command: SeatCommand.ReserveSeat) {
        seatRepository.findById(command.aggregateId) //TODO: Publish technical failure if null!!!
              //  .handle(command)
    }

}