package pl.inpost.recruitmenttask.ui.shipments.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemLoadingBinding
import pl.inpost.recruitmenttask.databinding.ShipmentListHeaderBinding
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.ui.shipments.adapter.model.ShipmentListItem
import pl.inpost.recruitmenttask.ui.shipments.adapter.viewholder.LoadingViewHolder
import pl.inpost.recruitmenttask.ui.shipments.adapter.viewholder.ShipmentHeaderViewHolder
import pl.inpost.recruitmenttask.ui.shipments.adapter.viewholder.ShipmentViewHolder
import pl.inpost.recruitmenttask.util.inflater
import pl.inpost.recruitmenttask.util.simpleDiffUtil

private const val HEADER = 0
private const val ITEM = 1
private const val LOADING = 2

class ShipmentsAdapter(
    callback: (ShipmentDomain) -> Unit
) : ListAdapter<ShipmentListItem, RecyclerView.ViewHolder>(simpleDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        HEADER -> ShipmentHeaderViewHolder(ShipmentListHeaderBinding.inflate(parent.inflater()))
        ITEM -> ShipmentViewHolder(ShipmentItemBinding.inflate(parent.inflater()))
        LOADING -> LoadingViewHolder(ShipmentItemLoadingBinding.inflate(parent.inflater()))
        else -> throw IllegalStateException("Invalid view type...")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ShipmentHeaderViewHolder -> {
                val item = getItem(position) as ShipmentListItem.ListHeader
                holder.bind(item.text)
            }
            is ShipmentViewHolder -> {
                val item = getItem(position) as ShipmentListItem.ListItem
                holder.bind(item.shipment)
            }
            else -> throw IllegalStateException("Invalid view holder...")
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is ShipmentListItem.ListHeader -> HEADER
        is ShipmentListItem.ListItem -> ITEM
        ShipmentListItem.Loading -> LOADING
    }
}