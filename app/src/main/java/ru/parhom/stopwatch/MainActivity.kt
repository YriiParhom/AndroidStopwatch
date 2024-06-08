package ru.parhom.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    var OFFSET_KEY = "offset"
    var RUNNING_KEY = "running"
    var BASE_KAY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById(R.id.chronometer)

        if (savedInstanceState != null) {
          offset = savedInstanceState.getLong(OFFSET_KEY)
          running = savedInstanceState.getBoolean(RUNNING_KEY)
          if (running) {
              stopwatch.base = savedInstanceState.getLong(BASE_KAY)
              stopwatch.start()
          }  else setBaseTime()
        }

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        val resumeButton = findViewById<Button>(R.id.stop_button)
        resumeButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        if (running) {
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }

    override fun onPause() {
        if (running) {
            super.onPause()
            saveOffset()
            stopwatch.stop()
        }
    }

    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KAY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }
}