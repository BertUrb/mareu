package com.mjcdouai.maru.di;

import com.mjcdouai.maru.service.DummyMeetingApiService;
import com.mjcdouai.maru.service.MeetingApiService;

public class DI {
    private static final MeetingApiService service =new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceMeetingApiService()
    {
        return new DummyMeetingApiService();
    }

}

