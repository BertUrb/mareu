package com.mjcdouai.maru.service;

import android.graphics.Color;

import com.mjcdouai.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("14:00","2/12/2021","Réunion A",Arrays.asList("maxime@lamzone.com","luc@lamzone.com","paul@lamzone.com"),"Mario", Color.CYAN),
            new Meeting("15:00","3/12/2021","Réunion B",Arrays.asList("elise@lamzone.com","luc@lamzone.com","jo@lamzone.com"),"Luigi", Color.MAGENTA),
            new Meeting("16:00","2/12/2021","Réunion C",Arrays.asList("thibault@lamzone.com","christine@lamzone.com","paul@lamzone.com"),"Peach", Color.GRAY)
    );
    static public List<Meeting> generateMeetings() { return new ArrayList<>(DUMMY_MEETINGS);    }
}
