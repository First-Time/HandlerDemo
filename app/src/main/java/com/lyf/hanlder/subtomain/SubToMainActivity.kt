package com.lyf.hanlder.subtomain

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.Message
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lyf.hanlder.R
import kotlinx.android.synthetic.main.activity_sub_to_main.*
import java.lang.ref.WeakReference

class SubToMainActivity : AppCompatActivity() {
    private var count = 0

    /*private val myHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x100 -> {
                    Toast.makeText(this@SubSendMessageToMainActivity, "收到消息", Toast.LENGTH_LONG)
                        .show()
                    println("收到消息")
                }
            }
        }
    }

    private class MyHandler(val context : Context) : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x100 -> {
                    Toast.makeText(context, "收到消息", Toast.LENGTH_LONG)
                        .show()
                    println("收到消息")
                }
            }
        }
    }*/

    private val myHandler = MyHandler(WeakReference(this))

    //不添加static修饰符的时候，如果使用弱引用，那么发送延时消息的时候，在延时期间存在内存泄漏
    //因为非静态内部类隐式持有外部类的引用

    //添加static修饰符的时候，即使显式传递了外部类的引用，因为使用了弱引用，此时仍不会出现内存泄漏
    private class MyHandler(private val myContext: WeakReference<Context>) :
        Handler(Callback {
            println("Callback 处理消息")
            false
        }) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x100 -> {
                    Toast.makeText(myContext.get(), "handleMessage 处理消息", Toast.LENGTH_SHORT).show()
                    println("接收消息")
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_to_main)

        initListener()
        myHandler.looper.queue.addIdleHandler {
            println("queueIdle ${count++}")
            return@addIdleHandler true
        }
    }

    private fun initListener() {
        btnSubSendMessageToMain.setOnClickListener {
            subThreadSendMessageToMainThread()
        }
    }

    private fun subThreadSendMessageToMainThread() {
        val runnable = Runnable {
            /*var message = Message.obtain(myHandler) {
                println("msg.callback 处理消息")
            }*/
            var message = myHandler.obtainMessage()
            message.what = 0x100
            myHandler.sendMessageDelayed(message, 5000)
        }

        Thread(runnable).start()
    }
}
