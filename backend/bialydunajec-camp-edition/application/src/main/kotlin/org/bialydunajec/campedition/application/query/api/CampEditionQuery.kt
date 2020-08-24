package org.bialydunajec.campedition.application.query.api

import org.bialydunajec.ddd.application.base.query.Query

sealed class CampEditionQuery : Query {
    object All : CampEditionQuery()
    class ById(val campEditionId: Int) : CampEditionQuery()
}
