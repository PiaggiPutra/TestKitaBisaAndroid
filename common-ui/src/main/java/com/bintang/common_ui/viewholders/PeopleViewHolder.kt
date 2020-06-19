package com.bintang.common_ui.viewholders

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.bintang.common_ui.databinding.ItemPersonBinding
import com.bintang.entity.entities.Person
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleViewHolder(
  view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

  interface Delegate {
    fun onItemClick(person: Person, view: View)
  }

  private lateinit var person: Person
  private val binding by bindings<ItemPersonBinding>(view)

  override fun bindData(data: Any) {
    if (data is Person) {
      person = data
      binding.apply {
        person = data
        executePendingBindings()
      }
    }
  }

  override fun onClick(p0: View?) =
    delegate.onItemClick(person, itemView.item_person_profile)

  override fun onLongClick(p0: View?) = false
}
