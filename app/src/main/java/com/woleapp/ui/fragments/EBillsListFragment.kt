package com.woleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.gcm.R
import com.woleapp.adapters.EBillsAdapter
import com.woleapp.databinding.FragmentEbillsListBinding
import com.woleapp.model.Biller
import com.woleapp.model.EBiller
import com.woleapp.network.getEbillsService
import com.woleapp.util.disposeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EBillsListFragment : BaseFragment() {

    private lateinit var binding: FragmentEbillsListBinding
    private lateinit var billerList: List<EBiller>
    private lateinit var adapter: EBillsAdapter
    private var dataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEbillsListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
            lifecycleOwner = viewLifecycleOwner
            billerList.numColumns = 2
        }
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.setOnRefreshListener {
            if (dataLoaded)
                binding.swipeRefresh.isRefreshing = false
            else
                getBillerList()
        }
        adapter = EBillsAdapter(arrayListOf()) {
            Timber.e("Clicked")
            addFragmentWithoutRemove(
                com.woleapp.R.id.container_main,
                EBillerWebView.newInstance(it.billerId),
                EBillerWebView::class.java.simpleName
            )
        }
        binding.billerList.adapter = adapter
        getBillerList()
        return binding.root
    }

    private fun getBillerList() {
        getEbillsService().getBillerList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                binding.swipeRefresh.isRefreshing = false
            }
            .subscribe { t1, t2 ->
                t1?.let {
                    dataLoaded = true
                    this.billerList = it
                    Timber.e("response")
                    Timber.e(it.size.toString())
                    adapter.setItems(it)
                }
                t2?.let {
                    Toast.makeText(
                        requireContext(),
                        "Error, pull down to try again",
                        Toast.LENGTH_LONG
                    ).show()
                    Timber.e("error")
                    Timber.e(it.localizedMessage)
                }
            }.disposeWith(CompositeDisposable())
    }
}