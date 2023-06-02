package pl.inpost.recruitmenttask.data.shipments

import pl.inpost.recruitmenttask.data.shipments.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.shipments.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.shipments.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentType
import pl.inpost.recruitmenttask.domain.shipments.model.CustomerDomain
import pl.inpost.recruitmenttask.domain.shipments.model.OperationsDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import java.util.Date
import kotlin.random.Random

internal fun mockShipmentNetwork(
    number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
    type: ShipmentType = ShipmentType.PARCEL_LOCKER,
    status: ShipmentStatus = ShipmentStatus.DELIVERED,
    sender: CustomerNetwork? = mockCustomerNetwork(),
    receiver: CustomerNetwork? = mockCustomerNetwork(),
    operations: OperationsNetwork = mockOperationsNetwork(),
    eventLog: List<EventLogNetwork> = emptyList(),
    openCode: String? = null,
    expireDate: Date? = null,
    storedDate: Date? = null,
    pickupDate: Date? = null
) = ShipmentNetwork(
    number = number,
    shipmentType = type.name,
    status = status.name,
    eventLog = eventLog,
    openCode = openCode,
    expiryDate = expireDate,
    storedDate = storedDate,
    pickUpDate = pickupDate,
    receiver = receiver,
    sender = sender,
    operations = operations
)

internal fun mockCustomerNetwork(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = CustomerNetwork(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

internal fun mockOperationsNetwork(
    manualArchive: Boolean = false,
    delete: Boolean = false,
    collect: Boolean = false,
    highlight: Boolean = true,
    expandAvizo: Boolean = false,
    endOfWeekCollection: Boolean = false
) = OperationsNetwork(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)