package com.bintang.common_ui.extensions

import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import com.bintang.common_ui.R

/** apply title to the toolbar simply. */
fun AppCompatActivity.simpleToolbarWithHome(toolbar: Toolbar, title_: String = "") {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    setDisplayHomeAsUpEnabled(true)
    setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    title = title_
  }
}

/** get a material container arc transform. */
fun AppCompatActivity.getContentTransform(): MaterialContainerTransform {
  return MaterialContainerTransform(this).apply {
    addTarget(android.R.id.content)
    duration = 400
    pathMotion = MaterialArcMotion()
  }
}

/** apply material exit container transformation. */
fun AppCompatActivity.applyExitMaterialTransform() {
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
  window.sharedElementsUseOverlay = false
}

/** apply material entered container transformation. */
fun AppCompatActivity.applyMaterialTransform(transitionName: String) {
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  ViewCompat.setTransitionName(findViewById<View>(android.R.id.content), transitionName)

  // set up shared element transition
  setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
  setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
  window.sharedElementEnterTransition = getContentTransform()
  window.sharedElementReturnTransition = getContentTransform()
}
