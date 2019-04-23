package dev.carrion.internationalbusinessman.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.internationalbusinessman.R
import dev.carrion.internationalbusinessman.ui.BusinessViewModel
import kotlinx.android.synthetic.main.fragment_item_transactions.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ItemTransactionsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: BusinessViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemTransactionsAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL

        recyclerTransactions.layoutManager = layoutManager
        recyclerTransactions.adapter = adapter

        viewModel.transactionsItemListUI.observe(this, Observer {
            txtTotalAmount.text = it.total.toString()
            adapter.setData(it.list)
        })
        val spinnerAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, mutableListOf())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCurrency.adapter = spinnerAdapter
        spinnerCurrency.onItemSelectedListener = this
        viewModel.currencyList.observe(this, Observer {listCurrencies ->
            if(listCurrencies.isNotEmpty()){
                spinnerAdapter.clear()
                spinnerAdapter.addAll(listCurrencies.toMutableList())
                spinnerAdapter.notifyDataSetChanged()
            }
        })
        viewModel.currency.observe(this, Observer {
            val position = spinnerAdapter.getPosition(it)
            spinnerCurrency.setSelection(position)
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setCurrency(parent?.getItemAtPosition(position) as String)
    }
}