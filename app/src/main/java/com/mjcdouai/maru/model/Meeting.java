package com.mjcdouai.maru.model;


import java.util.List;

public class Meeting {
    private String mMeetingTime;
    private String mMeetingDate;
    private String mMeetingSubject;
    private List<String> mMeetingUsers;
    private String mMeetingPlace;

    public Meeting(String meetingTime, String meetingDate, String meetingSubject, List<String> meetingUsers, String meetingPlace) {
        mMeetingTime = meetingTime;
        mMeetingDate = meetingDate;
        mMeetingSubject = meetingSubject;
        mMeetingUsers = meetingUsers;
        mMeetingPlace = meetingPlace;
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
}
