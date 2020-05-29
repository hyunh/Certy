package com.hyunh.certy.ts31124

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import kotlinx.android.synthetic.main.fragment_spec.*

class OptionFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = OptionRvAdapter(Usat.specs[Usat.current]!!.options)

         */
    }

    class OptionRvAdapter(
            private val options: List<Usat.Option>
    ) : RecyclerView.Adapter<OptionRvAdapter.ViewHolder>() {

        private val selected = MutableList(options.size) { false }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_usat_option, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = options.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.setOnClickListener {
                selected[position] = !selected[position]
                notifyItemChanged(position)
            }
            holder.checkbox.isChecked = selected[position]
            holder.id.text = options[position].id
            holder.title.text = options[position].title
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox_option)
            val id: AppCompatTextView = view.findViewById(R.id.tv_option_id)
            val title: AppCompatTextView = view.findViewById(R.id.tv_option_title)
        }
    }
}