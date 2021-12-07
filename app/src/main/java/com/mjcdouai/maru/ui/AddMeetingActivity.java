package com.mjcdouai.maru.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityAddMeetingBinding;
import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.databinding.FragmentMeetingFirstInfosBinding;
import com.mjcdouai.maru.databinding.FragmentMeetingMiscInfosBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddMeetingBinding mBinding;
    private MeetingApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.createMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();
    }

    @Override
    public void onClick(View view) {
        MeetingFirstInfosFragment firstInfos = (MeetingFirstInfosFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_first_infos);

        String subject = firstInfos.getSubject();
        String time = firstInfos.getTime();
        String date = firstInfos.getDate();

        MeetingMiscInfosFragment miscInfos = (MeetingMiscInfosFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_misc_infos);

        int color = miscInfos.getColor();
        String place = miscInfos.getPlace();
        List<String> list = miscInfos.getMails();

        Meeting meeting = new Meeting(time, date, subject, list, place, color);
        mApi.addMeeting(meeting);
        finish();
    }
}