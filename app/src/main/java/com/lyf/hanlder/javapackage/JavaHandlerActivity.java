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

import java.lang.ref.WeakReference;

public class JavaHandlerActivity extends AppCompatActivity {
    private MyHandler myHandler = new MyHandler(new WeakReference<Context>(this));

    //不添加static修饰符的时候，如果使用弱引用，那么发送延时消息的时候，在延时期间存在内存泄漏
    //因为非静态内部类隐式持有外部类的引用

    //添加static修饰符的时候，即使显式传递了外部类的引用，因为使用了弱引用，此时仍不会出现内存泄漏
    private class MyHandler extends Handler {
        private WeakReference<Context> myContext;

        public MyHandler(WeakReference<Context> context) {
            this.myContext = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x100:
                    Toast.makeText(myContext.get(), "接收消息", Toast.LENGTH_SHORT).show();
                    System.out.println("接收消息");
                    break;
            }
        }
    }


    /*private MyHandler myHandler = new MyHandler();

    //添加static修饰符的时候，如果发送延时消息，此时handler不持有外部类的引用，如果也不显式传递外部类的引用，那么不会存在内存泄漏
    //如果显式传递外部类的引用，那么也持有了外部类的引用，因此在延时期间也会存在内存泄漏，哇哈哈
    private static class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x100:
                    System.out.println("接收消息");
                    break;
            }
        }
    }*/


    /*private MyHandler myHandler = new MyHandler();

    //不添加static修饰符的时候，如果发送延时消息，在延时期间存在内存泄漏
    //因为非静态内部类隐式持有外部类的引用
    private class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x100:
                    System.out.println("接收消息");
                    break;
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_handler);

        initListener();
    }

    private void initListener() {
        findViewById(R.id.btnSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 0x100;
                myHandler.sendMessageDelayed(message, 10000);
            }
        });
    }
}
