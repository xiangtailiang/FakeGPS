package com.github.fakegps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.github.fakegps.model.LocBookmark;
import com.github.fakegps.model.LocPoint;
import com.litesuits.orm.db.model.ConflictAlgorithm;

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
        long id = FakeGpsApp.getLiteOrm().insert(bookmark, ConflictAlgorithm.Replace);
        if (id != -1) {
            notifyBookmarkUpdate();
        }
        return id;
    }

    public static void deleteBookmark(LocBookmark bookmark) {
        if (bookmark == null) return;
        FakeGpsApp.getLiteOrm().delete(bookmark);
        notifyBookmarkUpdate();
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

    public static void notifyBookmarkUpdate() {
        Intent intent = new Intent(BroadcastEvent.BookMark.ACTION_BOOK_MARK_UPDATE);
        LocalBroadcastManager.getInstance(FakeGpsApp.get()).sendBroadcast(intent);
    }

}
