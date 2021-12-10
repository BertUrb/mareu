package com.mjcdouai.maru.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityAddMeetingBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import top.defaults.colorpicker.ColorPickerPopup;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private MeetingApiService mApi;
    private ActivityAddMeetingBinding mBinding;
    // view box to preview the selected color
    private View mColorPreview;

    // this is the default color of the preview box
    private int mDefaultColor;
    private int mLastSelectedYear;
    private int mLastSelectedMonth;
    private int mLastSelectedDayOfMonth;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.createMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mLastSelectedYear = c.get(Calendar.YEAR);
        mLastSelectedMonth = c.get(Calendar.MONTH);
        mLastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        mBinding.firstInfos.buttonDate.setOnClickListener( v-> buttonSelectDate());
        mBinding.firstInfos.buttonTime.setOnClickListener( v-> buttonSelectTime());

        // register two of the buttons with their
        // appropriate IDs
        // two buttons to open color picker dialog and one to
        // set the color for GFG text
        Button pickColorButton = mBinding.miscInfos.pickColorButton;


        // and also register the view which shows the
        // preview of the color chosen by the user
        mColorPreview = mBinding.miscInfos.previewSelectedColor;

        // set the default color to 0 as it is black
        mDefaultColor = 0;

        // handling the Pick Color Button to open color
        // picker dialog
        pickColorButton.setOnClickListener(
                v -> new ColorPickerPopup.Builder(getBaseContext()).initialColor(
                        Color.RED) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                "Choose") // this is top right
                        // Choose button
                        .cancelTitle(
                                "Cancel") // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                        mColorPreview.setBackgroundColor(mDefaultColor);
                                    }
                                }));

    }

    @Override
    public void onClick(View view) {

        String subject = mBinding.firstInfos.meetingSubjectInput.getText().toString();
        String date = mBinding.firstInfos.editTextDate.getText().toString();
        String time = mBinding.firstInfos.editTextTime.getText().toString();




        String place = mBinding.miscInfos.meetingPlace.getSelectedItem().toString();

        // chipgroup
        mBinding.miscInfos.etValue.setOnEditorActionListener((v, actionId, event) -> {
            Log.d("blah", "onEditorAction: ");
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String txtVal = v.getText().toString();
                if (!txtVal.isEmpty()) {
                    Log.d("blah", "onEditorAction: ");
                    addChipToGroup(txtVal, mBinding.miscInfos.chipGroup2);
                    mBinding.miscInfos.etValue.setText("");
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });
        List<String> list = new ArrayList<>();
        for(int id : mBinding.miscInfos.chipGroup2.getCheckedChipIds())
        {
            Chip chip = mBinding.miscInfos.chipGroup2.findViewById(id);
            list.add(chip.getText().toString());
        }


        Meeting meeting = new Meeting(time, date, subject,list , place, mDefaultColor);
        mApi.addMeeting(meeting);

        finish();
    }
    private void buttonSelectDate() {
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {

            mBinding.firstInfos.editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

            mLastSelectedYear = year;
            mLastSelectedMonth = monthOfYear;
            mLastSelectedDayOfMonth = dayOfMonth;
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    dateSetListener, mLastSelectedYear, mLastSelectedMonth, mLastSelectedDayOfMonth);


        // Show
        datePickerDialog.show();
    }
    private void buttonSelectTime() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            mBinding.firstInfos.editTextTime.setText(hourOfDay + ":" + minute );
            lastSelectedHour = hourOfDay;
            lastSelectedMinute = minute;
        };

        // Create TimePickerDialog:
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    timeSetListener, lastSelectedHour, lastSelectedMinute, false);


        // Show
        timePickerDialog.show();
    }

    private void addChipToGroup(String txt, ChipGroup chipGroup) {
        Chip chip =  new Chip(getBaseContext());
        chip.setText(txt);

//        chip.chipIcon = ContextCompat.getDrawable(requireContext(), baseline_person_black_18)
        chip.setCloseIconVisible(true);
        chip.setChipIconTintResource(R.color.purple_500);

        // necessary to get single selection working
        chip.setClickable(false);
        chip.setCheckable(false);
        chipGroup.addView(chip);

        chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));
        printChipsValue(chipGroup);

    }
    private void printChipsValue(ChipGroup chipGroup) {
        for (int i=0;i<chipGroup.getChildCount();i++) {
            Chip chipObj = (Chip) chipGroup.getChildAt(i);
            Log.d("Chips text :: " , chipObj.getText().toString());

        }
    }






}