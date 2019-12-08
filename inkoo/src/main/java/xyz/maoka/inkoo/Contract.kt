package xyz.maoka.inkoo

import androidx.recyclerview.widget.RecyclerView

interface InkooView {
    fun recyclerView(): RecyclerView

    fun close(next: () -> Unit)

    fun open()
}