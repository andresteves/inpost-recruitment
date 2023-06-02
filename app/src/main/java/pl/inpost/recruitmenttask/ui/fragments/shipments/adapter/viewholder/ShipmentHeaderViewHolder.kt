package pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.viewholder

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentListHeaderBinding

class ShipmentHeaderViewHolder(
    private val binding: ShipmentListHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(@StringRes title: Int) = with(binding) {
        listHeaderView.setText(title)
    }

}