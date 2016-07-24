package com.tencent.fakegps;

import android.content.Context;
import android.support.annotation.NonNull;

import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.tencent.fakegps.model.LocBookmark;
import com.tencent.fakegps.model.LocPoint;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tiger on 7/23/16.
 */
public final class DbUtils {

    private static final String SHARED_PREF_NAME = "FakeGPS";
    private static final String KEY_LAST_LOC = "last_loc";

    private DbUtils() {
    }


    public static long insertBookmark(LocBookmark bookmark) {
        if (bookmark == null) {
            return -1;
        }
        return FakeGpsApp.getLiteOrm().insert(bookmark, ConflictAlgorithm.Replace);
    }

    public static void deleteBookmark(LocBookmark bookmark) {
        if (bookmark == null) return;
        FakeGpsApp.getLiteOrm().delete(bookmark);
    }

    public static void saveBookmark(Collection<LocBookmark> bookmarks) {
        FakeGpsApp.getLiteOrm().deleteAll(LocBookmark.class);
        FakeGpsApp.getLiteOrm().save(bookmarks);
    }

    public static ArrayList<LocBookmark> getAllBookmark() {
        return FakeGpsApp.getLiteOrm().query(LocBookmark.class);
    }

    public static void saveLastLocPoint(@NonNull Context context, @NonNull LocPoint locPoint) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit()
                .putString(KEY_LAST_LOC, locPoint.toString())
                .apply();
    }

    public static String getLastLocPoint(@NonNull Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(KEY_LAST_LOC, "");
    }

}
