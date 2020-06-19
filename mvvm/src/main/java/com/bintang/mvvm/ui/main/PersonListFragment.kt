
package com.bintang.mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.bintang.common_ui.adapters.PeopleAdapter
import com.bintang.common_ui.extensions.toast
import com.bintang.common_ui.viewholders.PeopleViewHolder
import com.bintang.entity.entities.Person
import com.bintang.mvvm.R
import com.bintang.mvvm.base.ViewModelFragment
import com.bintang.mvvm.databinding.FragmentPeopleBinding
import com.bintang.mvvm.ui.details.person.PersonDetailActivity
import kotlinx.android.synthetic.main.fragment_people.recyclerView

class PersonListFragment : ViewModelFragment(), PeopleViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by injectActivityVIewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding<FragmentPeopleBinding>(
      inflater, R.layout.fragment_people, container).apply {
      viewModel = this@PersonListFragment.viewModel
      lifecycleOwner = this@PersonListFragment
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
    recyclerView.adapter = PeopleAdapter(this)
    RecyclerViewPaginator(
      recyclerView = recyclerView,
      isLoading = { viewModel.isLoading() },
      loadMore = { loadMore(it) },
      onLast = { false }
    ).apply {
      threshold = 4
      currentPage = 1
    }
  }

  private fun loadMore(page: Int) = this.viewModel.postPeoplePage(page)

  override fun onItemClick(person: Person, view: View) =
    PersonDetailActivity.startActivity(activity, person.id, view)

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(this) { context?.toast(it) }
}
