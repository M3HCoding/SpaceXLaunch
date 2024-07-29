package com.spacexlaunch.android.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.databinding.ItemHomeBinding

class HomeAdapter(private val listener: HomeListListener) :
    PagingDataAdapter<XModel, RecyclerView.ViewHolder>(Diff()) {

    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListingItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val listingItemViewHolder = holder as ListingItemViewHolder
        val item = getItem(position)

        listingItemViewHolder.binding.let {
            it.tvMissionName.text = item?.mission_name
            it.tvLaunchYear.text = item?.launch_year
            it.tvRocketName.text = item?.rocket?.rocket_name
            it.tvBookMark.isSelected = false

            it.tvBookMark.setOnClickListener {
                listener.onBookmarkClick(
                    item?.flight_number,
                    listingItemViewHolder.binding.tvBookMark,
                    item
                )
            }
        }

        listener.isBookmark(item?.flight_number, listingItemViewHolder.binding.tvBookMark)

        listingItemViewHolder.itemView.setOnClickListener {
            if (item != null) {
                listener.onItemClick(item.flight_number)
            }
        }


    }

    class Diff : ItemCallback<XModel>() {
        override fun areItemsTheSame(oldItem: XModel, newItem: XModel): Boolean {
            return oldItem.flight_number == newItem.flight_number
        }

        override fun areContentsTheSame(oldItem: XModel, newItem: XModel): Boolean {
            return oldItem == newItem
        }
    }

    interface HomeListListener {
        fun onItemClick(item: Int?)
        fun onBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?)
        fun isBookmark(item: Int?, favorite: ImageView)
    }

    inner class ListingItemViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

}