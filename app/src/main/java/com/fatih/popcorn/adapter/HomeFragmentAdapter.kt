package com.fatih.popcorn.adapter

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.popcorn.R
import com.fatih.popcorn.databinding.FragmentMainRvRowBinding
import com.fatih.popcorn.entities.remote.DiscoverResult
import com.fatih.popcorn.other.Constants.getVibrantColor
import com.fatih.popcorn.ui.HomeFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentAdapter @Inject constructor():RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>() {

    private var myItemClickLambda:((Int,Pair<Int,Int>?)->Unit)?=null

    fun setMyOnClickLambda(lambda:(Int,Pair<Int,Int>?) ->Unit){
        this.myItemClickLambda=lambda
    }


    private val discoverDataUtil=object:DiffUtil.ItemCallback<DiscoverResult>(){
        override fun areContentsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
            return oldItem==newItem
        }

        override fun areItemsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
            return oldItem.id==newItem.id
        }
    }
    private val searchAsyncListDiffer=AsyncListDiffer(this,discoverDataUtil)

    var discoverList:List<DiscoverResult>
        get() = searchAsyncListDiffer.currentList
        set(value) =searchAsyncListDiffer.submitList(value)
    inner class HomeFragmentViewHolder(val binding:FragmentMainRvRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        return HomeFragmentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.fragment_main_rv_row,parent,false))
    }

    override fun getItemCount(): Int {
        return discoverList.size
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        val discoverResult=discoverList[position]
        holder.binding.discoverResult=discoverResult
        holder.itemView.setOnClickListener {
            val pair= getVibrantColor(holder.binding.movieImage)
            discoverResult.id?.let { id->
                myItemClickLambda?.invoke(id,pair)
            }
        }
    }
}