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
    private var dialog: Dialog? = null

    fun showSuccess(
        title: String,
        message: String,
        buttonText: String,
        onDismiss: () -> Unit
    ) {
        showDialog(true, title, message, buttonText, onDismiss)
    }

    fun showError(
        title: String,
        message: String,
        onDismiss: () -> Unit
    ) {
        showDialog(false, title, message, context.getString(R.string.lbl_ok), onDismiss)
    }

    private fun showDialog(
        isSuccess: Boolean,
        title: String,
        message: String,
        buttonText: String,
        onDismiss: () -> Unit
    ) {
        // Dismiss any existing dialog
        dialog?.dismiss()

        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)

            val binding = DialogAuthResultBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)

            // Set dialog properties
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
            setCancelable(false)

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
                        dismiss()
                        onDismiss()
                    }
                }
            }

            setOnDismissListener {
                dialog = null
            }
        }

        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}