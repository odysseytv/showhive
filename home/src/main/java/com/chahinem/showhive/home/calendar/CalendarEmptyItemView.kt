package com.chahinem.showhive.home.calendar

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.chahinem.showhive.home.R
import javax.inject.Inject

class CalendarEmptyItemView {
  class Delegate @Inject constructor() : CalendarAdapter.Delegate {

    override fun layoutId() = R.layout.item_empty_calendar

    override fun create(parent: ViewGroup) = Holder(itemView(parent))

    override fun bind(item: CalendarAdapter.Item, holder: ViewHolder) {}
  }

  class Holder(itemView: View) : ViewHolder(itemView)

  class Item : CalendarAdapter.Item {
    override fun itemViewType() = CalendarAdapter.EMPTY
  }
}
