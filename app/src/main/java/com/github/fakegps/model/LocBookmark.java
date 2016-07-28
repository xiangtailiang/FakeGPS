package com.github.fakegps.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * bookmark for location
 * Created by tiger on 7/23/16.
 */
@Table("LocBookmark")
public class LocBookmark implements Serializable{
    static final long serialVersionUID =-1770575152720897666L;

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    private int id;

    @NotNull
    private String mName;

    private LocPoint mLocPoint;


    public LocBookmark() {
    }

    public LocBookmark(String name, LocPoint locPoint) {
        mName = name;
        mLocPoint = locPoint;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public LocPoint getLocPoint() {
        return mLocPoint;
    }

    public void setLocPoint(LocPoint locPoint) {
        mLocPoint = locPoint;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("LocBookmark: ");
        builder.append(mName);
        builder.append(" ");
        builder.append(mLocPoint != null ? mLocPoint.toString() : "loc null");

        return builder.toString();
    }
}
