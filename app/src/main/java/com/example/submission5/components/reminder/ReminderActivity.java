package com.example.submission5.components.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.submission5.R;
import com.example.submission5.SharedPreference;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreference sharedPreference;
    private ReminderReceiver reminderReceiver;
    private Switch switchDaily, switchNewRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setTitle(R.string.reminder);
        sharedPreference = new SharedPreference(this);
        reminderReceiver = new ReminderReceiver();

        switchDaily = findViewById(R.id.switch_daily);
        switchNewRelease = findViewById(R.id.switch_new_release);

        switchDaily.setOnClickListener(this);
        switchNewRelease.setOnClickListener(this);

        checkReminderStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_daily:
                if (switchDaily.isChecked()) {
                    sharedPreference.saveBoolean(SharedPreference.DAILY_REMINDER_STATUS, true);
                    reminderReceiver.setDailyReminder(this, "07:00", "Let's Check Catalogue Movie Today");
                    Toast.makeText(this, getString(R.string.daily_reminder_enabled), Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreference.saveBoolean(SharedPreference.DAILY_REMINDER_STATUS, false);
                    reminderReceiver.cancelDailyReminder(this);
                    Toast.makeText(this, R.string.daily_reminder_disabled, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.switch_new_release:
                if (switchNewRelease.isChecked()) {
                    sharedPreference.saveBoolean(SharedPreference.NEW_RELEASE_REMINDER_STATUS, true);
                    reminderReceiver.setNewReleaseReminder(this, "07:00", ReminderReceiver.EXTRA_MESSAGE);
                    Toast.makeText(this, getString(R.string.newrelease_reminder_enabled), Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreference.saveBoolean(SharedPreference.NEW_RELEASE_REMINDER_STATUS, false);
                    reminderReceiver.cancelNewReleaseReminder(this);
                    Toast.makeText(this, R.string.newrelease_reminder_disabled, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkReminderStatus() {
        if (sharedPreference.getStatusDailyReminder()) {
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }

        if (sharedPreference.getStatusNewReleaseReminder()) {
            switchNewRelease.setChecked(true);
        } else {
            switchNewRelease.setChecked(false);
        }
    }
}
