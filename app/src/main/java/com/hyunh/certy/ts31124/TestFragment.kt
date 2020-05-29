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

class TestFragment : Fragment() {
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
        recyclerview.adapter = TestRvAdapter(Usat.specs[Usat.current]!!.tests)

         */
    }

    class TestRvAdapter(
            private val tests: List<Usat.Test>
    ) : RecyclerView.Adapter<TestRvAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_spec, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = tests.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.id.text = tests[position].id
            holder.description.text = tests[position].description
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox_text)
            val id: AppCompatTextView = view.findViewById(R.id.tv_test_id)
            val description: AppCompatTextView = view.findViewById(R.id.tv_test_description)
        }
    }

    class SequenceRvAdapter(
            private val sequences: List<Usat.Sequence>
    ) : RecyclerView.Adapter<SequenceRvAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_rel, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = sequences.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.number.text = sequences[position].number
            holder.description.text = sequences[position].description
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox_sequence)
            val number: AppCompatTextView = view.findViewById(R.id.tv_sequence_number)
            val description: AppCompatTextView = view.findViewById(R.id.tv_sequence_description)
        }
    }
}