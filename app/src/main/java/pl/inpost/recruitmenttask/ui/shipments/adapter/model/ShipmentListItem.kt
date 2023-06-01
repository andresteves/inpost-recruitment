package pl.inpost.recruitmenttask.ui.shipments.adapter.model

import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain

sealed class ShipmentListItem {

    object Loading : ShipmentListItem()

    data class ListHeader(val text: String) : ShipmentListItem()

    data class ListItem(val shipment: ShipmentDomain) : ShipmentListItem()

}
