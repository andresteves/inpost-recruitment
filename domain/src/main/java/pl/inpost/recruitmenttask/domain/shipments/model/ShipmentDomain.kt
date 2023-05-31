package pl.inpost.recruitmenttask.domain.shipments.model

import java.time.ZonedDateTime

data class ShipmentDomain(
    val number: String,
    val archived: Boolean,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val eventLog: List<EventLogDomain>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerDomain?,
    val sender: CustomerDomain?,
    val operations: OperationsDomain
)
