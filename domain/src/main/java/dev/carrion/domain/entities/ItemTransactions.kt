package dev.carrion.domain.entities

import java.math.BigDecimal

data class ItemTransactions(val list: List<Transaction>, val total: BigDecimal)