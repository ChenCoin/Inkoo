package xyz.maoka.inkoo

class PageButton(val text: String, val onTap: () -> Unit)

class Page {
    val data = ArrayList<InkooRaw>()

    operator fun String.not() = data.add(Title(this))

    operator fun String.unaryPlus() = data.add(Text(this))

    operator fun PageButton.unaryMinus() = data.add(Button(text, onTap))

    operator fun String.invoke(onTap: () -> Unit): PageButton = PageButton(this, onTap)
}

fun InkooView.page(initial: Page.() -> Unit) {
    close {
        val data = Page()
        initial(data)
        recyclerView().refresh(data.data)
        open()
    }
}