package com.fatih.popcorn.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.popcorn.R
import com.fatih.popcorn.adapter.HomeFragmentAdapter
import com.fatih.popcorn.adapter.SeasonAdapter
import com.fatih.popcorn.adapter.WatchListAdapter
import com.fatih.popcorn.databinding.FragmentSeasonsBinding
import com.fatih.popcorn.databinding.FragmentWatchListBinding
import com.fatih.popcorn.other.Status
import com.fatih.popcorn.viewmodel.DetailsFragmentViewModel

class WatchListFragment:Fragment() {

    private val viewModel: DetailsFragmentViewModel by lazy{
        ViewModelProvider(requireActivity())[DetailsFragmentViewModel::class.java]
    }
    private var _binding: FragmentWatchListBinding?=null
    private val binding: FragmentWatchListBinding
        get() = _binding!!

    private var watchListAdapter: WatchListAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= DataBindingUtil.inflate(inflater,R.layout.fragment_watch_list,container,false)
        watchListAdapter= WatchListAdapter(R.layout.watch_list_row).apply {
            setMyOnClickLambda { url, id, pair ->
                pair?.let {
                    findNavController().navigate(WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(id,pair.first,pair.second,url))
                }?: findNavController().navigate(WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(id,R.color.white,R.color.black2,url))
            }
        }
        binding.watchListRecyclerView.apply {
            layoutManager= GridLayoutManager(requireContext(), Resources.getSystem().displayMetrics.widthPixels/200)
            adapter=watchListAdapter
            hasFixedSize()
        }
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData(){
        viewModel.entityList.observe(viewLifecycleOwner) { result->
            watchListAdapter?.list=result
        }
    }

    override fun onDestroyView() {
        watchListAdapter=null
        _binding=null
        super.onDestroyView()
    }
}