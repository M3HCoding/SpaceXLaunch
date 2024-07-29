package com.spacexlaunch.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.spacexlaunch.android.R
import com.spacexlaunch.android.data.BookMarkModel
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.databinding.FragmentHomeBinding
import com.spacexlaunch.android.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null
    private lateinit var customProgressDialog: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customProgressDialog = CustomProgressDialog(requireContext())

        setupAdapter()
        initRecyclerView()
        observeLoadingState()
        getList("")
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter(object : HomeAdapter.ShipListListener {
            override fun onItemClick(item: Int?) {
                navigateToDestination(item)
            }

            override fun onBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?) {
                handleBookmarkClick(item, favorite, item1)
            }

            override fun isBookmark(item: Int?, favorite: ImageView) {
                checkIfBookmarked(item, favorite)
            }
        })
        homeAdapter?.setContext(requireContext())
    }

    private fun handleBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?) {
        lifecycleScope.launch(Dispatchers.IO) {
            val checkBookmark = item?.let { viewModel.getBookmark(it) }
            withContext(Dispatchers.Main) {
                if (checkBookmark != null) {
                    viewModel.deleteBookmark(checkBookmark)
                    favorite.isSelected = false
                } else {
                    item?.let {
                        val bookMarkModel = BookMarkModel(
                            flight_number = item1?.flight_number,
                            details = item1?.details,
                            is_tentative = item1?.is_tentative,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null

                        )
                        viewModel.insertBookmark(bookMarkModel)
                        favorite.isSelected = true
                    }
                }
            }
        }
    }

    private fun checkIfBookmarked(item: Int?, favorite: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            val checkBookmark = item?.let { viewModel.getBookmark(it) }
            withContext(Dispatchers.Main) {
                favorite.isSelected = checkBookmark != null
            }
        }
    }

    private fun navigateToDestination(item: Int?) {
        val bundle = Bundle().apply {
            item?.let { putInt("flightNumberKey", it) }
        }
        findNavController().navigate(R.id.action_home_to_DetailInformationFragment, bundle)
    }

    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collectLatest { isLoading ->
                    if (isLoading) {
                        customProgressDialog.show()
                    } else {
                        customProgressDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun getList(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMoviesListing(query).collectLatest {
                    homeAdapter?.submitData(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.layoutManager = linearLayoutManager
        binding.recyclerViewHome.adapter = homeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
