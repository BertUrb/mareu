package com.mjcdouai.maru.meeting_list.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.mjcdouai.maru.R;

import org.hamcrest.Matcher;

import top.defaults.colorpicker.ColorPickerView;

public class ColorPickerUtils {
    public static ViewAction setColor(int color) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                ColorPickerView colorPickerView = view.findViewById(R.id.colorPickerView);
                colorPickerView.setInitialColor(color);
            }
        };

    }


}
