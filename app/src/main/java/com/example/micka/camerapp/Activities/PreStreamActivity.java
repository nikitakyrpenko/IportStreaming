package com.example.micka.camerapp.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.ImageThread;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PreStreamActivity extends AppCompatActivity {

    private static final String url = "http://91.226.253.10:20080//zm/cgi-bin/nph-zms?mode=single&scale=100&maxfps=5&buffer=1000&monitor=56&user=iport&connkey=602221&rand=1511870800";
    private ImageView imageView;
    private Handler handler;
    private Context ctx;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_stream);

        imageView = (ImageView) findViewById(R.id.iv_prestream_image);
        btn = (Button) findViewById(R.id.btn_disable_thread);
        ctx = getApplicationContext();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new ImageThread(handler),500,500,TimeUnit.MILLISECONDS);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.shutdown();
            }
        });
    }
}