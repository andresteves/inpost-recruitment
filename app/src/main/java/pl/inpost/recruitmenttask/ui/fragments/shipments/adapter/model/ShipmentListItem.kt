package pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.model

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain

sealed class ShipmentListItem {

    object Loading : ShipmentListItem()

    data class ListHeader(@StringRes val text: Int) : ShipmentListItem()

    data class ListItem(val shipment: ShipmentDomain) : ShipmentListItem()

}
