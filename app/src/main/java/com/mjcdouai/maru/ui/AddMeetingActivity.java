package com.mjcdouai.maru.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

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
    private View mColorPreview;
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

        final Calendar c = Calendar.getInstance();
        mLastSelectedYear = c.get(Calendar.YEAR);
        mLastSelectedMonth = c.get(Calendar.MONTH);
        mLastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        mBinding.firstInfos.buttonDate.setOnClickListener(v -> buttonSelectDate());
        mBinding.firstInfos.buttonTime.setOnClickListener(v -> buttonSelectTime());

        mBinding.miscInfos.etValue.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String txtVal = v.getText().toString();
                if (!txtVal.isEmpty()) {
                    if (isValidEmail(txtVal)) {
                        addChipToGroup(txtVal, mBinding.miscInfos.chipGroup2);
                        mBinding.miscInfos.etValue.setText("");
                    } else {
                        Toast.makeText(this, getString(R.string.toast_not_valid_mail), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        });

        Button pickColorButton = mBinding.miscInfos.pickColorButton;
        mColorPreview = mBinding.miscInfos.previewSelectedColor;
        mDefaultColor = 0;
        pickColorButton.setOnClickListener(
                v -> new ColorPickerPopup.Builder(getBaseContext()).initialColor(Color.RED)
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle(getString(R.string.StrChoose))
                        .cancelTitle(getString(R.string.StrCancel))
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void
                            onColorPicked(int color) {
                                mDefaultColor = color;
                                mColorPreview.setBackgroundColor(mDefaultColor);
                            }
                        }));

    }

    @Override
    public void onClick(View view) {
        boolean valid = true;

        String subject = mBinding.firstInfos.meetingSubjectInput.getText().toString();
        if (subject.isEmpty()) {
            valid = false;
            mBinding.firstInfos.meetingSubjectInput.setError(getString(R.string.toast_subject_required));
        }

        String date = mBinding.firstInfos.editTextDate.getText().toString();
        if (date.isEmpty()) {
            valid = false;
            mBinding.firstInfos.editTextDate.setError(getString(R.string.toast_date_required));
        }

        String time = mBinding.firstInfos.editTextTime.getText().toString();
        if (time.isEmpty()) {
            valid = false;
            mBinding.firstInfos.editTextTime.setError(getString(R.string.toast_time_required));
        }

        String place = mBinding.miscInfos.meetingPlace.getSelectedItem().toString();

        List<String> list = new ArrayList<>();
        if (mBinding.miscInfos.chipGroup2.getChildCout(n) < 1) {
            valid = false;
                       mBinding.miscInfos.etValue.setError(getString(R.string.toast_mail_required));
        } else {
            for (int i = 0; i < mBinding.miscInfos.chipGroup2.getChildCount(); i++) {
                Chip chip = (Chip) mBinding.miscInfos.chipGroup2.getChildAt(i);
                list.add(chip.getText().toString());
            }
        }

        if (valid) {
            Meeting meeting = new Meeting(time, date, subject, list, place, mDefaultColor);
            mApi.addMeeting(meeting);
            finish();
        }
    }

    private void buttonSelectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {

            mBinding.firstInfos.editTextDate.setText(getString(R.string.date_format, dayOfMonth, monthOfYear + 1, year));

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
            mBinding.firstInfos.editTextTime.setText(getString(R.string.time_format, hourOfDay, minute));
            lastSelectedHour = hourOfDay;
            lastSelectedMinute = minute;
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                timeSetListener, lastSelectedHour, lastSelectedMinute, false);


        timePickerDialog.show();
    }

    private void addChipToGroup(String txt, ChipGroup chipGroup) {
        Chip chip = new Chip(this);

        chip.setText(txt);
        chip.setCloseIconVisible(true);

        // necessary to get single selection working
        chip.setClickable(false);
        chip.setCheckable(false);
        chipGroup.addView(chip);

        chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));
    }

    private boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}