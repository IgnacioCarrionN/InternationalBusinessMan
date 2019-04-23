package dev.carrion.internationalbusinessman.ui

import android.util.Log
import androidx.lifecycle.*
import dev.carrion.data.Repository
import dev.carrion.domain.*
import dev.carrion.domain.entities.ItemTransactions

class BusinessViewModel(private val repository: Repository) : ViewModel() {

    private val itemName = MutableLiveData<String>()

    val rates = repository.getLocalRates()
    val itemNames = repository.getDistinctItemsList()

    private val transactionsForItemDB = Transformations.switchMap(itemName) {
        repository.getLocalTransactionsForItem(it)
    }

    val transactionsItemListUI = MutableLiveData<ItemTransactions>()

    val currencyList = MutableLiveData<List<String>>()

    val currency = MutableLiveData<String>()

    init {
        currency.postValue("USD")
        repository.clearTables()
        // For test purposes only
        repository.fetchApiRates()
        rates.observeForever {
            currencyList.postValue(it.getDistinctCurrencies())
        }
        repository.fetchApiTransactions()
        itemNames.observeForever {
            Log.d("VIEWMODEL", it.toString())
        }

        transactionsForItemDB.observeForever {
            Log.d("VIEWMODEL", "RATES: ${rates.value}")
            if(it.isNotEmpty()){
                transactionsItemListUI.postValue(
                    it.toItemTransactions(
                        currency.value ?: "USD",
                        rates.value ?: emptyList(),
                        2)
                )
            }
        }

        currency.observeForever {
            if(transactionsForItemDB.value?.isNotEmpty() == true) {
                transactionsItemListUI.postValue(
                    transactionsForItemDB.value?.toItemTransactions(
                        it,
                        rates.value ?: emptyList(),
                        2
                    )
                )
            }
        }

    }

    fun searchItem(item: String){
        itemName.postValue(item)
    }

    fun setCurrency(currency: String){
        this.currency.postValue(currency)
    }

}