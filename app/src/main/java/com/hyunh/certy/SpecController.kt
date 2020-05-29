package com.hyunh.certy

import org.xmlpull.v1.XmlPullParser

data class Spec(
    val name: String,
    val view: String,
    val rel: List<String>
)

object SpecController {
    private val spec = mutableListOf<Spec>()

    const val TITLE = "TITLE"
    const val REL = "REL"

    fun load() : List<Spec> {
        spec.clear()

        val parser = Certy.getXmlParser(R.xml.spec)
        var event = parser.next()

        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "item" -> {
                            val name = parser.getAttributeValue(null, "name")
                            val view = parser.getAttributeValue(null, "fragment")
                            val rels = loadRel(parser)

                            spec.add(Spec(name, view, rels))
                        }
                    }
                }
            }

            event = parser.next()
        }

        return spec
    }

    private fun loadRel(parser: XmlPullParser) : List<String> {
        val rels = mutableListOf<String>()
        loop@ while (true) {
            when (parser.next()) {
                XmlPullParser.START_TAG -> when (parser.name) {
                    "rel" -> rels.add(parser.getAttributeValue(null, "ver"))
                }
                XmlPullParser.END_TAG -> when (parser.name) {
                    "item" -> break@loop
                }
            }
            parser.next()
        }
        return rels
    }
}