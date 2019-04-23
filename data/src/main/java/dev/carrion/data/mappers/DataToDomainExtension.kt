package dev.carrion.data.mappers

import dev.carrion.data.local.DataEntity
import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction
import dev.carrion.domain.roundHalfToEven
import java.math.BigDecimal

fun DataEntity.Rate.map() = Rate(
    from = from,
    to = to,
    rate = BigDecimal(rate).roundHalfToEven(2)
)

fun DataEntity.Transaction.map() = Transaction(
    sku = sku,
    amount = BigDecimal(amount),
    currency = currency
)