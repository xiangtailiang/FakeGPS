package com.tencent.fakegps;

import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.tencent.fakegps.model.LocBookmark;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tiger on 7/23/16.
 */
public final class DbUtils {

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

}
