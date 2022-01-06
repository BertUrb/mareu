package com.mjcdouai.maru.service;

import android.util.Log;

import com.mjcdouai.maru.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements  MeetingApiService{
    private final List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
    private final List<Meeting> mFilteredMeeting= new ArrayList<>();
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);

    }

    @Override
    public void delMeeting(Meeting meeting) {
        mMeetings.remove(meeting);

    }

    @Override
    public List<Meeting> filterMeetingByDate(String date) {
        mFilteredMeeting.clear();
        for(Meeting meeting : mMeetings)
        {
            if(meeting.getMeetingDate().equals(date))
            {
                mFilteredMeeting.add(meeting);
            }
        }

        return mFilteredMeeting;
    }

    @Override
    public List<Meeting> filterMeetingByPlace(String place) {
        mFilteredMeeting.clear();
        for(Meeting meeting : mMeetings)
        {
            if(meeting.getMeetingPlace().equals(place))
            {
                mFilteredMeeting.add(meeting);
            }
        }
        return mFilteredMeeting;

    }

    @Override
    public String[] getRooms() {
        return Meeting.mMeetingPlaces;
    }
}
