package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId

class CampEditionShirtSnapshot(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val shirtSizesFileUrl: Url?,
        val colorOptions: Collection<ShirtColorOptionSnapshot>,
        val sizeOptions: Collection<ShirtSizeOptionSnapshot>
)