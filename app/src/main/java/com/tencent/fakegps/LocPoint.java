package com.tencent.fakegps;

/**
 * Created by tiger on 7/23/16.
 */
public class LocPoint {
    private double mLatitude = 37.802406;
    private double mLongitude = -122.401779;

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
