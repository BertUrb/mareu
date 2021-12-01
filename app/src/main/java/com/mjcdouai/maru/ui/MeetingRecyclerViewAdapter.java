package com.mjcdouai.maru.ui;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ListItemMeetingBinding;
import com.mjcdouai.maru.model.Meeting;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    List<Meeting> mMeetingList;
    private ListItemMeetingBinding mBinding;

    public MeetingRecyclerViewAdapter(List<Meeting> meetingList)
    {
        mMeetingList = meetingList;
    }
    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_meeting, parent, false);
        mBinding = ListItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MeetingViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

        Meeting meeting = mMeetingList.get(position);
        String firstLine = meeting.getMeetingSubject() + " - "
                + meeting.getMeetingTime() + " - "
                + meeting.getMeetingPlace();
        holder.getFirstLine().setText(firstLine);

        String secondLine = "";



        for (String mail : meeting.getMeetingUsers()
             ) {
            secondLine = secondLine + mail + " - ";

        }
        secondLine = secondLine.substring(0,secondLine.length() - 3);
        holder.getSecondLine().setText(secondLine);
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }
}
