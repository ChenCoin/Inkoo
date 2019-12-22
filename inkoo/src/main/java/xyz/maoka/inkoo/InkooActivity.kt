package xyz.maoka.inkoo

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InkooActivity : AppCompatActivity(), Ink {

    abstract fun main()

    override lateinit var recyclerView: RecyclerView

    override val context: Context = this

    private lateinit var foreground: View

    override val data = ArrayList<InkooRaw>()

    override fun refresh() {
        animate { notifyDataSetChanged() }
    }

    override fun refreshTo(position: Int) {
        animate {
            notifyDataSetChanged()
            recyclerView.scrollToPosition(position)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    private fun animate(it: () -> Unit) {
        foreground.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(foreground, "alpha", 0F, 0.6F)
            .setDuration(800).start()
        Handler().postDelayed({
            it()
            ObjectAnimator.ofFloat(foreground, "alpha", 0.6F, 0F)
                .setDuration(400).start();
            Handler().postDelayed({ foreground.visibility = View.GONE }, 400)
        }, 800)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inkoo)
        recyclerView = findViewById(R.id.recyclerView)
        foreground = findViewById(R.id.foreground)
        data.clear()
        recyclerView.create()
        main()
        notifyDataSetChanged()
    }
}