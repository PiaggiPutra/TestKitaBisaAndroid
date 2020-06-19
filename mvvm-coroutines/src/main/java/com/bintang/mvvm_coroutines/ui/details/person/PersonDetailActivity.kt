package com.bintang.mvvm_coroutines.ui.details.person

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.observe
import com.bintang.common_ui.extensions.checkIsMaterialVersion
import com.bintang.common_ui.extensions.toast
import com.bintang.mvvm_coroutines.R
import com.bintang.mvvm_coroutines.base.DatabindingActivity
import com.bintang.mvvm_coroutines.databinding.ActivityPersonDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailActivity : DatabindingActivity() {

  private val viewModel: PersonDetailViewModel by viewModel()
  private val binding: ActivityPersonDetailBinding by binding(R.layout.activity_person_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // post the person id from intent
    viewModel.postPersonId(intent.getIntExtra(person, 0))
    // binding data into layout view
    with(binding) {
      lifecycleOwner = this@PersonDetailActivity
      activity = this@PersonDetailActivity
      viewModel = this@PersonDetailActivity.viewModel
      person = this@PersonDetailActivity.viewModel.getPerson()
    }
    // observe error messages
    observeMessages()
  }

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(this) { toast(it) }

  companion object {
    const val person = "person"
    private const val intent_requestCode = 1000

    fun startActivity(activity: Activity?, personId: Int, view: View) {
      if (activity != null) {
        val intent = Intent(activity, PersonDetailActivity::class.java)
        intent.putExtra(person, personId)
        if (checkIsMaterialVersion()) {
          ViewCompat.getTransitionName(view)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
            activity.startActivityForResult(intent, intent_requestCode, options.toBundle())
          }
        } else {
          activity.startActivity(intent)
        }
      }
    }
  }
}
