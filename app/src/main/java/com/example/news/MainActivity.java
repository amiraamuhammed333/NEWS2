package com.example.news;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import com.example.news.Base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        new Handler ( ).postDelayed ( new Runnable () {
                    @Override
                    public void run() { startActivity ( new Intent ( activity,News.class ) ); }
                },2000 ); }
}
