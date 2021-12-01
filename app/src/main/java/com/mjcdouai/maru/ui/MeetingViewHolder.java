package com.mjcdouai.maru.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.databinding.ListItemMeetingBinding;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    private  ListItemMeetingBinding mBinding;

    public MeetingViewHolder(ListItemMeetingBinding b)
    {
        super(b.getRoot());
        mBinding = b;
    }
    public TextView getFirstLine()
    {
        return mBinding.firstLine;
    }
    public TextView getSecondLine()
    {
        return mBinding.secondLine;
    }
}
