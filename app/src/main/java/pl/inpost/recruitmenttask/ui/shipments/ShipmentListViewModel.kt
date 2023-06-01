package pl.inpost.recruitmenttask.ui.shipments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) : ViewModel() {

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    init {
        refreshData()
    }

    fun archive(shipmentDomain: ShipmentDomain) = viewModelScope.launch(Dispatchers.IO) {
        if (shipmentDomain.archived) {
            shipmentRepository.unarchive(shipmentDomain)
        } else {
            shipmentRepository.archive(shipmentDomain)
        }
    }

    fun refresh() {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch(Dispatchers.IO) {
        shipmentRepository.shipments().collect {
            _state.value = State.Shipments(it)
        }
    }

    private fun handleShipments() {

    }
}

sealed class State {
    object Loading : State()

    data class Shipments(val shipments: List<ShipmentDomain>) : State()
}
