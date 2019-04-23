package dev.carrion.internationalbusinessman.ui.transactions

import android.content.Context
import android.widget.ArrayAdapter

class CurrencySpinnerAdapter(context: Context, id: Int) : ArrayAdapter<String>(context, id) {

    val currencyList: List<String> = mutableListOf()

    override fun addAll(collection: MutableCollection<out String>) {
        super.addAll(collection)
    }
}