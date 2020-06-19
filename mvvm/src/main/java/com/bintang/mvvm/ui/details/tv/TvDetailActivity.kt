package com.bintang.mvvm.ui.details.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import com.bintang.common_ui.extensions.toast
import com.bintang.mvvm.R
import com.bintang.mvvm.base.ViewModelActivity
import com.bintang.mvvm.databinding.ActivityTvDetailBinding

class TvDetailActivity : ViewModelActivity() {

  private val vm: TvDetailViewModel by injectViewModels()
  private val binding: ActivityTvDetailBinding by binding(R.layout.activity_tv_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // post the tv id from intent
    vm.postTvId(intent.getIntExtra(tv, 0))
    // binding data into layout view
    with(binding) {
      lifecycleOwner = this@TvDetailActivity
      activity = this@TvDetailActivity
      viewModel = vm
      tv = vm.getTv()
    }
    // observe error messages
    observeMessages()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  private fun observeMessages() =
    this.vm.toastLiveData.observe(this) { toast(it) }

  companion object {
    private const val tv = "tv"
    fun startActivityModel(context: Context?, tvId: Int) {
      val intent = Intent(context, TvDetailActivity::class.java)
      intent.putExtra(tv, tvId)
      context?.startActivity(intent)
    }
  }
}
