package xyz.maoka.inkoo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InkooViewHolder(
    view: View,
    bindView: HashMap<String, View>.(View) -> Unit,
    var map: HashMap<String, View> = HashMap()
) : RecyclerView.ViewHolder(view) {
    init {
        bindView(map, view)
    }
}

class InkooBind(
    var layout: Int = 0,
    var size: () -> Int = { 0 },
    var bindView: HashMap<String, View>.(View) -> Unit = {},
    var bindData: HashMap<String, View>.(Int) -> Unit = {}
)

fun RecyclerView.create(initial: InkooBind.() -> Unit) {
    layoutManager = LinearLayoutManager(context)
    val bind = InkooBind()
    initial(bind)
    adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, type: Int) = InkooViewHolder(
            LayoutInflater.from(parent.context).inflate(bind.layout, parent, false),
            bind.bindView
        )

        override fun getItemCount(): Int = bind.size()

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            bind.bindData((holder as InkooViewHolder).map, position)
    };
}

