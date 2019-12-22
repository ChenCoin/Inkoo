package xyz.maoka.inkoo

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface Ink {
    val data: ArrayList<InkooRaw>

    val recyclerView: RecyclerView

    val context: Context

    fun refresh()

    fun refreshTo(position: Int)

    operator fun String.not() = title(this@Ink, this)

    operator fun String.unaryPlus() = text(this@Ink, this)

    operator fun Button.unaryMinus() = button(this@Ink, this)

    operator fun Button.unaryPlus() = continueButton(this@Ink, this)

    val Int.dp get() = padding(this@Ink, this)
}

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
    var contents: ArrayList<InkooContent>,
    var types: SparseArray<InkooType>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int = contents[position].type

    override fun getItemCount(): Int = contents.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(types[type].layout, parent, false)
        return InkooViewHolder(view, types[type].bindView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        contents[position].bindData((holder as InkooViewHolder).map, position)
}

fun RecyclerView.create() {
    layoutManager = LinearLayoutManager(context)
    overScrollMode = View.OVER_SCROLL_NEVER
    adapter = InkooAdapter(ArrayList(), SparseArray())
}

fun Ink.notifyDataSetChanged() {
    val contentList = ArrayList<InkooContent>()
    val typeList = SparseArray<InkooType>()
    data.forEach {
        val type = it::class.java.name.hashCode()
        typeList[type] ?: typeList.put(type, InkooType(it.layout, it.bindView))
        contentList.add(InkooContent(type, it.bindData))
    }
    val inkooAdapter = (recyclerView.adapter as InkooAdapter)
    inkooAdapter.contents = contentList
    inkooAdapter.types = typeList
    inkooAdapter.notifyDataSetChanged()
}

