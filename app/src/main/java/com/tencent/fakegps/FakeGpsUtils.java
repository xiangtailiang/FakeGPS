package com.tencent.fakegps;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.tencent.fakegps.model.LocPoint;

/**
 * Created by tiger on 7/23/16.
 */
public final class FakeGpsUtils {
    private static final String TAG = "FakeGpsUtils";

    private FakeGpsUtils() {
    }

    public static void copyToClipboard(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public static LocPoint getLocPointFromInput(Context context, EditText editText) {
        LocPoint point = null;
        String text = editText.getText().toString().replace("(", "").replace(")", "");
        String[] split = text.split(",");
        if (split.length == 2) {
            try {
                double lat = Double.parseDouble(split[0].trim());
                double lon = Double.parseDouble(split[1].trim());
                point = new LocPoint(lat, lon);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Parse loc point error!", e);
            }
        }
        return point;
    }

    public static double getMoveStepFromInput(Context context, EditText editText) {
        double step = 0;
        String stepStr = editText.getText().toString().trim();
        try {
            step = Double.valueOf(stepStr);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Parse move step error!", e);
        }

        return step;
    }

}
