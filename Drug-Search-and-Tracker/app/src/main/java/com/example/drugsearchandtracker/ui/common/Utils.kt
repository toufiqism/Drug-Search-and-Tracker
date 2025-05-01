package com.example.drugsearchandtracker.ui.common

import android.graphics.Rect
import android.view.View
import android.widget.ScrollView

object Utils {
    fun setKeyboardListener(rootLayout: View, scrollableView: ScrollView) {
        rootLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootLayout.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootLayout.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                // Keyboard is opened
                scrollableView.setPadding(0, 0, 0, 20)
            } else {
                // Keyboard is closed
                scrollableView.setPadding(0, 0, 0, 0)
            }
        }
    }
}