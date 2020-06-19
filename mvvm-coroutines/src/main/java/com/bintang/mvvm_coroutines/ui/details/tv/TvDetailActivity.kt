package com.bintang.mvvm_coroutines.ui.details.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import com.bintang.common_ui.extensions.toast
import com.bintang.mvvm_coroutines.R
import com.bintang.mvvm_coroutines.base.DatabindingActivity
import com.bintang.mvvm_coroutines.databinding.ActivityTvDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TvDetailActivity : DatabindingActivity() {

  private val viewModel: TvDetailViewModel by viewModel()
  private val binding: ActivityTvDetailBinding by binding(R.layout.activity_tv_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // post the tv id from intent
    viewModel.postTvId(intent.getIntExtra(tv, 0))
    // binding data into layout view
    with(binding) {
      lifecycleOwner = this@TvDetailActivity
      activity = this@TvDetailActivity
      viewModel = this@TvDetailActivity.viewModel
      tv = this@TvDetailActivity.viewModel.getTv()
    }
    // observe error messages
    observeMessages()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(this) { toast(it) }

  companion object {
    private const val tv = "tv"
    fun startActivityModel(context: Context?, tvId: Int) {
      val intent = Intent(context, TvDetailActivity::class.java).apply { putExtra(tv, tvId) }
      context?.startActivity(intent)
    }
  }
}
