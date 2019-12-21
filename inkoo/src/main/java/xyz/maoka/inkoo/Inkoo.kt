package xyz.maoka.inkoo

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

val data = ArrayList<InkooRaw>()

var refresh: () -> Unit = {}

var refreshTo: (Int) -> Unit = {}

interface InkooRaw {
    val layout: Int
    val bindView: HashMap<String, View>.(View) -> Unit
    val bindData: HashMap<String, View>.(Int) -> Unit
}

private class InkooViewHolder(
    view: View,
    bindView: HashMap<String, View>.(View) -> Unit,
    var map: HashMap<String, View> = HashMap()
) : RecyclerView.ViewHolder(view) {
    init {
        bindView(map, view)
    }
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

fun RecyclerView.refresh() {
    val contentList = ArrayList<InkooContent>()
    val typeList = SparseArray<InkooType>()
    data.forEach {
        val type = it::class.java.name.hashCode()
        typeList[type] ?: typeList.put(type, InkooType(it.layout, it.bindView))
        contentList.add(InkooContent(type, it.bindData))
    }
    val inkooAdapter = (adapter as InkooAdapter)
    inkooAdapter.contentList = contentList
    inkooAdapter.typeList = typeList
    inkooAdapter.notifyDataSetChanged()
}

