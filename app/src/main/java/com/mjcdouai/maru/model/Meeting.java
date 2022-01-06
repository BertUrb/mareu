package com.mjcdouai.maru.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Meeting implements Parcelable {
    private final String mMeetingTime;
    private final String mMeetingDate;
    private final String mMeetingSubject;
    private final List<String> mMeetingUsers;
    private final String mMeetingPlace;
    private final Integer mMeetingColor;
    public static final String[] mMeetingPlaces = {
            "Mario","Luigi","Peach","Room 4","Room 5","Room 6","Room 7","Room 8", "Room 9", "Room 10"
    };

    protected Meeting(Parcel in) {
        mMeetingTime = in.readString();
        mMeetingDate = in.readString();
        mMeetingSubject = in.readString();
        mMeetingUsers = in.createStringArrayList();
        mMeetingPlace = in.readString();
        if (in.readByte() == 0) {
            mMeetingColor = null;
        } else {
            mMeetingColor = in.readInt();
        }
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public Integer getMeetingColor() {
        return mMeetingColor;
    }

    public Meeting(String meetingTime, String meetingDate, String meetingSubject, List<String> meetingUsers, String meetingPlace, Integer meetingColor) {
        mMeetingTime = meetingTime;
        mMeetingDate = meetingDate;
        mMeetingSubject = meetingSubject;
        mMeetingUsers = meetingUsers;
        mMeetingPlace = meetingPlace;
        mMeetingColor = meetingColor;
    }

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public String getMeetingSubject() {
        return mMeetingSubject;
    }

    public List<String> getMeetingUsers() {
        return mMeetingUsers;
    }

    public String getMeetingPlace() {
        return mMeetingPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMeetingTime);
        parcel.writeString(mMeetingDate);
        parcel.writeString(mMeetingSubject);
        parcel.writeStringList(mMeetingUsers);
        parcel.writeString(mMeetingPlace);
        if (mMeetingColor == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mMeetingColor);
        }
    }
}
