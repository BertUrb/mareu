package com.mjcdouai.maru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.Color;

import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.DummyMeetingGenerator;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.Arrays;
import java.util.List;

public class MeetingServiceTest {

    private MeetingApiService mApi;

    @Before
    public void setup()
    {
        mApi = DI.getNewInstanceMeetingApiService();
    }


    @Test
    public void getMeetingsWithSuccess(){
        List<Meeting> meetings = mApi.getMeetings();
        List<Meeting> expectedMeeting = DummyMeetingGenerator.DUMMY_MEETINGS;
        Assert.assertEquals(meetings,expectedMeeting);
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting(
                "14:00",
                "12/12/2021",
                "Test",
                Arrays.asList("admin@admin.com","test@test.com"),
                "ROOM 9",
                Color.BLUE);
        assertFalse(mApi.getMeetings().contains(meetingToAdd));
        mApi.addMeeting(meetingToAdd);
        assertTrue(mApi.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void deleteMeetingWithSuccess()
    {
        List<Meeting> meetings = mApi.getMeetings();
        Meeting meetingToDel = meetings.get(0);
        mApi.delMeeting(meetingToDel);
        assertFalse(meetings.contains(meetingToDel));

    }

    @Test
    public void filterMeetingsByDateWithSuccess()
    {
        List<Meeting> filteredMeeting = mApi.filterMeetingByDate("02/12/2021");

        for(Meeting meeting : filteredMeeting)
        {
            assertEquals("02/12/2021", meeting.getMeetingDate());
        }
    }

    @Test
    public void filterMeetingsByPlaceWithSuccess()
    {
        List<Meeting> filteredMeeting = mApi.filterMeetingByPlace("Peach");

        for(Meeting meeting : filteredMeeting)
        {
            assertEquals("Peach", meeting.getMeetingPlace());
        }
    }
}