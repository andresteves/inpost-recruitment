package pl.inpost.recruitmenttask.data.shipments

import io.kotlintest.shouldBe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import pl.inpost.recruitmenttask.data.database.dao.ShipmentDAO
import pl.inpost.recruitmenttask.domain.shipments.model.CustomerDomain
import pl.inpost.recruitmenttask.domain.shipments.model.EventLogDomain
import pl.inpost.recruitmenttask.domain.shipments.model.OperationsDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import java.util.Date

internal class ShipmentGatewayTest {

    @Test
    fun `Repository on refresh call should store data on db`() = runTest {
        val shipments = List(4) { mockShipmentNetwork(number = "${it}324474783487347834") }
        val api = mock<ShipmentApi> {
            onBlocking { getShipments() } doReturn shipments
        }
        val shipmentDAO = mock<ShipmentDAO>()

        val repository = ShipmentGateway(api, shipmentDAO)

        repository.refresh()
        verify(shipmentDAO).isArchived("0324474783487347834")
        verify(shipmentDAO).insert(shipments[0].toDB(false))
    }

    @Test
    fun `Respository on archive should update db`() = runTest {
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
            on { archived } doReturn false
            on { number } doReturn "1234567890987421234678"
            on { status } doReturn pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus.READY_TO_PICKUP
            on { shipmentType } doReturn pl.inpost.recruitmenttask.domain.shipments.model.ShipmentType.COURIER
            on { expiryDate } doReturn currentDate
            on { operations } doReturn operationDomain
            on { sender } doReturn customerDomain
            on { eventLog } doReturn listOf(eventLogDomain)
        }

        val shipments = List(4) { mockShipmentNetwork(number = "${it}324474783487347834") }
        val api = mock<ShipmentApi> {
            onBlocking { getShipments() } doReturn shipments
        }
        val shipmentDAO = mock<ShipmentDAO>()

        val repository = ShipmentGateway(api, shipmentDAO)

        repository.archive(shipmentDomain)

        verify(shipmentDAO).update(shipmentDomain.toDB())
    }

    @Test
    fun `Repository on calling shipments should emit db data and call refresh`() = runTest {
        val shipments = List(4) { mockShipmentNetwork(number = "${it}324474783487347834") }
        val api = mock<ShipmentApi> {
            onBlocking { getShipments() } doReturn shipments
        }
        val shipmentDAO = mock<ShipmentDAO> {
            onBlocking { loadAll() } doReturn flow {
                emit(shipments.map { it.toDB(false) })
            }
        }
        val repository = ShipmentGateway(api, shipmentDAO)

        repository.shipments().collectLatest {
            it.size shouldBe 4
            it[0].number shouldBe "0324474783487347834"
            it[1].number shouldBe "1324474783487347834"
            it[2].number shouldBe "2324474783487347834"
            it[3].number shouldBe "3324474783487347834"
        }
    }

}