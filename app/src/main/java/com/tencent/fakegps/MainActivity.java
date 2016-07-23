package com.tencent.fakegps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout editLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLl = (LinearLayout) findViewById(R.id.edit_ll);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);

        if (savedInstanceState == null) {
            JoyStickManager.get().init(this.getApplicationContext());
        }
    }

    private EditText getTextLat() {
        return (EditText) findViewById(R.id.textLat);
    }

    private EditText getTextLon() {
        return (EditText) findViewById(R.id.textLon);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                JoyStickManager.get().start();
                break;
            case R.id.btnStop:
                JoyStickManager.get().stop();
                break;
        }
    }
}
