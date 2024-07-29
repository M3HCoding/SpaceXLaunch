package com.spacexlaunch.android.dialogFragment.exit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.spacexlaunch.android.R
import com.spacexlaunch.android.databinding.DialogFragmentExitBinding

class ExitDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogFragmentExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialog()
        setListeners()
    }

    private fun setupDialog() {
        dialog?.window?.apply {
            setBackgroundDrawableResource(R.drawable.rounded_top_corner_bg)
            attributes = attributes?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                gravity = Gravity.BOTTOM
                horizontalMargin = 0f
                verticalMargin = 0f
            }
        }
    }

    private fun setListeners() {
        binding.dialogNo.setOnClickListener {
            dismiss()
        }

        binding.dialogYes.setOnClickListener {
            dismiss()
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
