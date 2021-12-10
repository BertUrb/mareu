package com.mjcdouai.maru.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Meeting implements Parcelable {
    private String mMeetingTime;
    private String mMeetingDate;
    private String mMeetingSubject;
    private List<String> mMeetingUsers;
    private String mMeetingPlace;
    private Integer mMeetingColor;
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

    public void setMeetingColor(Integer meetingColor) {
        mMeetingColor = meetingColor;
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

    public void setMeetingTime(String meetingTime) {
        mMeetingTime = meetingTime;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        mMeetingDate = meetingDate;
    }

    public String getMeetingSubject() {
        return mMeetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        mMeetingSubject = meetingSubject;
    }

    public List<String> getMeetingUsers() {
        return mMeetingUsers;
    }

    public void setMeetingUsers(List<String> meetingUsers) {
        mMeetingUsers = meetingUsers;
    }

    public String getMeetingPlace() {
        return mMeetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        mMeetingPlace = meetingPlace;
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
