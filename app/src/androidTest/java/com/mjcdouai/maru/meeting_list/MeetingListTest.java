package com.mjcdouai.maru.meeting_list;


import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.meeting_list.utils.RecyclerViewUtils;
import com.mjcdouai.maru.service.MeetingApiService;
import com.mjcdouai.maru.ui.ListMeetingsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MeetingListTest {

    @Rule
    public ActivityScenarioRule<ListMeetingsActivity> mActivityRule =
            new ActivityScenarioRule<>(ListMeetingsActivity.class);
    private ActivityScenario<ListMeetingsActivity> mActivity;
    private int currentMeetingsSize= -1;
    private MeetingApiService mApi;

    @Before
    public void setUp() {
        mApi = DI.getNewInstanceMeetingApiService();
        mActivity = mActivityRule.getScenario();
        assertThat(mActivity, notNullValue());
        currentMeetingsSize = mApi.getMeetings().size();

    }
    @Test
    public void checkRecyclerViewItemCount() {
        onView(ViewMatchers.withId(R.id.recyclerview)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }

    @Test
    public void checkDeleteOnButtonClick(){
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,RecyclerViewUtils.clickChildView(R.id.item_delete_button)));
        onView(ViewMatchers.withId(R.id.recyclerview)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize -1));

    }

}
