package com.lyf.hanlder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lyf.hanlder.javapackage.JavaHandlerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x100 -> {
                    Toast.makeText(this@MainActivity, "收到消息", Toast.LENGTH_LONG).show()
                    println("收到消息")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    private fun initListener() {
        btnJavaHandler.setOnClickListener {
            startActivity(Intent(this, JavaHandlerActivity::class.java))
        }

        btnSendMessage.setOnClickListener {
            var message = Message()
            message.what = 0x100
            myHandler.sendMessage(message)
        }
    }
}
