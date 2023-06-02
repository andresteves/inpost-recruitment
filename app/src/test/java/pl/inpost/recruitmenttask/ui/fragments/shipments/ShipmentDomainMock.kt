package pl.inpost.recruitmenttask.ui.fragments.shipments

import pl.inpost.recruitmenttask.domain.shipments.model.CustomerDomain
import pl.inpost.recruitmenttask.domain.shipments.model.EventLogDomain
import pl.inpost.recruitmenttask.domain.shipments.model.OperationsDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType
import java.util.Date
import kotlin.random.Random


internal fun mockShipmentDomain(
    number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
    type: ShipmentType = ShipmentType.PARCEL_LOCKER,
    status: ShipmentStatus = ShipmentStatus.DELIVERED,
    sender: CustomerDomain? = mockCustomerDomain(),
    receiver: CustomerDomain? = mockCustomerDomain(),
    operations: OperationsDomain = mockOperationsDomain(),
    eventLog: List<EventLogDomain> = emptyList(),
    openCode: String? = null,
    expireDate: Date? = null,
    storedDate: Date? = null,
    pickupDate: Date? = null,
    isToArchived: Boolean = true
) = ShipmentDomain(
    number = number,
    shipmentType = type,
    status = status,
    eventLog = eventLog,
    openCode = openCode,
    expiryDate = expireDate,
    storedDate = storedDate,
    pickUpDate = pickupDate,
    receiver = receiver,
    sender = sender,
    operations = operations,
    archived = isToArchived
)

internal fun mockCustomerDomain(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = CustomerDomain(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

internal fun mockOperationsDomain(
    manualArchive: Boolean = false,
    delete: Boolean = false,
    collect: Boolean = false,
    highlight: Boolean = true,
    expandAvizo: Boolean = false,
    endOfWeekCollection: Boolean = false
) = OperationsDomain(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)