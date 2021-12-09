package com.mjcdouai.maru.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityAddMeetingBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.List;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private MeetingApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.mjcdouai.maru.databinding.ActivityAddMeetingBinding binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();
    }

    @Override
    public void onClick(View view) {
        MeetingFirstInfosFragment firstInfos = (MeetingFirstInfosFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_first_infos);

        assert firstInfos != null;
        String subject = firstInfos.getSubject();
        String time = firstInfos.getTime();
        String date = firstInfos.getDate();

        MeetingMiscInfosFragment miscInfos = (MeetingMiscInfosFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_misc_infos);

        assert miscInfos != null;
        int color = miscInfos.getColor();
        String place = miscInfos.getPlace();
        List<String> list = miscInfos.getMails();

        Meeting meeting = new Meeting(time, date, subject, list, place, color);
        mApi.addMeeting(meeting);

        Intent resultIntent = new Intent();

        resultIntent.putExtra("meeting", meeting);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}