package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private fun refreshData() = viewModelScope.launch {
        shipmentRepository.shipments().collect {
            _state.value = State.Shipments(it)
        }
    }

    fun archive(shipmentDomain: ShipmentDomain) = viewModelScope.launch {
        if (shipmentDomain.archived) {
            shipmentRepository.unarchive(shipmentDomain)
        } else {
            shipmentRepository.archive(shipmentDomain)
        }
    }

    fun refresh() {
        refreshData()
    }
}

sealed class State {
    object Loading : State()

    data class Shipments(val shipments: List<ShipmentDomain>) : State()
}
