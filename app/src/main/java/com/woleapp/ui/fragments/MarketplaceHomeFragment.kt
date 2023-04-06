package com.woleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.woleapp.R
import com.woleapp.adapters.MarketplaceAdapter
import com.woleapp.databinding.LayoutMarketplaceHomeBinding
import com.woleapp.model.Marketplace
import com.woleapp.network.MerchantsApiClient
import com.woleapp.viewmodels.MarketplaceViewModel
import java.util.*

class MarketplaceHomeFragment : BaseMarketplaceFragment() {
    private lateinit var binding: LayoutMarketplaceHomeBinding
    private lateinit var viewModel: MarketplaceViewModel
    private lateinit var marketplaceAdapter: MarketplaceAdapter
    private var storeList: List<Marketplace>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MarketplaceViewModel::class.java).apply {
            setMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()))
        }
        binding = LayoutMarketplaceHomeBinding.inflate(inflater, container, false)
        marketplaceAdapter = MarketplaceAdapter {
            addFragmentWithoutRemove(
                R.id.container_main,
                MarketplaceProductListFragment.newInstance(it),
                MarketplaceProductListFragment::class.java.simpleName
            )
        }
        binding.dealers.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = marketplaceAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refreshLayout.isRefreshing = true
        viewModel.getStores()
        viewModel.message.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        binding.storeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                storeList?.let {
                    marketplaceAdapter.submitList(
                        if (newText.isEmpty())
                            it
                        else {
                            it.filter { marketplace ->
                                marketplace.name.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                                        || marketplace.description.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                            }

                        }
                    )
                }
                return false
            }
        })
        viewModel.stores.observe(viewLifecycleOwner, { stores ->
            binding.refreshLayout.isRefreshing = false
            stores?.let {
                this.storeList = it
                marketplaceAdapter.submitList(it)
            }
        })
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getStores()
        }
    }
}