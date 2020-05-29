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
import com.hyunh.certy.databinding.FragmentRspConditionBinding
import com.hyunh.certy.databinding.LayoutRspConditionBinding

class ConditionFragment : Fragment() {

    private val model: RspViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRspConditionBinding>(
                inflater, R.layout.fragment_rsp_condition, container, false).apply {
            layoutRoundList.recyclerview.setHasFixedSize(true)
            layoutRoundList.recyclerview.adapter = ConditionRvAdapter().apply {
                model.loadConditions().observe(viewLifecycleOwner, Observer {
                    update(it)
                })
            }
        }
        return binding.root
    }

    class ConditionRvAdapter : RecyclerView.Adapter<ConditionRvAdapter.ViewHolder>() {

        private val items: List<Rsp.Condition> = mutableListOf()

        fun update(new: List<Rsp.Condition>) {
            if (items is MutableList) {
                items.clear()
                items.addAll(new)
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DataBindingUtil.inflate<LayoutRspConditionBinding>(
                    LayoutInflater.from(parent.context), R.layout.layout_rsp_condition, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        class ViewHolder(
                private val binding: LayoutRspConditionBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Any) {
            }
        }
    }
}