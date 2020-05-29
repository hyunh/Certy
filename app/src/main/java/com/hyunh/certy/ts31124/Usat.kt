package com.hyunh.certy.ts31124

import android.content.Context
import com.hyunh.certy.R
import org.xmlpull.v1.XmlPullParser

object Usat {
    data class Option(
            val id: String,
            val title: String,
            val status: String,
            val mnemonic: String
    )

    data class Condition(
            val id: String,
            val mnemonic: String
    ) {
        var isSet: Boolean = false
    }

    data class Sequence(
            val number: String,
            val description: String,
            val condition: String,
            val tp: String
    ) {
    }

    data class Test(
            val id: String,
            val number: String,
            val description: String,
            val sequences: MutableList<Sequence>
    ) {

    }

    data class TerminalProfile (
            val id: String,
            val byte: String,
            val tp: String,
            val status: String,
            val mnemoic: String
    )

    class Spec(val rel: String) {
        val options = mutableListOf<Option>()
        val conditions = mutableListOf<Condition>()
        val terminalProfiles = mutableListOf<TerminalProfile>()
        val tests = mutableListOf<Test>()

        fun getTestByOptions(cond: List<Option>): List<Test> {
            return mutableListOf()
        }
    }

    val specs = mutableMapOf<String, Spec>()
    var current = ""

    fun findTestCaseByOption() {
    }

    fun load(context: Context) {
        val parser = context.resources.getXml(R.xml.ts31124)
        var event = parser.next()

        var spec: Spec? = null
        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "ts31124" -> {
                            spec = Spec(parser.getAttributeValue(null, "rel")).also {
                                specs[it.rel] = it
                            }
                        }
                        "condition" -> { loadCondition(parser, spec!!.conditions) }
                        "table_a_1" -> { loadOption(parser, spec!!.options) }
                        "table_e_1" -> { loadTerminalProfile(parser, spec!!.terminalProfiles) }
                        "table_b_1" -> { loadTest(parser, spec!!.tests) }
                    }
                }
            }
            event = parser.next()
        }
    }

    private fun loadOption(
            parser: XmlPullParser,
            options: MutableList<Option>
    ) {
        while (true) {
            parser.next()
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    val id = parser.getAttributeValue(null, "id")
                    val option = parser.getAttributeValue(null, "option")
                    val status = parser.getAttributeValue(null, "status")
                    val mnemonic = parser.getAttributeValue(null, "mnemonic")
                    options.add(Option(id, option, status, mnemonic))
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name != "item") {
                        return
                    }
                }
            }
        }
    }

    private fun loadTerminalProfile(
            parser: XmlPullParser,
            terminalProfiles: MutableList<TerminalProfile>
    ) {
        while (true) {
            parser.next()
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    val id = parser.getAttributeValue(null, "id")
                    val byte = parser.getAttributeValue(null, "byte")
                    val tp = parser.getAttributeValue(null, "terminal_profile")
                    val status = parser.getAttributeValue(null, "status")
                    val mnemonic = parser.getAttributeValue(null, "mnemonic")
                    terminalProfiles.add(TerminalProfile(id, byte, tp, status, mnemonic))
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name != "item") {
                        return
                    }
                }
            }
        }
    }

    private fun loadCondition(
            parser: XmlPullParser,
            conditions: MutableList<Condition>
    ) {
        while (true) {
            parser.next()
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    val id = parser.getAttributeValue(null, "id")
                    val mnemonic = parser.getAttributeValue(null, "mnemonic")
                    conditions.add(Condition(id, mnemonic))
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name != "item") {
                        return
                    }
                }
            }
        }
    }

    private fun loadTest(
            parser: XmlPullParser,
            tests: MutableList<Test>
    ) {

    }
}