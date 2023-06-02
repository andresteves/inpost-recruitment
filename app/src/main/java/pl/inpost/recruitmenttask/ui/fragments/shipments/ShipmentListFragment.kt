package pl.inpost.recruitmenttask.ui.fragments.shipments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import pl.inpost.recruitmenttask.ui.fragments.shipments.adapter.ShipmentsAdapter
import pl.inpost.recruitmenttask.ui.common.VerticalSpacingDecorator
import pl.inpost.recruitmenttask.ui.dialogs.ShipmentActionDialog
import pl.inpost.recruitmenttask.extensions.collectWhenStarted
import pl.inpost.recruitmenttask.util.network.NetworkChanges

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var _binding: FragmentShipmentListBinding? = null
    private val binding get() = _binding!!

    private val shipmentAdapter = ShipmentsAdapter(::shipmentClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        refreshView.setOnRefreshListener {
            viewModel.refresh()
            refreshView.isRefreshing = false
        }

        with(shipmentsView) {
            adapter = shipmentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpacingDecorator(R.dimen.margin_sm))
        }

        toolbarView.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.allShipmentsMenu -> {
                    viewModel.filter(FilterType.ALL)
                    true
                }

                R.id.readyToPickupShipmentsMenu -> {
                    viewModel.filter(FilterType.READY_PICKUP)
                    true
                }

                else -> {
                    viewModel.filter(FilterType.OTHERS)
                    true
                }
            }
        }

        val networkWatcher = NetworkChanges(refreshView)
        networkWatcher.networkConnection.observe(viewLifecycleOwner) { available ->
            if (available) {
                viewModel.refresh()
            }
        }
        networkWatcher.checkInternetConnection(requireContext())
    }

    private fun initObservers() = with(binding) {
        viewModel.state.collectWhenStarted(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {}
                is State.Shipments -> {
                    emptyTextView.isVisible = state.shipments.isEmpty()
                    shipmentAdapter.submitList(state.shipments)
                }
            }
        }

        viewModel.event.collectWhenStarted(viewLifecycleOwner) { event ->
            when (event) {
                Event.Error -> {}
            }
        }
    }

    private fun shipmentClicked(shipmentDomain: ShipmentDomain) {
        /**
         *  no information on what to present so going to
         *  use a bottomsheetdialog to present a button to archive
         */
        ShipmentActionDialog.newInstance(object : ShipmentActionDialog.Companion.ActionListener {
            override fun archiveClick() {
                viewModel.archive(shipmentDomain.copy(archived = true))
            }
        }).show(childFragmentManager, "actionDialog")
    }

}


