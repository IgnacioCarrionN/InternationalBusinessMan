package dev.carrion.internationalbusinessman.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.carrion.internationalbusinessman.R
import dev.carrion.internationalbusinessman.ui.BusinessViewModel
import kotlinx.android.synthetic.main.fragment_select_item.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SelectItemFragment : Fragment(), SelectItemAdapter.OnItemClickListener {

    private val viewModel: BusinessViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SelectItemAdapter(this)
        val gridLayoutManager = GridLayoutManager(context, 2)


        itemsRecycler.adapter = adapter
        itemsRecycler.layoutManager = gridLayoutManager

        viewModel.itemNames.observe(this,  Observer {
            adapter.setListData(it)
        })
    }

    override fun onItemClick(sku: String) {
        viewModel.searchItem(sku)
        activity?.let {
            findNavController().navigate(R.id.action_selectItemFragment_to_itemTransactionsFragment)
        }
    }
}