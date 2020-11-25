package com.example.timer_kotlin.ui.main

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlin.concurrent.schedule
import com.example.timer_kotlin.R
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class Stopwatch : Fragment() {

    private lateinit var stopwatchModel: StopwatchModel

    private var time: Int = 0
        set(value) {
            field = if(value >= 0) value else 0
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopwatchModel = ViewModelProviders.of(this).get(StopwatchModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?  {
        val root = inflater.inflate(R.layout.stopwatch_main, container, false)
        val textView: TextView = root.findViewById(R.id.textView2)

        var isTimerRunning: Boolean = false
        var timer: TimerTask? = null

        val button: Button = root.findViewById(R.id.button6)
        button.setOnClickListener {
            if(!isTimerRunning){
                isTimerRunning = true
                timer?.cancel()
                timer = Timer("Stopwatch", true).schedule(0, 33) {
                    time += 33
                    textView.text = String.format(Locale.US, "%.3f s",time.toFloat()/1000)
                }

                button.text = "Stop"
            } else {
                button.text = "Start"
                isTimerRunning = false
                timer?.cancel()
                textView.text = String.format(Locale.US, "%.3f s",time.toFloat()/1000)
            }
        };
        val buttonReset: Button = root.findViewById(R.id.button7)
        buttonReset.setOnClickListener {
            time = 0
            textView.text =  "0 s"
        };




        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): Stopwatch {
            return Stopwatch().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}