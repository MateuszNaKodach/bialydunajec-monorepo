package org.bialydunajec.campedition.application.query.api

import org.bialydunajec.ddd.application.base.query.Query

sealed class CampEditionQuery : Query {
    class All : CampEditionQuery()
    class ById(val campEditionId: Int) : CampEditionQuery()
}