import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.conditions.CottageConditions
import org.bialydunajec.registrations.domain.cottage.conditions.CottageConditionsRepository

internal class InMemoryCottageConditionsRepository : CottageConditionsRepository,
    InMemoryRepository<CottageConditions>() {

    override fun findByCottageId(cottageId: CottageId): CottageConditions? {
        return items.find { it.getAggregateId() == cottageId }
    }
}
