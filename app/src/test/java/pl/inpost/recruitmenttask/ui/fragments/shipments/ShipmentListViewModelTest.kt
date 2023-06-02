package pl.inpost.recruitmenttask.ui.fragments.shipments

import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.model.ShipmentListItem

@OptIn(ExperimentalCoroutinesApi::class)
internal class ShipmentListViewModelTest {

    @Test
    fun `Viewmodel on init should call repo shipments and return proper state`() = runTest {
        val repo = mock<ShipmentRepository> {
            onBlocking { shipments() } doReturn flow {
                emit(Result.success(listOf(mockShipmentDomain())))
            }
        }

        val viewModel = ShipmentListViewModel(repo)

        backgroundScope.launch(StandardTestDispatcher(testScheduler)) {
            viewModel.state.collectLatest {
                it.shouldBeTypeOf<State.Shipments>()
                with(it as State.Shipments) {
                    it.shipments.size shouldBe 3
                    it.shipments[0].shouldBeTypeOf<ShipmentListItem.ListHeader>()
                }
            }
        }
    }

    @Test
    fun `Viewmodel on init with error on call shipments should return proper event`() = runTest {
        val repo = mock<ShipmentRepository> {
            onBlocking { shipments() } doReturn flow {
                emit(Result.failure(Exception("Error")))
            }
        }

        val viewModel = ShipmentListViewModel(repo)

        backgroundScope.launch(StandardTestDispatcher(testScheduler)) {
            viewModel.event.collectLatest {
                it.shouldBeTypeOf<Event.Error>()
            }
        }
    }


    @Test
    fun `Viewmodel on archive should return proper state`() = runTest {
        val shipments = listOf(mockShipmentDomain(), mockShipmentDomain())
        val repo = mock<ShipmentRepository> {
            onBlocking { shipments() } doReturn flow {
                emit(Result.success(shipments))
            }

            onBlocking { archive(any()) } doReturn Unit
        }

        val viewModel = ShipmentListViewModel(repo)
        val shipmentDomain = shipments[0]
        viewModel.archive(shipmentDomain.copy(archived = true))

        backgroundScope.launch(StandardTestDispatcher(testScheduler)) {
            viewModel.state.collectLatest {
                it.shouldBeTypeOf<State.Shipments>()
                with(it as State.Shipments) {
                    it.shipments.size shouldNotBe 4
                    it.shipments[0].shouldBeTypeOf<ShipmentListItem.ListHeader>()
                    it.shipments[1].shouldBeTypeOf<ShipmentListItem.ListItem>()
                    with(it.shipments[1] as ShipmentListItem.ListItem) {
                        shipment.archived shouldBe false
                    }
                    it.shipments[2].shouldBeTypeOf<ShipmentListItem.ListHeader>()
                }
            }
        }
    }

    @Test
    fun `Viewmodel when filtering by Ready to Pickup should return proper state`() = runTest {
        val repo = mock<ShipmentRepository> {
            onBlocking { shipments() } doReturn flow {
                emit(
                    Result.success(
                        listOf(
                            mockShipmentDomain(),
                            mockShipmentDomain(status = ShipmentStatus.OTHER),
                            mockShipmentDomain(status = ShipmentStatus.READY_TO_PICKUP),
                            mockShipmentDomain(status = ShipmentStatus.OTHER)
                        )
                    )
                )
            }
        }

        val viewModel = ShipmentListViewModel(repo)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.filter(FilterType.READY_PICKUP)
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {
                it.shouldBeTypeOf<State.Shipments>()
                with(it as State.Shipments) {
                    it.shipments.size shouldBe 3
                    it.shipments[0].shouldBeTypeOf<ShipmentListItem.ListHeader>()
                    (it.shipments[1] as ShipmentListItem.ListItem).shipment.status shouldBe ShipmentStatus.READY_TO_PICKUP
                }
            }
        }
    }

    @Test
    fun `Viewmodel when filtering by Others should return proper state`() = runTest {
        val repo = mock<ShipmentRepository> {
            onBlocking { shipments() } doReturn flow {
                emit(
                    Result.success(
                        listOf(
                            mockShipmentDomain(),
                            mockShipmentDomain(status = ShipmentStatus.OTHER),
                            mockShipmentDomain(status = ShipmentStatus.READY_TO_PICKUP),
                            mockShipmentDomain(status = ShipmentStatus.OTHER)
                        )
                    )
                )
            }
        }

        val viewModel = ShipmentListViewModel(repo)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.filter(FilterType.OTHERS)
        }

        backgroundScope.launch(StandardTestDispatcher(testScheduler)) {
            viewModel.state.collect {
                it.shouldBeTypeOf<State.Shipments>()
                with(it as State.Shipments) {
                    it.shipments.size shouldBe 4
                    it.shipments[0].shouldBeTypeOf<ShipmentListItem.ListHeader>()
                    (it.shipments[1] as ShipmentListItem.ListItem).shipment.status shouldBe ShipmentStatus.OTHER
                    (it.shipments[2] as ShipmentListItem.ListItem).shipment.status shouldBe ShipmentStatus.OTHER
                }
            }
        }
    }

}