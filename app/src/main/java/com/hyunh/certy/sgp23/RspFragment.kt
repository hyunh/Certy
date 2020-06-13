package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspBinding
import com.hyunh.certy.setSupportActionBar
import com.hyunh.certy.supportActionBar

class RspFragment : Fragment() {

    data class TabInfo(
            val fragment: Fragment,
            val title: String
    )

    private val tabInfos by lazy {
        listOf(
                TabInfo(OptionFragment(), getString(R.string.rsp_option)),
                TabInfo(ConditionFragment(), getString(R.string.rsp_condition)),
                TabInfo(TestCaseFragment(), getString(R.string.rsp_testcase))
        )
    }

    private val model: RspViewModel by viewModels()
    private val args by navArgs<RspFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {
        val binding = DataBindingUtil.inflate<FragmentRspBinding>(
                inflater, R.layout.fragment_rsp, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = model
            viewPager.adapter = RspPagerAdapter(this@RspFragment, tabInfos)
            toolbarTitle = "${args.name}  -  ${args.rel}"
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        buttonRspLeft.text = loadRspLeftButton(tabInfos[it].fragment)
                        buttonRspRight.text = loadRspRightButton(tabInfos[it].fragment)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
            buttonRspLeft.text = loadRspLeftButton(tabInfos[0].fragment)
            buttonRspRight.text = loadRspRightButton(tabInfos[0].fragment)
        }
        model.setVersion(args.rel)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = inflater.inflate(R.layout.layout_tab_text, container, false).also {
                if (it is TextView) {
                    it.text = tabInfos[position].title
                    it.setTextColor(resources.getColorStateList(R.color.tab_text_color,
                            requireContext().theme))
                    it.setBackgroundColor(android.R.attr.selectableItemBackground)
                }
            }
        }.attach()

        setHasOptionsMenu(true)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun loadRspLeftButton(fragment: Fragment): String {
        return when (fragment) {
            is OptionFragment -> getString(R.string.rsp_condition)
            is ConditionFragment -> getString(R.string.rsp_option)
            is TestCaseFragment -> getString(R.string.rsp_option)
            else -> throw IllegalStateException("Fragment can't be identified")
        }
    }

    private fun loadRspRightButton(fragment: Fragment): String {
        return when (fragment) {
            is OptionFragment -> getString(R.string.rsp_testcase)
            is ConditionFragment -> getString(R.string.rsp_testcase)
            is TestCaseFragment -> getString(R.string.rsp_condition)
            else -> throw IllegalStateException("Fragment can't be identified")
        }
    }

    class RspPagerAdapter(
            fragment: Fragment,
            private val tabInfos: List<TabInfo>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = tabInfos.size

        override fun createFragment(position: Int): Fragment {
            return tabInfos[position].fragment
        }
    }
}