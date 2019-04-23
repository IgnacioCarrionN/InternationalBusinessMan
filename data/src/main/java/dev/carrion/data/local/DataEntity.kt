package dev.carrion.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class DataEntity {
    @Entity(tableName = "rate_table")
    data class Rate(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val from: String,
        val to: String,
        val rate: String
    ): DataEntity()

    @Entity(tableName = "transaction_table")
    data class Transaction(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val sku: String,
        val amount: String,
        val currency: String
    ): DataEntity()
}