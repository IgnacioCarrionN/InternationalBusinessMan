package dev.carrion.data.network

sealed class ApiEntity {
    data class Rate(val from: String, val to: String, val rate: String): ApiEntity()

    data class Transaction(val sku: String, val amount: String, val currency: String)
}