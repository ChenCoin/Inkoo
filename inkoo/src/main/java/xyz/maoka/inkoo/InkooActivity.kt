package xyz.maoka.inkoo

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InkooActivity : AppCompatActivity(), Ink {

    abstract fun main()

    override lateinit var recyclerView: RecyclerView

    override val activity = this

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

    private val animTime: Long = 300

    private fun startAnim(start: Float, end: Float) {
        val anim = ObjectAnimator.ofFloat(foreground, "alpha", start, end)
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.setDuration(animTime).start()
    }

    private fun animate(it: () -> Unit) {
        foreground.visibility = View.VISIBLE
        startAnim(0F, 1F)
        Handler().postDelayed({
            it()
            startAnim(1F, 0F)
            Handler().postDelayed({ foreground.visibility = View.GONE }, animTime)
        }, animTime)
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