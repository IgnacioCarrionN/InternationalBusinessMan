package dev.carrion.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dev.carrion.data.local.rate.RateDao
import dev.carrion.data.local.transaction.TransactionDao
import dev.carrion.data.mappers.map
import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


// TODO LocalDataSource methods to access DB
class LocalDataSource(val rateDao: RateDao, val transactionDao: TransactionDao) : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    // Rate methods
    fun getRates(): LiveData<List<Rate>> {
        val ratesDB = rateDao.selectAll()

        val mediator = MediatorLiveData<List<Rate>>()

        mediator.addSource(ratesDB) {
            val list = it.map { dataRate ->
                dataRate.map()
            }
            mediator.value = list
        }
        return mediator
    }

    fun saveRates(rates: List<Rate>, insertFinished: () -> Unit) {
        launch {
            val ratesDB = rates.map {
                it.map()
            }
            rateDao.insert(ratesDB)
            insertFinished()
        }
    }

    fun deleteRates() = launch {
        rateDao.truncate()
    }


    // Transaction methods
    fun getTransactions(): LiveData<List<Transaction>> {
        val transactionsDB = transactionDao.selectAll()

        val mediator = MediatorLiveData<List<Transaction>>()

        mediator.addSource(transactionsDB) {
            val domainList = it.map { dataTransaction ->
                dataTransaction.map()
            }
            mediator.value = domainList
        }
        return mediator
    }

    fun getTransactionsForItem(item: String): LiveData<List<Transaction>> {
        val transactionsDB = transactionDao.selectByItem(item)

        val mediator = MediatorLiveData<List<Transaction>>()

        mediator.addSource(transactionsDB) {
            val domainList = it.map { dataTransaction ->
                dataTransaction.map()
            }
            mediator.value = domainList
        }
        return mediator
    }

    fun getDistinctItemsList(): LiveData<List<String>> = transactionDao.selectDistinctItemNames()

    fun saveTransactions(transactions: List<Transaction>, insertFinished: () -> Unit) {
        launch {
            val transactionsDB = transactions.map {
                it.map()
            }
            transactionDao.insert(transactionsDB)
            insertFinished()
        }
    }

    fun deleteTransactions() = launch {
        transactionDao.truncate()
    }
}