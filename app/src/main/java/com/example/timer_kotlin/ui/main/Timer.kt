package com.example.timer_kotlin.ui.main

import android.content.Context
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timer_kotlin.R

/**
 * A placeholder fragment containing a simple view.
 */
class Timer : Fragment() {

    private lateinit var timerModel: TimerModel

    private var time: Int = 0
        set(value) {
            field = if(value >= 0) value else 0
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timerModel = ViewModelProviders.of(this).get(TimerModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.timer_main, container, false)
        val textView: TextView = root.findViewById(R.id.textView)


        fun renderTime(time: Int):String {
            val seconds = time / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            return "${hours%24} : ${minutes%60} : ${seconds%60}"
        }

        fun initButtons() {
            var button: Button = root.findViewById(R.id.button2)
            button.text = "-1s"
            button.setOnClickListener {
                time += -1000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button3)
            button.text = "+1s"
            button.setOnClickListener {
                time += 1000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button8)
            button.text = "-10s"
            button.setOnClickListener {
                time += -10000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button9)
            button.text = "+10s"
            button.setOnClickListener {
                time += 10000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button5)
            button.text = "-1min"
            button.setOnClickListener {
                time += -60000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button4)
            button.text = "+1min"
            button.setOnClickListener {
                time += 60000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button10)
            button.text = "-10min"
            button.setOnClickListener {
                time += -600000
                textView.text = renderTime(time)
            }

            button = root.findViewById(R.id.button11)
            button.text = "+10min"
            button.setOnClickListener {
                time += 600000
                textView.text = renderTime(time)
            }
        }
        initButtons()

        var isTimerRunning: Boolean = false
        var timer: CountDownTimer? = null
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val button: Button = root.findViewById(R.id.button)
        button.setOnClickListener {
            if(!isTimerRunning){
                isTimerRunning = true
                timer?.cancel()
                timer = object: CountDownTimer((time).toLong(), 100) {
                    override fun onTick(millisUntilFinished: Long) {
                        time = millisUntilFinished.toInt()
                        textView.text = renderTime(time)
                    }

                    override fun onFinish() {
                        time = 0
                        textView.text = "Time Is Up!"
                        if (vibrator.hasVibrator()) { // Vibrator availability checking
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                vibrator.vibrate(1000)
                            }
                        }
                    }
                }

                timer?.start()
                button.text = "Stop"
            } else {
                button.text = "Start"
                isTimerRunning = false
                timer?.cancel()
                textView.text = renderTime(time)
            }
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
        fun newInstance(sectionNumber: Int): Timer {
            return Timer().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}