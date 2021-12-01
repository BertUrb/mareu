package com.mjcdouai.maru.service;

import com.mjcdouai.maru.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements  MeetingApiService{
    private final List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
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
}
