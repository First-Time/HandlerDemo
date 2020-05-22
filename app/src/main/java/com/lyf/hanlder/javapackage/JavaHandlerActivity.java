package com.lyf.hanlder.javapackage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lyf.hanlder.R;

public class JavaHandlerActivity extends AppCompatActivity {
    private static Context myContext;

    private static Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x100:
                    Toast.makeText(myContext, "接收消息", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_handler);

        myContext = this;

        initListener();
    }

    private void initListener() {
        findViewById(R.id.btnSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 0x100;
                myHandler.sendMessage(message);
            }
        });
    }
}
