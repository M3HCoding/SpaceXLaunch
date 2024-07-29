package com.spacexlaunch.android.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText

object Utility {
    @JvmStatic
    fun hideKeyboard(context: Context, textInputEditTextSearch: TextInputEditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(textInputEditTextSearch.windowToken, 0)
    }
}