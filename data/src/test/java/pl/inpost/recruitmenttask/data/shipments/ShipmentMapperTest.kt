package pl.inpost.recruitmenttask.data.shipments

import io.kotlintest.shouldBe
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import pl.inpost.recruitmenttask.data.shipments.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.shipments.model.ShipmentType
import pl.inpost.recruitmenttask.domain.shipments.model.CustomerDomain
import pl.inpost.recruitmenttask.domain.shipments.model.EventLogDomain
import pl.inpost.recruitmenttask.domain.shipments.model.OperationsDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import java.util.Date

internal class ShipmentMapperTest {

    @Test
    fun `Shipment network model should be properly mapped to db entity`() {
        val currentDate = Date()
        val shipmentNetwork = mockShipmentNetwork(openCode = "12345", expireDate = currentDate)
        val shipmentDB = shipmentNetwork.toDB(true)

        with(shipmentDB) {
            archived shouldBe true
            status shouldBe ShipmentStatus.DELIVERED.name
            shipmentType shouldBe ShipmentType.PARCEL_LOCKER.name
            openCode shouldBe "12345"
            expiryDate shouldBe currentDate
            receiver?.email shouldBe "name@email.com"
            operations.highlight shouldBe true
            operations.collect shouldBe false
        }
    }

    @Test
    fun `Shipment entity model should be properly mapped to domain model`() {
        val currentDate = Date()
        val operationsMock = mockOperationsNetwork(highlight = false, endOfWeekCollection = true)
        val customerMock = CustomerNetwork(null, null, "Testing")
        val shipmentNetwork = mockShipmentNetwork(
            status = ShipmentStatus.CONFIRMED,
            type = ShipmentType.COURIER,
            openCode = "2345",
            expireDate = currentDate,
            operations = operationsMock,
            sender = customerMock
        )

        val shipment = shipmentNetwork.toDB(false)

        with(shipment.toDomain()) {
            status shouldBe pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus.CONFIRMED
            shipmentType shouldBe pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType.COURIER
            openCode shouldBe "2345"
            expiryDate shouldBe currentDate
            operations.highlight shouldBe false
            operations.endOfWeekCollection shouldBe true
            sender?.name shouldBe "Testing"
        }
    }

    @Test
    fun `Shipment domain model should be properly mapped to entity model`() {
        val currentDate = Date()
        val operationDomain = mock<OperationsDomain> {
            on { highlight } doReturn true
            on { endOfWeekCollection } doReturn true
        }
        val customerDomain = mock<CustomerDomain> {
            on { email } doReturn "name@email.com"
        }
        val eventLogDomain = mock<EventLogDomain> {
            on { name } doReturn "CREATED"
        }
        val shipmentDomain = mock<ShipmentDomain> {
            on { openCode } doReturn "54321"
            on { number } doReturn "1234567890987421234678"
            on { status } doReturn pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus.READY_TO_PICKUP
            on { shipmentType } doReturn pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType.COURIER
            on { expiryDate } doReturn currentDate
            on { operations } doReturn operationDomain
            on { sender } doReturn customerDomain
            on { eventLog } doReturn listOf(eventLogDomain)
        }

        with(shipmentDomain.toDB()) {
            openCode shouldBe "54321"
            expiryDate shouldBe currentDate
            number shouldBe "1234567890987421234678"
            status shouldBe pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus.READY_TO_PICKUP.name
        }

    }

}