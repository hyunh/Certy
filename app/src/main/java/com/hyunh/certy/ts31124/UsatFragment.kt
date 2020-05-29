package com.hyunh.certy.ts31124

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hyunh.certy.R
import kotlinx.android.synthetic.main.fragment_usat.*

class UsatFragment : Fragment() {
    companion object {
        private const val TAB_OPTION = 0
        private const val TAB_TERMINAL_PROFILE = 1
        private const val TAB_TEST = 2
        private const val TAB_COUNT = 3

        private const val OPTION = "Option"
        private const val TEST = "Test"
        private const val TERMINAL_PROFILE = "Terminal Profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            Usat.load(it).also {
                Usat.current = arguments?.getString("REL") ?: ""
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        context?.let {
            if (it is AppCompatActivity) {
                it.supportActionBar?.title = arguments?.getString("TITLE") ?: getString(R.string.app_name)
                it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
        return inflater.inflate(R.layout.fragment_usat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.let {
            pager.adapter = ViewPagerAdapter(it)
        }
        tab_layout.setupWithViewPager(pager)
    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int) = when(position) {
            TAB_OPTION -> OptionFragment()
            TAB_TERMINAL_PROFILE -> TerminalProfileFragment()
            TAB_TEST -> TestFragment()
            else -> throw IllegalStateException()
        }

        override fun getCount() = TAB_COUNT

        override fun getPageTitle(position: Int): CharSequence? = when(position) {
            TAB_OPTION -> OPTION
            TAB_TERMINAL_PROFILE -> TERMINAL_PROFILE
            TAB_TEST -> TEST
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}