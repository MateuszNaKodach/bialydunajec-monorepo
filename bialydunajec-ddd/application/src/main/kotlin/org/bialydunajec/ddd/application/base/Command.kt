package org.bialydunajec.ddd.application.base

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

open class Command<TargetAggregateIdType : AggregateId>(val targetAggregateId: TargetAggregateIdType)