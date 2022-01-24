package com.mjcdouai.maru.service;

import com.mjcdouai.maru.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    List<Meeting> getMeetings();
    void addMeeting(Meeting meeting);
    void delMeeting(Meeting meeting);
    List<Meeting> filterMeetingByDate(String date);
    List<Meeting> filterMeetingByPlace(String place);
}
