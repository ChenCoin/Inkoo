package xyz.maoka.inkoo

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InkooActivity : AppCompatActivity() {

    abstract fun main()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inkoo)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val foreground: View = findViewById(R.id.foreground)
        data.clear()
        val animate: (() -> Unit) -> Unit = {
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
        refresh = { animate { recyclerView.refresh() } }
        refreshTo = {
            animate {
                recyclerView.refresh()
                recyclerView.scrollToPosition(it)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(it, 0)
            }
        }
        recyclerView.create()
        main()
        recyclerView.refresh()
    }
}