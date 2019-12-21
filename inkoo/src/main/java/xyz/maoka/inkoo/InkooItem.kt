package xyz.maoka.inkoo

import android.view.View
import android.widget.TextView
import androidx.core.view.setPadding

// !"" means title
operator fun String.not() = data.add(Title(this))

class Title(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_title
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

// +"" means content
operator fun String.unaryPlus() = data.add(Text(this))

class Text(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_text
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

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

operator fun Button.unaryMinus() {
    event = {
        data.clear()
        tap()
        refresh()
    }
    data.add(this)
}

operator fun Button.unaryPlus() {
    event = {
        val exist = data.filter { it !is Button }
        data.clear()
        data.addAll(exist)
        tap()
        refreshTo(exist.size)
    }
    data.add(this)
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

val Int.dp get() = data.add(Padding(this))

// 
