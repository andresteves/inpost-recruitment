package pl.inpost.recruitmenttask.data.shipments

import pl.inpost.recruitmenttask.data.database.entity.Shipment
import pl.inpost.recruitmenttask.data.shipments.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.shipments.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.shipments.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.shipments.model.CustomerDomain
import pl.inpost.recruitmenttask.domain.shipments.model.EventLogDomain
import pl.inpost.recruitmenttask.domain.shipments.model.OperationsDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType


fun Shipment.toDomain() = ShipmentDomain(
    number,
    archived,
    shipmentTypeDomain(shipmentType),
    statusToDomain(status),
    eventLog.map { it.toDomain() },
    openCode,
    expiryDate,
    storedDate,
    pickUpDate,
    receiver?.toDomain(),
    sender?.toDomain(),
    operations.toDomain()
)

fun ShipmentDomain.toDB() = Shipment(
    number,
    archived,
    shipmentType.name,
    status.name,
    eventLog.map { it.toData() },
    openCode,
    expiryDate,
    storedDate,
    pickUpDate,
    receiver?.toData(),
    sender?.toData(),
    operations.toData()
)

private fun shipmentTypeDomain(type: String) = when (type) {
    "PARCEL_LOCKER" -> ShipmentType.PARCEL_LOCKER
    else -> ShipmentType.COURIER
}

private fun statusToDomain(status: String) = when (status) {
    "CREATED" -> ShipmentStatus.CREATED
    "CONFIRMED" -> ShipmentStatus.CONFIRMED
    "ADOPTED_AT_SOURCE_BRANCH" -> ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH
    "SENT_FROM_SOURCE_BRANCH" -> ShipmentStatus.SENT_FROM_SOURCE_BRANCH
    "ADOPTED_AT_SORTING_CENTER" -> ShipmentStatus.ADOPTED_AT_SORTING_CENTER
    "SENT_FROM_SORTING_CENTER" -> ShipmentStatus.SENT_FROM_SORTING_CENTER
    "OTHER" -> ShipmentStatus.OTHER
    "DELIVERED" -> ShipmentStatus.DELIVERED
    "RETURNED_TO_SENDER" -> ShipmentStatus.RETURNED_TO_SENDER
    "AVIZO" -> ShipmentStatus.AVIZO
    "OUT_FOR_DELIVERY" -> ShipmentStatus.OUT_FOR_DELIVERY
    "READY_TO_PICKUP" -> ShipmentStatus.READY_TO_PICKUP
    "PICKUP_TIME_EXPIRED" -> ShipmentStatus.PICKUP_TIME_EXPIRED
    else -> ShipmentStatus.OTHER
}

private fun EventLogNetwork.toDomain() = EventLogDomain(name, date)

private fun CustomerNetwork.toDomain() = CustomerDomain(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun OperationsNetwork.toDomain() = OperationsDomain(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)

private fun EventLogDomain.toData() = EventLogNetwork(name, date)

private fun CustomerDomain.toData() = CustomerNetwork(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun OperationsDomain.toData() = OperationsNetwork(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)

fun ShipmentNetwork.toDB(archived: Boolean) = Shipment(
    number,
    archived,
    shipmentType,
    status,
    eventLog,
    openCode,
    expiryDate,
    storedDate,
    pickUpDate,
    receiver,
    sender,
    operations
)