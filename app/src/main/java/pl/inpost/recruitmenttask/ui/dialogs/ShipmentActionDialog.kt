package pl.inpost.recruitmenttask.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pl.inpost.recruitmenttask.databinding.FragmentActionsListDialogBinding

class ShipmentActionDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentActionsListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionListener: ActionListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActionsListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            archiveView.setOnClickListener {
                actionListener.archiveClick()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            listener: ActionListener
        ): ShipmentActionDialog = ShipmentActionDialog().apply {
            actionListener = listener
        }

        interface ActionListener {
            fun archiveClick()
        }
    }

}