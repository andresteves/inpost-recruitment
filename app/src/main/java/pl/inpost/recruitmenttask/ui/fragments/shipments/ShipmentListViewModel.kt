package pl.inpost.recruitmenttask.ui.fragments.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentStatus
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.model.ShipmentListItem
import pl.inpost.recruitmenttask.util.ShipmentComparator
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) : ViewModel() {

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    private lateinit var originalShipments: List<ShipmentDomain>

    init {
        refreshData()
    }

    fun archive(shipmentDomain: ShipmentDomain) = viewModelScope.launch(Dispatchers.IO) {
        shipmentRepository.archive(shipmentDomain)
    }

    fun refresh() {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch(Dispatchers.IO) {
        shipmentRepository.shipments().collect {
            originalShipments = it
            handleShipments(it)
        }
    }

    fun filter(type: FilterType) {
        val filtered = when (type) {
            FilterType.ALL -> originalShipments
            FilterType.READY_PICKUP -> originalShipments.filter { it.status == ShipmentStatus.READY_TO_PICKUP }
            FilterType.OTHERS -> originalShipments.filter { it.status == ShipmentStatus.OTHER }
        }

        handleShipments(filtered)
    }

    private fun handleShipments(shipmentDomains: List<ShipmentDomain>) {
        val items = mutableListOf<ShipmentListItem>()

        items.add(ShipmentListItem.ListHeader(R.string.highlight_header))

        val highlightShipments = shipmentDomains
            .filter { it.operations.highlight }
            .sortedWith(ShipmentComparator())
            .map { ShipmentListItem.ListItem(it) }
        items.addAll(highlightShipments)

        items.add(ShipmentListItem.ListHeader(R.string.other_header))

        shipmentDomains
            .filter { !it.operations.highlight }
            .sortedWith(ShipmentComparator())
            .groupBy {
                it.status
            }.map { mapShipments ->
                items.add(ShipmentListItem.ListHeader(mapShipments.key.nameRes))
                mapShipments.value.forEach { items.add(ShipmentListItem.ListItem(it)) }
            }

        _state.value = State.Shipments(items)
    }
}

sealed class State {
    object Loading : State()

    data class Shipments(val shipments: List<ShipmentListItem>) : State()
}
