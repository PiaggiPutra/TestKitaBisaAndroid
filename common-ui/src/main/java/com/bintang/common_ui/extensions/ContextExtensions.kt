
package com.bintang.common_ui.extensions

import android.content.Context
import android.widget.Toast

/** shows a toast message. */
fun Context.toast(message: String) =
  Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
