package com.bintang.common_ui.extensions

import android.os.Build

/** return is device sdk version is over than 21 or not. */
fun checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
