package xyz.maoka.inkoo

import android.R.menu
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


abstract class InkooActivity : AppCompatActivity(), InkooView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foreground: View
    private val animatorTime = 360L

    abstract fun main()

    override fun recyclerView(): RecyclerView = recyclerView

    override fun close(next: () -> Unit) {
        ObjectAnimator.ofFloat(foreground, "alpha", 0F, 0.6F)
            .setDuration(animatorTime).start();
        Handler().postDelayed({ next() }, animatorTime)
    }

    override fun open() {
        ObjectAnimator.ofFloat(foreground, "alpha", 0.6F, 0F)
            .setDuration(animatorTime).start();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inkoo)
        recyclerView = findViewById(R.id.recyclerView)
        foreground = findViewById(R.id.foreground)
        recyclerView.create()
        main()
    }
}