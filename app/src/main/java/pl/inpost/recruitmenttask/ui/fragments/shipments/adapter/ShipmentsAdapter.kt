package pl.inpost.recruitmenttask.ui.fragments.shipments.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemLoadingBinding
import pl.inpost.recruitmenttask.databinding.ShipmentListHeaderBinding
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.model.ShipmentListItem
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.viewholder.LoadingViewHolder
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.viewholder.ShipmentHeaderViewHolder
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.viewholder.ShipmentViewHolder
import pl.inpost.recruitmenttask.extensions.inflater
import pl.inpost.recruitmenttask.util.simpleDiffUtil

private const val HEADER = 0
private const val ITEM = 1
private const val LOADING = 2

class ShipmentsAdapter(
    private val callback: (ShipmentDomain) -> Unit
) : ListAdapter<ShipmentListItem, RecyclerView.ViewHolder>(simpleDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        HEADER -> ShipmentHeaderViewHolder(
            ShipmentListHeaderBinding.inflate(
                parent.inflater(),
                parent,
                false
            )
        )

        ITEM -> ShipmentViewHolder(ShipmentItemBinding.inflate(parent.inflater(), parent, false))
        LOADING -> LoadingViewHolder(
            ShipmentItemLoadingBinding.inflate(
                parent.inflater(),
                parent,
                false
            )
        )

        else -> throw IllegalStateException("Invalid view type...")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShipmentHeaderViewHolder -> {
                val item = getItem(position) as ShipmentListItem.ListHeader
                holder.bind(item.text)
            }

            is ShipmentViewHolder -> {
                val item = getItem(position) as ShipmentListItem.ListItem
                holder.bind(item.shipment)
                holder.itemView.setOnClickListener {
                    callback.invoke(item.shipment)
                }
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