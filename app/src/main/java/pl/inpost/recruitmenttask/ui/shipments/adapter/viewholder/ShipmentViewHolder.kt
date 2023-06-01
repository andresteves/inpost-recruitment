package pl.inpost.recruitmenttask.ui.shipments.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_PATTERN = "'pn.' | dd.MM.yy | hh:mm"

class ShipmentViewHolder(
    private val binding: ShipmentItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shipmentDomain: ShipmentDomain) = with(binding) {
        val context = binding.root.context
        shipmentNumberView.text = shipmentDomain.number
        shipmentStatusView.text = context.getString(shipmentDomain.status.nameRes)
        when (shipmentDomain.shipmentType) {
            ShipmentType.PARCEL_LOCKER -> shipmentTypeView.setImageResource(R.drawable.ic_paczkomat)
            ShipmentType.COURIER -> shipmentTypeView.setImageResource(R.drawable.ic_kurier)
        }
        shipmentUserView.text = shipmentDomain.sender?.name ?: shipmentDomain.sender?.email

        if (shipmentDomain.status == ShipmentStatus.READY_TO_PICKUP
            || shipmentDomain.status == ShipmentStatus.DELIVERED
        ) {
            shipmentDomain.storedDate?.let {
                shipmentSecondaryStatusHeaderView.isVisible = true
                shipmentDateView.isVisible = true
                val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
                shipmentDateView.text = formatter.format(it)
            }
        }
    }

}