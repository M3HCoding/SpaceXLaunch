package com.spacexlaunch.android.ui.store

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.spacexlaunch.android.databinding.FragmentStoreBinding
import com.spacexlaunch.android.utils.CustomProgressDialog

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var customProgressDialog: CustomProgressDialog
    private var toolbarTitle = "Store"
    private var webViewUrl = "https://www.spacex.com/vehicles/falcon-9/"
    private var isWebViewLoaded = false // Track if the WebView has loaded

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customProgressDialog = CustomProgressDialog(requireContext())
        binding.toolbarLayout.toolbarBackButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.toolbarLayout.toolbarTitle.text = toolbarTitle

        // Initialize WebView state
        binding.webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    return if (url.startsWith("http://") || url.startsWith("https://")) {
                        false // Load URL in WebView
                    } else {
                        // URL is external, open in default browser
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    }
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                customProgressDialog.dismiss()
                isWebViewLoaded = true // Mark as loaded
            }
        }

        // Restore WebView state
        savedInstanceState?.let {
            binding.webView.restoreState(it)
            isWebViewLoaded = true
        }

        if (!isWebViewLoaded) {
            loadUrl(webViewUrl)
        }
    }

    private fun loadUrl(url: String) {
        // Show progress dialog
        customProgressDialog.show()

        // Load URL
        binding.webView.loadUrl(url)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState) // Save WebView state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
