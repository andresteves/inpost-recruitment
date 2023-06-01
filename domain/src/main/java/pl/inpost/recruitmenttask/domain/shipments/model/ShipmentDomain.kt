package pl.inpost.recruitmenttask.domain.shipments.model

import java.util.Date

data class ShipmentDomain(
    val number: String,
    val archived: Boolean,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val eventLog: List<EventLogDomain>,
    val openCode: String?,
    val expiryDate: Date?,
    val storedDate: Date?,
    val pickUpDate: Date?,
    val receiver: CustomerDomain?,
    val sender: CustomerDomain?,
    val operations: OperationsDomain
)
