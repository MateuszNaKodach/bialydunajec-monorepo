internal abstract class InMemoryRepository<T> {

    protected val items = mutableListOf<T>()

    fun save(aggregateRoot: T): T {
        items.add(aggregateRoot)
        return aggregateRoot
    }
}
