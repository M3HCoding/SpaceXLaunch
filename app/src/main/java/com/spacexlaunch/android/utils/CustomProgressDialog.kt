package com.spacexlaunch.android.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.spacexlaunch.android.R
import com.spacexlaunch.android.databinding.DialogProgressBarBinding

class CustomProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(R.drawable.circle_background)
    }
}
