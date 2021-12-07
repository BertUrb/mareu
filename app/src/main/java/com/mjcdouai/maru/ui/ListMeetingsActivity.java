package com.mjcdouai.maru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.databinding.ListItemMeetingBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.DummyMeetingGenerator;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class ListMeetingsActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityListMeetingsBinding mBinding;
    private MeetingApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = ActivityListMeetingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.addMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();
        initList(mApi.getMeetings());


    }
    private void initList(List<Meeting> meetings)
    {

        mBinding.recyclerview.setAdapter(new MeetingRecyclerViewAdapter(meetings));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem dates = menu.findItem(R.id.filter_menu_item).getSubMenu()
                .findItem(R.id.filter_by_date);
        MenuItem places = menu.findItem(R.id.filter_menu_item).getSubMenu()
                .findItem(R.id.filter_by_place);
        MenuItem noFilter = menu.findItem(R.id.filter_menu_item).getSubMenu()
                .findItem(R.id.no_filter_item_menu);
        noFilter.setOnMenuItemClickListener(v -> {
            initList(mApi.getMeetings());
            return false;
        });
        List<String> alreadyAdded = new ArrayList<>();
        for(Meeting meeting : mApi.getMeetings())
        {
            if(!alreadyAdded.contains(meeting.getMeetingDate())) {
                dates.getSubMenu().add(meeting.getMeetingDate()).setOnMenuItemClickListener(v -> {
                    initList(mApi.FilterMeetingByDate(meeting.getMeetingDate()));
                    return false;
                });
                alreadyAdded.add(meeting.getMeetingDate());
            }
            if(!alreadyAdded.contains(meeting.getMeetingPlace())) {
                places.getSubMenu().add(meeting.getMeetingPlace()).setOnMenuItemClickListener(v-> {
                    initList(mApi.FilterMeetingByPlace(meeting.getMeetingPlace()));
                    return false;
                });
                alreadyAdded.add(meeting.getMeetingPlace());
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(),AddMeetingActivity.class);
        startActivity(intent);

    }
    @Override
    public void onResume() {
        super.onResume();
        initList(mApi.getMeetings());
    }
}