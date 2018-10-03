package org.bialydunajec.campedition.application.query.api

import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.ddd.application.base.query.Query

sealed class CampEditionQuery : Query {
    class All : CampEditionQuery()
    class ById(val campEditionId: CampEditionId) : CampEditionQuery()
}