package com.spacexlaunch.android.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.databinding.FragmentDetailedInformationBinding
import com.spacexlaunch.android.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailedInformationFragment : Fragment() {

    private var _binding: FragmentDetailedInformationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailedInformationViewModel by viewModels()
    private lateinit var customProgressDialog: CustomProgressDialog
    private val toolbarTitle = "Detailed Information"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customProgressDialog = CustomProgressDialog(requireContext())

        binding.toolbarLayout.toolbarBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.toolbarLayout.toolbarTitle.text = toolbarTitle

        setupInitView()
    }

    private fun setupInitView() {
        val flightNumber = arguments?.getInt("flightNumberKey")
        flightNumber?.let {
            lifecycleScope.launch {
                try {
                    customProgressDialog.show()
                    val item = withContext(Dispatchers.IO) { viewModel.getDetails(it) }
                    bindDataToViews(item)
                } catch (e: Exception) {
                    // Handle exception (e.g., show a Toast or an error message)
                } finally {
                    customProgressDialog.dismiss()
                }
            }
        }
    }

    private fun bindDataToViews(item: XModel?) {
        binding.apply {
            missionName.text = item?.mission_name ?: "N/A"
            launchDate.text = item?.launch_date_local ?: "N/A"
            launchSite.text = item?.launch_site?.site_name ?: "N/A"
            rocketName.text = item?.rocket?.rocket_name ?: "N/A"
            rockettype.text = item?.rocket?.rocket_type ?: "N/A"
            val payload = item?.rocket?.second_stage?.payloads?.getOrNull(0)
            payloadId.text = payload?.payload_id ?: "N/A"
            payloadCountry.text = payload?.payload_mass_kg?.toString() ?: "N/A"
            link.text = item?.links?.article_link ?: "N/A"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
