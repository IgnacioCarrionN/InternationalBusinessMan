package dev.carrion.domain.entities

import java.math.BigDecimal

data class Rate(val from: String, val to: String, val rate: BigDecimal)