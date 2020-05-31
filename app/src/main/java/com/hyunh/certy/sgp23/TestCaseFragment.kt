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
import com.hyunh.certy.databinding.FragmentRspTestcaseBinding
import com.hyunh.certy.databinding.LayoutRspTestcaseBinding

class TestCaseFragment : Fragment() {

    private val model: RspViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRspTestcaseBinding>(
                inflater, R.layout.fragment_rsp_testcase, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.layoutRoundList.recyclerview.setHasFixedSize(true)
        binding.layoutRoundList.recyclerview.adapter = TestCaseRvAdapter().apply {
            model.loadTestCases().observe(viewLifecycleOwner, Observer {
                update(it)
            })
        }
        return binding.root
    }

    class TestCaseRvAdapter : RecyclerView.Adapter<TestCaseRvAdapter.ViewHolder>() {

        private val items: List<Rsp.TestCase> = mutableListOf()

        fun update(new: List<Rsp.TestCase>) {
            if (items is MutableList) {
                items.clear()
                items.addAll(new)
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DataBindingUtil.inflate<LayoutRspTestcaseBinding>(
                    LayoutInflater.from(parent.context), R.layout.layout_rsp_testcase, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        class ViewHolder(
                private val binding: LayoutRspTestcaseBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Rsp.TestCase) {
                binding.tvTestcaseId.text = item.id
                binding.tvTestcaseName.text = item.name
            }
        }
    }
}