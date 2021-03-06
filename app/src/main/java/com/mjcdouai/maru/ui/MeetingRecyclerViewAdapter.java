package com.mjcdouai.maru.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        mBinding = ListItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MeetingViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting = mMeetingList.get(position);
        String firstLine = meeting.getMeetingSubject() + " - "
                + meeting.getMeetingTime() + " - "
                + meeting.getMeetingPlace();
        holder.getFirstLine().setText(firstLine);
        holder.getSecondLine().setText(meeting.getMeetingUsers().toString().replace("[","").replace("]",""));
        holder.getCircle().setColorFilter(meeting.getMeetingColor());
        mBinding.itemDeleteButton.setOnClickListener(v -> {
            mMeetingList.remove(holder.getBindingAdapterPosition());
            notifyItemRemoved(holder.getBindingAdapterPosition());

        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }
}
