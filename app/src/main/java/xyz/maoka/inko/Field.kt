package xyz.maoka.inko

import xyz.maoka.inkoo.Ink
import xyz.maoka.inkoo.InkooActivity
import xyz.maoka.inkoo.invoke
import xyz.maoka.inkoo.title

class Field : InkooActivity(), InkField {
    override fun main() = field()
}

interface InkField : Ink {
    operator fun Int.not() = title(this@InkField, "$this")
}

fun InkField.field() {
    !2020
    -"回去"{ activity.finish() }
}