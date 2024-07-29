package com.spacexlaunch.android.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.spacexlaunch.android.data.BookMarkModel
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.databinding.FragmentSearchBinding
import com.spacexlaunch.android.ui.home.HomeAdapter
import com.spacexlaunch.android.ui.home.HomeViewModel
import com.spacexlaunch.android.utils.CustomProgressDialog
import com.spacexlaunch.android.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null
    private lateinit var customProgressDialog: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customProgressDialog = CustomProgressDialog(requireContext())

        binding.toolbarSearchLayout.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        homeAdapter = HomeAdapter(object : HomeAdapter.ShipListListener {
            override fun onItemClick(item: Int?) {
                // Handle item click
            }

            override fun onBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?) {
                item?.let { flightNumber ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        val checkBookmark = viewModel.getBookmark(flightNumber)
                        if (checkBookmark != null) {
                            viewModel.deleteBookmark(flightNumber)
                            favorite.isSelected = false
                        } else {
                            item1?.let { xModel ->
                                val bookMarkModel = BookMarkModel(
                                    xModel.flight_number,
                                    xModel.details,
                                    xModel.is_tentative,
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
                                    null, null, null, null, null, null, null, null, null, null

                                )
                                viewModel.insertBookmark(bookMarkModel)
                                favorite.isSelected = true
                            }
                        }
                    }
                }
            }

            override fun isBookmark(item: Int?, favorite: ImageView) {
                item?.let { flightNumber ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        val checkBookmark = viewModel.getBookmark(flightNumber)
                        favorite.isSelected = checkBookmark != null
                    }
                }
            }
        })

        initRecyclerView()

        binding.toolbarSearchLayout.textInputEditTextSearch.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                getList(s.toString())
            }
        })

        binding.toolbarSearchLayout.ibSearch.setOnClickListener {
            val queryText =
                binding.toolbarSearchLayout.textInputEditTextSearch.text?.toString()?.trim()
            if (!queryText.isNullOrEmpty()) {
                clearSearchEditText()
                Utility.hideKeyboard(
                    requireContext(),
                    binding.toolbarSearchLayout.textInputEditTextSearch
                )
                getList(queryText)
            } else {
                binding.toolbarSearchLayout.textInputEditTextSearch.hint = "Type to search"
            }
        }

        observeLoadingState()
        getList("")
    }

    private fun clearSearchEditText() {
        binding.toolbarSearchLayout.textInputEditTextSearch.text?.clear()
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
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchScrollView.adapter = homeAdapter
        binding.searchScrollView.layoutManager = linearLayoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
