package com.d.dao.eventbus_learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

//MainThread 主线程
//BackgroundThread 后台线程
//Async 后台线程
//PostThread 发送线程（默认）


public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private Button button1, button2, button3, button4;
    private final static String TAG = "EventBusTest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        mTv = (TextView) findViewById(R.id.text);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        mTv.setText("hello world");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MainMessage("hello EventBus Main"));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new BackgroundMessage("hello EventBus Background"));

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new AsyncMessage("hello EventBus Async"));

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new PostMessage("hello EventBus Post"));

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMainEventBus(MainMessage msg) {
        mTv.setText(msg.getMsg());
        Log.d(TAG, "onEventBus() returned: " + Thread.currentThread());
//  打印结果    onEventBus() returned: Thread[main,5,main]
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onBackgroundEventBus(BackgroundMessage msg) {
        Log.d(TAG, "onEventBusBackground() returned: " + Thread.currentThread());
        mTv.setText(msg.getMsg());
//   打印结果     onEventBusBackground() returned: Thread[pool-1-thread-1,5,main]
//   打印结果   Only the original thread that created a view hierarchy can touch its views.
    }

    @Subscribe(threadMode = ThreadMode.Async)
    public void onAsyncMessage(AsyncMessage msg) {
        Log.d(TAG, "onEventBusAsync() returned: " + Thread.currentThread());
        mTv.setText(msg.getMsg());
        //打印结果onEventBusAsync() returned: Thread[pool-1-thread-2,5,main]
        //打印结果Only the original thread that created a view hierarchy can touch its views.
    }
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void onPostMessage(PostMessage msg){
        Log.d(TAG, "onEventBusPost() returned: " + Thread.currentThread());
        mTv.setText(msg.getMsg());
//   打印结果     onEventBusPost() returned: Thread[main,5,main
//   打印结果    由于消息是在主线程发送,所以正常运行
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
