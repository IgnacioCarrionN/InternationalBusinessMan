package dev.carrion.domain

import dev.carrion.domain.entities.ItemTransactions
import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction
import java.math.BigDecimal
import java.math.RoundingMode

fun getAmountForCurrency(transaction: Transaction, currency: String, rates: List<Rate>): BigDecimal? {
    if(transaction.currency == currency)
        return transaction.amount
    for(rate in rates){
        if(rate.from == transaction.currency && rate.to == currency){
            return transaction.amount * rate.rate
        }
    }
    return convertTo(transaction, currency, rates, emptyList())
}

/**
 *  Method to convert when no direct rate is given
 */
fun convertTo(transaction: Transaction, currency: String, rates: List<Rate>, usedRates: List<String>): BigDecimal?{
    for(rate in rates){
        if(rate.from == transaction.currency){
            // If we have the correct rate we return its amount
            if(rate.to == currency) {
                return transaction.amount * rate.rate
            } else {
                // We check if the rate is not used on a previous iteration of the function.
                if(!usedRates.contains(rate.to)) {
                    // We convert the transaction to this currency and calculate the amount for given rate.
                    val convertedTransaction = Transaction(transaction.sku, transaction.amount * rate.rate, rate.to)
                    // We add the rate to the list of used rates to avoid infinite recursion.
                    val newUsedRates = usedRates + rate.from
                    // Call again the function with the new transaction and new usedRates list.
                    return convertTo(convertedTransaction, currency, rates, newUsedRates)
                }
            }
        }
    }
    // If no rate is found we return null
    return null
}

/**
 * Extension for list of transactions, used to get a ItemTransactions object with the list updated with amounts
 * for the given currency
 */
fun List<Transaction>.toItemTransactions(currency: String, rates: List<Rate>, positionsRound: Int): ItemTransactions {
    var totalAmount = BigDecimal(0)
    val listTransactions: MutableList<Transaction> = mutableListOf()
    this.forEach {
        val convertedAmount = getAmountForCurrency(it, currency, rates) ?: BigDecimal(0)
        totalAmount += convertedAmount
        listTransactions.add(Transaction(
            sku = it.sku,
            amount = convertedAmount.roundHalfToEven(positionsRound),
            currency = currency
        ))
    }
    return ItemTransactions(listTransactions.toList(), totalAmount.roundHalfToEven(positionsRound))
}

fun BigDecimal.roundHalfToEven(positions: Int): BigDecimal = this.setScale(positions, RoundingMode.HALF_EVEN)

// For safety returns null when not all currencies are the same
fun List<Transaction>.getTotalAmount(): BigDecimal? {
    var total = BigDecimal(0)
    // Check all currencies are the same
    if(this.isNotEmpty()){
        val currency = this[0].currency
        this.forEach {
            if(it.currency != currency){
                return null
            }
            total += it.amount
        }
    }
    return total
}

fun List<Rate>.getDistinctCurrencies(): List<String> {
    val rates: HashSet<String> = hashSetOf()
    this.forEach {
        rates.add(it.from)
        rates.add(it.to)
    }
    return rates.toList()
}