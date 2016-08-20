package com.github.fakegps.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.fakegps.DbUtils;
import com.github.fakegps.FakeGpsUtils;
import com.github.fakegps.model.LocBookmark;
import com.github.fakegps.model.LocPoint;
import com.tencent.fakegps.R;

/**
 * BookmarkActivity
 * Created by tiger on 7/23/16.
 */
public class BookmarkActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_LOCATION = "extra_location";
    public static final String EXTRA_NAME = "extra_name";

    private EditText mNameEditText;
    private EditText mLocEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);
        LocPoint locPoint = (LocPoint) intent.getSerializableExtra(EXTRA_LOCATION);
        mNameEditText = (EditText) findViewById(R.id.inputName);
        mLocEditText = (EditText) findViewById(R.id.inputLoc);

        mNameEditText.setText(name);
        if (!TextUtils.isEmpty(name)) {
            mNameEditText.setSelection(name.length());
        }
        if (locPoint != null) {
            mLocEditText.setText(locPoint.toString());
        }

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                saveBookmark();
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void saveBookmark() {
        String name = mNameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        LocPoint locPointFromInput = FakeGpsUtils.getLocPointFromInput(this, mLocEditText);
        if (locPointFromInput != null) {
            LocBookmark locBookmark = new LocBookmark(name, locPointFromInput);
            long id = DbUtils.insertBookmark(locBookmark);
            if (id != -1) {
                Toast.makeText(this, "bookmark saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "bookmark cannot save!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void startPage(Context context, String name, LocPoint locPoint) {
        Intent intent = new Intent(context, BookmarkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_LOCATION, locPoint);
        context.startActivity(intent);
    }

}
