package com.mjcdouai.maru.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class ListMeetingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityListMeetingsBinding mBinding;
    private MeetingApiService mApi;
    private Menu mMenu;
    List<String> alreadyAdded = new ArrayList<>();
    ActivityResultLauncher<Intent> AddMeetingActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        assert data != null;
                        Meeting meeting = data.getParcelableExtra("meeting");

                        if (!alreadyAdded.contains(meeting.getMeetingDate())) {
                            mMenu.findItem(R.id.filter_menu_item)
                                    .getSubMenu()
                                    .findItem(R.id.filter_by_date)
                                    .getSubMenu()
                                    .add(meeting.getMeetingDate());
                            alreadyAdded.add(meeting.getMeetingDate());
                        }
                        if (!alreadyAdded.contains(meeting.getMeetingPlace())) {
                            mMenu.findItem(R.id.filter_menu_item)
                                    .getSubMenu()
                                    .findItem(R.id.filter_by_place)
                                    .getSubMenu()
                                    .add(meeting.getMeetingPlace());
                            alreadyAdded.add(meeting.getMeetingPlace());
                        }


                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = ActivityListMeetingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.addMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();
        initList(mApi.getMeetings());


    }

    private void initList(List<Meeting> meetings) {

        mBinding.recyclerview.setAdapter(new MeetingRecyclerViewAdapter(meetings));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        alreadyAdded.clear();
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

        for (Meeting meeting : mApi.getMeetings()) {
            if (!alreadyAdded.contains(meeting.getMeetingDate())) {
                dates.getSubMenu().add(meeting.getMeetingDate()).setOnMenuItemClickListener(v -> {
                    initList(mApi.FilterMeetingByDate(meeting.getMeetingDate()));
                    return false;
                });
                alreadyAdded.add(meeting.getMeetingDate());
            }
            if (!alreadyAdded.contains(meeting.getMeetingPlace())) {
                places.getSubMenu().add(meeting.getMeetingPlace()).setOnMenuItemClickListener(v -> {
                    initList(mApi.FilterMeetingByPlace(meeting.getMeetingPlace()));
                    return false;
                });
                alreadyAdded.add(meeting.getMeetingPlace());
            }
        }
        mMenu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), AddMeetingActivity.class);
        AddMeetingActivityResultLauncher.launch(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        initList(mApi.getMeetings());
    }


}