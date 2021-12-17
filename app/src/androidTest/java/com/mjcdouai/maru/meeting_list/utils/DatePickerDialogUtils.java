package com.mjcdouai.maru.meeting_list.utils;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

public class DatePickerDialogUtils {
    public static ViewAction setDate(int day, int month, int year) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                 return ViewMatchers.isAssignableFrom(DatePicker.class);
            }

            @Override
            public String getDescription() {
                return "set the passed date in the datepicker";
            }

            @Override
            public void perform(UiController uiController, View view) {
                DatePicker dp = (DatePicker) view;
                dp.updateDate(year,month,day);

            }
        };
    }
}
