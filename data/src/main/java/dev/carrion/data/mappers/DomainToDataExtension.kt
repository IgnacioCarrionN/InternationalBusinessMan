package dev.carrion.data.mappers

import dev.carrion.data.local.DataEntity
import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction

fun Rate.map() = DataEntity.Rate(
    id = 0,
    from = from,
    to = to,
    rate = rate.toString()
)

fun Transaction.map() = DataEntity.Transaction(
    id = 0,
    sku = sku,
    amount = amount.toString(),
    currency = currency
)