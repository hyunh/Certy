package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspContentBinding
import com.hyunh.certy.databinding.LayoutRspOptionBinding

class OptionFragment : Fragment() {

    private val model: RspViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRspContentBinding>(
                inflater, R.layout.fragment_rsp_content, container, false)
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

        private val items: List<Rsp.Option> = mutableListOf()

        fun update(new: List<Rsp.Option>) {
            if (items is MutableList) {
                items.clear()
                items.addAll(new)
                notifyDataSetChanged()
            }
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DataBindingUtil.inflate<LayoutRspOptionBinding>(
                    LayoutInflater.from(parent.context), R.layout.layout_rsp_option, parent, false)
            return ViewHolder(binding)
        }

        class ViewHolder(
                private val binding: LayoutRspOptionBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Rsp.Option) {
                binding.tvOptionType.text = item.type
                binding.tvOptionDescription.text = item.option
            }
        }
    }
}