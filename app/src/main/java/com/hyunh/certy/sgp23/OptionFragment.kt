package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspOptionBinding

class OptionFragment : Fragment() {

    private val model: RspViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRspOptionBinding>(
                inflater, R.layout.fragment_rsp_option, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.layoutRoundList.recyclerview.setHasFixedSize(true)
        binding.layoutRoundList.recyclerview.adapter = OptionRvAdapter().apply {
            model.loadOptions().observe(viewLifecycleOwner, Observer {
                update(it)
            })
        }
        return binding.root
    }

    class OptionRvAdapter : RecyclerView.Adapter<OptionRvAdapter.ViewHolder>() {
        private val options: List<Rsp.Option> = mutableListOf()

        fun update(new: List<Rsp.Option>) {
            if (options is MutableList) {
                options.clear()
                options.addAll(new)
                notifyDataSetChanged()
            }
        }

        override fun getItemCount() = options.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.type.text = options[position].type
            holder.description.text = options[position].option
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_rsp_option, parent, false)
            view.setOnClickListener {
            }
            return ViewHolder(view)
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox_option)
            val type: AppCompatTextView = view.findViewById(R.id.tv_option_type)
            val description: AppCompatTextView = view.findViewById(R.id.tv_option_description)
        }
    }
}