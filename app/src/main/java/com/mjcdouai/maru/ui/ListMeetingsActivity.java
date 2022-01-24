package com.mjcdouai.maru.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcdouai.maru.R;
import com.mjcdouai.maru.databinding.ActivityListMeetingsBinding;
import com.mjcdouai.maru.di.DI;
import com.mjcdouai.maru.model.Meeting;
import com.mjcdouai.maru.service.MeetingApiService;

import java.util.Calendar;
import java.util.List;

public class ListMeetingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityListMeetingsBinding mBinding;
    private MeetingApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityListMeetingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.addMeetingButton.setOnClickListener(this);
        mApi = DI.getMeetingApiService();
        initList(mApi.getMeetings());
    }

    private void initList(List<Meeting> meetings) {
        mBinding.recyclerview.setAdapter(new MeetingRecyclerViewAdapter(meetings));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
       if(title.equals(getResources().getString(R.string.filter_by_date)))
       {
           DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> initList(mApi.filterMeetingByDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
           final Calendar c = Calendar.getInstance();
           DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                   dateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
           datePickerDialog.show();

       }
       else if(title.equals(getResources().getString(R.string.filter_by_place)))
       {
           AlertDialog.Builder b = new AlertDialog.Builder(this);
           b.setTitle(R.string.select_room);
           b.setItems(getResources().getStringArray(R.array.places_array), (dialog, which) -> {
               dialog.dismiss();
               initList(mApi.filterMeetingByPlace(getResources().getStringArray(R.array.places_array)[which]));
               });
           b.show();
       }
       else if(title.equals(getResources().getString(R.string.no_filter)))
       {
           initList(mApi.getMeetings());
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
       startActivity(new Intent(getBaseContext(), AddMeetingActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList(mApi.getMeetings());
    }
}