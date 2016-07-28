package com.github.fakegps.model;

import java.io.Serializable;

/**
 * Created by tiger on 7/23/16.
 */
public class LocPoint implements Serializable {
    static final long serialVersionUID = -1770575152720897533L;

    private double mLatitude;
    private double mLongitude;

    public LocPoint(LocPoint locPoint) {
        mLatitude = locPoint.getLatitude();
        mLongitude = locPoint.getLongitude();
    }

    public LocPoint(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    @Override
    public String toString() {
        return "(" + mLatitude + " , " + mLongitude + ")";
    }
}
