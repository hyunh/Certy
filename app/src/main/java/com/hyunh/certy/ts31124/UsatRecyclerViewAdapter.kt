package com.hyunh.certy.ts31124

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import kotlinx.android.synthetic.main.layout_usat_option.view.*

class UsatRecyclerViewAdapter(
        private val listener: ((Int) -> Unit)?
) : RecyclerView.Adapter<UsatRecyclerViewAdapter.ViewHolder>() {

    private val list = mutableListOf<Usat.Option>().apply {
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener { listener?.invoke(position) }
        holder.id.text = list[position].id
        holder.title.text   = list[position].title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_usat_option, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val id = view.tv_option_id!!
        val title = view.tv_option_title!!
    }
}