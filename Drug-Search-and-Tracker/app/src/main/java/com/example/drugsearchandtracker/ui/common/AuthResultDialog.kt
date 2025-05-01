package com.example.drugsearchandtracker.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.DialogAuthResultBinding

class AuthResultDialog(private val context: Context) {

    fun showSuccess(
        title: String,
        message: String,
        buttonText: String = "OK",
        onDismiss: () -> Unit = {}
    ) {
        showDialog(
            title = title,
            message = message,
            buttonText = buttonText,
            isSuccess = true,
            onDismiss = onDismiss
        )
    }

    fun showError(
        title: String,
        message: String,
        buttonText: String = "Try Again",
        onDismiss: () -> Unit = {}
    ) {
        showDialog(
            title = title,
            message = message,
            buttonText = buttonText,
            isSuccess = false,
            onDismiss = onDismiss
        )
    }

    private fun showDialog(
        title: String,
        message: String,
        buttonText: String,
        isSuccess: Boolean,
        onDismiss: () -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        
        val binding = DialogAuthResultBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        
        // Set dialog properties
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCancelable(false)
        
        // Set content
        binding.apply {
            statusIcon.setImageResource(
                if (isSuccess) R.drawable.ic_success else R.drawable.ic_error
            )
            titleText.text = title
            messageText.text = message
            actionButton.apply {
                text = buttonText
                setBackgroundColor(
                    context.getColor(
                        if (isSuccess) R.color.blue else R.color.red
                    )
                )
                setOnClickListener {
                    dialog.dismiss()
                    onDismiss()
                }
            }
        }
        
        dialog.show()
    }
} 