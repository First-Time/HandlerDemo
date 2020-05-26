package com.lyf.hanlder.subtosub

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lyf.hanlder.R
import kotlinx.android.synthetic.main.activity_sub_to_sub.*

class SubToSubActivity : AppCompatActivity() {

    private lateinit var subHandler1: Handler
    private lateinit var subHandler2: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_to_sub)

        initListener()
        createSubThread()
    }

    private fun initListener() {
        btnSubToSub.setOnClickListener {
            subThreadSendMessageToSubThread()
        }
    }

    private fun createSubThread() {
        val subThread1 = Thread {
            Looper.prepare()
            subHandler1 = Handler {
                when (it.what) {
                    0x100 -> {
                        println("subThread1 接收到消息")
                        Toast.makeText(
                            this@SubToSubActivity,
                            "subThread1 接收到消息",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        var message = subHandler1.obtainMessage(0x111)
                        subHandler2.sendMessageDelayed(message, 3000)
                    }
                }
                false
            }
            Looper.loop()
        }
        subThread1.start()

        val subThread2 = Thread {
            Looper.prepare()
            subHandler2 = Handler {
                when (it.what) {
                    0x111 -> {
                        println("subThread2 接收到消息")
                        Toast.makeText(
                            this@SubToSubActivity,
                            "subThread2 接收到消息",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        var message = subHandler2.obtainMessage(0x100)
                        subHandler1.sendMessageDelayed(message, 3000)
                    }
                }
                false
            }
            Looper.loop()
        }
        subThread2.start()
    }

    private fun subThreadSendMessageToSubThread() {
        var message = subHandler2.obtainMessage(0x100)
        subHandler1.sendMessageDelayed(message, 3000)
    }
}
