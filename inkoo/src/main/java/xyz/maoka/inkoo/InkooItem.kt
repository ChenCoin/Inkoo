package xyz.maoka.inkoo

import android.view.View
import android.widget.TextView

class Title(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_title
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

class Text(content: String) : InkooRaw {
    override val layout: Int = R.layout.item_text
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        (this["text"] as TextView).text = content
    }
}

class Button(content: String, tap: () -> Unit) : InkooRaw {
    override val layout: Int = R.layout.item_button
    override val bindView: HashMap<String, View>.(View) -> Unit = {
        this["text"] = it.findViewById(R.id.text)
    }
    override val bindData: HashMap<String, View>.(Int) -> Unit = {
        val text = (this["text"] as TextView)
        text.text = content
        text.setOnClickListener { tap() }
    }
}