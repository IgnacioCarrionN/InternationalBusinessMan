package dev.carrion.domain.entities

import java.math.BigDecimal

data class Transaction(val sku: String, val amount: BigDecimal, val currency: String)