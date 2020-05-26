package com.lyf.hanlder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyf.hanlder.javapackage.JavaHandlerActivity
import com.lyf.hanlder.subtomain.SubToMainActivity
import com.lyf.hanlder.subtosub.SubToSubActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    private fun initListener() {
        btnJavaHandler.setOnClickListener {
            startActivity(Intent(this, JavaHandlerActivity::class.java))
        }

        btnSubToMain.setOnClickListener {
            startActivity(Intent(this, SubToMainActivity::class.java))
        }

        btnSubToSub.setOnClickListener {
            startActivity(Intent(this, SubToSubActivity::class.java))
        }
    }
}
