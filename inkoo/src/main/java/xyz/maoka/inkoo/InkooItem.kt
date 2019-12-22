package xyz.maoka.inkoo

import android.view.View
import android.widget.TextView
import androidx.core.view.setPadding

// !"" means title
class Title(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_title
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

fun title(ink: Ink, content: String) = ink.data.add(Title(content))

// +"" means content
class Text(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_text
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

fun text(ink: Ink, content: String) = ink.data.add(Text(content))

// -""{} means button
// +""{} means button with origin text
class Button(content: String, val tap: () -> Unit) : InkooRaw {
    var event = {}
    override val layout: Int = R.layout.item_button
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        val text = (this["text"] as TextView)
        text.text = content
        text.setOnClickListener { event() }
    }
}

operator fun String.invoke(event: () -> Unit) = Button(this, event)

fun button(ink: Ink, item: Button) {
    item.event = {
        ink.data.clear()
        item.tap()
        ink.refresh()
    }
    ink.data.add(item)
}

fun continueButton(ink: Ink, item: Button) {
    item.event = {
        val exist = ink.data.filter { it !is Button }
        ink.data.clear()
        ink.data.addAll(exist)
        item.tap()
        ink.refreshTo(exist.size)
    }
    ink.data.add(item)
}

// 16.dp() means 16dp padding
class Padding(value: Int) : InkooRaw {
    override val layout: Int = R.layout.item_padding
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        val padding = (value * it.context.resources.displayMetrics.density / 2).toInt()
        it.findViewById<View>(R.id.view).setPadding(padding)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {}
}

fun padding(ink: Ink, item: Int) {
    ink.data.add(Padding(item))
}
