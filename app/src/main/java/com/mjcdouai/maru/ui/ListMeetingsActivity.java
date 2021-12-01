package com.mjcdouai.maru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.databinding.ListItemMeetingBinding;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.DummyMeetingGenerator;

import java.util.List;

public class ListMeetingsActivity extends AppCompatActivity {

    private ActivityListMeetingsBinding mBinding;
    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = ActivityListMeetingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.recyclerview.setAdapter(new MeetingRecyclerViewAdapter(mMeetings));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}