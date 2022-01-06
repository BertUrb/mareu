package com.mjcdouai.maru.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mjcdouai.maru.databinding.ListItemMeetingBinding;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    private final ListItemMeetingBinding mBinding;

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
    public ImageView getCircle(){return mBinding.circle;}
}
