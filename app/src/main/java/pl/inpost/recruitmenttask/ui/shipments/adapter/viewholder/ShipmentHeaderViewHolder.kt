package pl.inpost.recruitmenttask.ui.shipments.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentListHeaderBinding

class ShipmentHeaderViewHolder(
    private val binding: ShipmentListHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String) = with(binding) {
        listHeaderView.text = title
    }

}