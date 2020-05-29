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

class TerminalProfileFragment : Fragment() {
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
        recyclerview.adapter = TerminalProfileRvAdapter(
                Usat.specs[Usat.current]!!.terminalProfiles)

         */
    }

    class TerminalProfileRvAdapter(
            private val terminalProfiles: List<Usat.TerminalProfile>
    ) : RecyclerView.Adapter<TerminalProfileRvAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_usat_terminal_profile, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = terminalProfiles.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.id.text = terminalProfiles[position].id
            holder.byte.text = terminalProfiles[position].byte
            holder.title.text = terminalProfiles[position].tp
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox_option)
            val id: AppCompatTextView = view.findViewById(R.id.tv_tp_id)
            val byte: AppCompatTextView = view.findViewById(R.id.tv_tp_byte)
            val title: AppCompatTextView = view.findViewById(R.id.tv_tp_title)
        }
    }
}