package com.mjcdouai.maru.meeting_list;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.graphics.Color;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Root;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.meeting_list.utils.ColorPickerUtils;
import com.mjcdouai.maru.meeting_list.utils.DatePickerDialogUtils;
import com.mjcdouai.maru.meeting_list.utils.RecyclerViewUtils;
import com.mjcdouai.maru.meeting_list.utils.TimePickerDialogUtils;
import com.mjcdouai.maru.service.MeetingApiService;
import com.mjcdouai.maru.ui.ListMeetingsActivity;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    @Rule
    public ActivityScenarioRule<ListMeetingsActivity> mActivityRule =
            new ActivityScenarioRule<>(ListMeetingsActivity.class);
    private int currentMeetingsSize = -1;

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }

    @Before
    public void setUp() {
        MeetingApiService api = DI.getMeetingApiService();
        ActivityScenario<ListMeetingsActivity> activity = mActivityRule.getScenario();
        assertThat(activity, notNullValue());
        currentMeetingsSize = api.getMeetings().size();

    }

    @Test
    public void checkRecyclerViewItemCount() {
        onView(ViewMatchers.withId(R.id.recyclerview)).check(new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }

    @Test
    public void checkDeleteOnButtonClick() {
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewUtils.clickChildView(R.id.item_delete_button)));
        onView(ViewMatchers.withId(R.id.recyclerview)).check(new RecyclerViewUtils.ItemCount(currentMeetingsSize - 1));

    }

    @Test
    public void checkIfAddingMeetingWorks() {


        onView(ViewMatchers.withId(R.id.add_meeting_button)).perform(click());
        onView(ViewMatchers.withId(R.id.meeting_subject_input)).perform(typeText("Test meeting"));
        onView(ViewMatchers.withId(R.id.button_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(TimePickerDialogUtils.setTime(11, 15));
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.button_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(DatePickerDialogUtils.setDate(11, 12, 2022));
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.pick_color_button)).perform(click());
        onView(isRoot()).inRoot(isPopupWindow()).perform(ColorPickerUtils.setColor(Color.YELLOW));
        onView(withText("Choose")).inRoot(isPopupWindow()).perform(click());
        onView(ViewMatchers.withId(R.id.etValue))
                .perform(typeText("admin@mjcdouai.fr")).perform(ViewActions.pressImeActionButton())
                .perform(typeText("cyber@mjcdouai.fr")).perform(ViewActions.pressImeActionButton());
        onView(ViewMatchers.withId(R.id.meeting_place)).perform(click());
        onView(withText("Room 5")).perform(click());
        onView(ViewMatchers.withId(R.id.create_meeting_button)).perform(ViewActions.closeSoftKeyboard()).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview)).check(new RecyclerViewUtils.ItemCount(currentMeetingsSize + 1));

    }

}
