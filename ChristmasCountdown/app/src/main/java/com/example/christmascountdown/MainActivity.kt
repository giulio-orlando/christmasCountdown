package com.example.christmascountdown

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.LinearGradient
import android.graphics.Shader
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min
import kotlin.random.Random

class MainActivity : Activity() {
    private lateinit var countdown: ChristmasView
    private val handler = Handler(Looper.getMainLooper())
    private val refresh = object : Runnable {
        override fun run() {
            countdown.invalidate()
            handler.postDelayed(this, 30_000L)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(9, 39, 31)
        window.navigationBarColor = Color.rgb(9, 39, 31)
        countdown = ChristmasView()
        setContentView(countdown)
    }
    override fun onResume() { super.onResume(); handler.removeCallbacks(refresh); refresh.run() }
    override fun onPause() { handler.removeCallbacks(refresh); super.onPause() }

    private inner class ChristmasView : View(this) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val flakes = List(65) { i ->
            Triple(Random(i + 42).nextFloat(), Random(i + 100).nextFloat(), Random(i + 500).nextFloat())
        }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val w = width.toFloat(); val h = height.toFloat()
            val scale = min(w / 390f, h / 780f)
            canvas.drawColor(Color.rgb(9, 39, 31))
            paint.shader = LinearGradient(0f, 0f, 0f, h, Color.rgb(13, 67, 50), Color.rgb(6, 28, 27), Shader.TileMode.CLAMP)
            canvas.drawRect(0f, 0f, w, h, paint)
            paint.shader = null
            flakes.forEach { (x, y, r) ->
                paint.color = Color.argb(80 + (r * 100).toInt(), 235, 244, 226)
                canvas.drawCircle(x * w, y * h, (1.0f + r * 2.2f) * scale, paint)
            }
            fun txt(value: String, y: Float, size: Float, color: Int, bold: Boolean = false) {
                paint.color = color; paint.textAlign = Paint.Align.CENTER
                paint.typeface = if (bold) android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD) else android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.NORMAL)
                paint.textSize = size * scale
                canvas.drawText(value, w / 2f, y, paint)
            }
            val mid = h / 2f
            val gold = Color.rgb(246, 207, 124)
            txt("✦  ✧  ✦", mid - 230f * scale, 30f, gold)
            txt("CHRISTMAS COUNTDOWN", mid - 164f * scale, 19f, gold, true)
            val today = LocalDate.now()
            var christmas = LocalDate.of(today.year, 12, 25)
            if (today.isAfter(christmas)) christmas = christmas.plusYears(1)
            val days = ChronoUnit.DAYS.between(today, christmas)
            if (days == 0L) {
                txt("🎄", mid - 40f * scale, 74f, Color.WHITE)
                txt("Buon Natale!", mid + 45f * scale, 42f, Color.WHITE, true)
            } else {
                txt(days.toString(), mid + 15f * scale, 142f, Color.WHITE, true)
                txt(if (days == 1L) "giorno a Natale" else "giorni a Natale", mid + 68f * scale, 28f, Color.WHITE)
            }
            txt("25 DICEMBRE ${christmas.year}", mid + 155f * scale, 17f, gold, true)
            txt("La magia si avvicina ✦", mid + 230f * scale, 18f, Color.rgb(192, 220, 202))
        }
    }
}
