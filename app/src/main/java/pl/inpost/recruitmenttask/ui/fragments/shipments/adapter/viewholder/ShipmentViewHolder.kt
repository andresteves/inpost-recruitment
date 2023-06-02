package pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.viewholder

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType
import java.text.SimpleDateFormat
import java.util.Date
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

        //Made some assumptions not sure if correct though
        when (shipmentDomain.status) {
            ShipmentStatus.CREATED,
            ShipmentStatus.CONFIRMED,
            ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH,
            ShipmentStatus.SENT_FROM_SOURCE_BRANCH,
            ShipmentStatus.ADOPTED_AT_SORTING_CENTER,
            ShipmentStatus.SENT_FROM_SORTING_CENTER,
            ShipmentStatus.RETURNED_TO_SENDER,
            ShipmentStatus.DELIVERED,
            ShipmentStatus.PICKUP_TIME_EXPIRED,
            ShipmentStatus.OTHER -> {
                shipmentSecondaryStatusHeaderView.isVisible = false
                shipmentDateView.isVisible = false
            }

            ShipmentStatus.AVIZO -> shipmentDomain.expiryDate?.let {
                handleStatusDates(context, it, R.string.code_expiration)
            }

            ShipmentStatus.OUT_FOR_DELIVERY -> shipmentDomain.expiryDate?.let {
                handleStatusDates(context, it, R.string.delivery_until)
            }

            ShipmentStatus.READY_TO_PICKUP -> shipmentDomain.storedDate?.let {
                handleStatusDates(context, it, R.string.ready_to_pickup)
            }
        }
    }

    private fun handleStatusDates(context: Context, date: Date, @StringRes title: Int) =
        with(binding) {
            shipmentSecondaryStatusHeaderView.isVisible = true
            shipmentSecondaryStatusHeaderView.text = context.getString(title)
            shipmentDateView.isVisible = true
            val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
            shipmentDateView.text = formatter.format(date)
        }

}