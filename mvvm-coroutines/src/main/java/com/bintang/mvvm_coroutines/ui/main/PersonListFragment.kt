package com.bintang.mvvm_coroutines.ui.main

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
import com.bintang.mvvm_coroutines.R
import com.bintang.mvvm_coroutines.base.DatabindingFragment
import com.bintang.mvvm_coroutines.databinding.FragmentPeopleBinding
import com.bintang.mvvm_coroutines.ui.details.person.PersonDetailActivity
import kotlinx.android.synthetic.main.fragment_people.recyclerView
import org.koin.android.viewmodel.ext.android.viewModel

class PersonListFragment : DatabindingFragment(), PeopleViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding<FragmentPeopleBinding>(
      inflater, R.layout.fragment_people, container).apply {
      viewModel = this@PersonListFragment.viewModel
      lifecycleOwner = this@PersonListFragment
      adapter = PeopleAdapter(this@PersonListFragment)
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
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
