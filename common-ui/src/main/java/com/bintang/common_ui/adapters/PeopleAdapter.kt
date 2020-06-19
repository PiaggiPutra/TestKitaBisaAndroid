
package com.bintang.common_ui.adapters

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.bintang.common_ui.R
import com.bintang.common_ui.viewholders.PeopleViewHolder
import com.bintang.entity.entities.Person

/** PeopleAdapter is an adapter class for binding [Person] items. */
class PeopleAdapter(
  private val delegate: PeopleViewHolder.Delegate
) : BaseAdapter() {

  init {
    addSection(ArrayList<Person>())
  }

  fun addPeople(people: List<Person>) {
    sections()[0].addAll(people)
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_person

  override fun viewHolder(layout: Int, view: View) = PeopleViewHolder(view, delegate)
}
