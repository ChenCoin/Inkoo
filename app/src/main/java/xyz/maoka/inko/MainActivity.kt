package xyz.maoka.inko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.maoka.inkoo.InkooBind
import xyz.maoka.inkoo.InkooViewHolder
import xyz.maoka.inkoo.create

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recyclerView).create{
            layout = R.layout.item
            size = { 20 }
            bindView = {
                val text = it.findViewById<TextView>(R.id.text)
                this["text"] = text
            }
            bindData = {
                (this["text"] as TextView).text = "index $it"
            }
        }
    }

}
