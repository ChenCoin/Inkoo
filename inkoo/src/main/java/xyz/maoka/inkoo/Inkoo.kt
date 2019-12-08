package xyz.maoka.inkoo

import android.util.SparseArray
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

// useless
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
    }
}

interface InkooRaw {
    val layout: Int
    val bindView: HashMap<String, View>.(View) -> Unit
    val bindData: HashMap<String, View>.(Int) -> Unit
}

private class InkooContent(
    val type: Int,
    val bindData: HashMap<String, View>.(Int) -> Unit
)

private class InkooType(
    val layout: Int,
    val bindView: HashMap<String, View>.(View) -> Unit
)

private class InkooAdapter(
    var contentList: ArrayList<InkooContent>,
    var typeList: SparseArray<InkooType>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int = contentList[position].type

    override fun getItemCount(): Int = contentList.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) = InkooViewHolder(
        LayoutInflater.from(parent.context).inflate(typeList[type].layout, parent, false),
        typeList[type].bindView
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        contentList[position].bindData((holder as InkooViewHolder).map, position)
}

fun RecyclerView.create() {
    layoutManager = LinearLayoutManager(context)
    overScrollMode = View.OVER_SCROLL_NEVER
    adapter = InkooAdapter(ArrayList(), SparseArray())
}

fun RecyclerView.refresh(rawData: ArrayList<InkooRaw>) {
    val contentList = ArrayList<InkooContent>()
    val typeList = SparseArray<InkooType>()
    rawData.forEach {
        val type = it::class.java.name.hashCode()
        typeList[type] ?: typeList.put(type, InkooType(it.layout, it.bindView))
        contentList.add(InkooContent(type, it.bindData))
    }
    val inkooAdapter = (adapter as InkooAdapter)
    inkooAdapter.contentList = contentList
    inkooAdapter.typeList = typeList
    inkooAdapter.notifyDataSetChanged()
}
