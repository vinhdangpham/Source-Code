package com.calendar;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EditEvent_Activity extends Activity {
	TextView txtDate;
	EditText edtContent, edtTitle;
	Button btnSave, btnCancel;
	String updateeventId;
	String cbTrueFalse;
	String dateIntent;

	SqliteDataSource dataSource;
	String dateToAddEvent;
	CheckBox cbAlarm;
	TimePicker timePicker;

	String[] dateSeparate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_event);

		txtDate = (TextView) findViewById(R.id.txtDate);
		edtContent = (EditText) findViewById(R.id.editContent);
		edtTitle = (EditText) findViewById(R.id.editTitle);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		dataSource = new SqliteDataSource(getApplicationContext());

		cbAlarm = (CheckBox) findViewById(R.id.cbAlarmEdit);
		timePicker = (TimePicker) findViewById(R.id.tpEdit);

		Intent intent = getIntent();

		txtDate.setText(intent.getStringExtra("dateIntent"));
		edtContent.setText(intent.getStringExtra("contentIntent"));
		edtTitle.setText(intent.getStringExtra("titleIntent"));
		cbTrueFalse = intent.getStringExtra("alarmIntent");
		dateIntent = intent.getStringExtra("dateIntent");
		
		dateSeparate = dateIntent.split("/");
		
		timePicker.setEnabled(false);
		
		if (cbTrueFalse.equals("true")) {
			cbAlarm.setChecked(true);
			timePicker.setEnabled(true);
		}

		updateeventId = intent.getStringExtra("idIntent");

		cbAlarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (cbAlarm.isChecked())
					timePicker.setEnabled(true);
				else
					timePicker.setEnabled(false);
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!edtContent.getText().toString().equals("")
						&& !edtTitle.getText().toString().equals("")) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							EditEvent_Activity.this);

					// set title
					alertDialogBuilder.setTitle("Update Event Information");

					// set dialog message
					alertDialogBuilder
							.setMessage("Are you sure to update this event?")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dataSource
													.updateEvent(
															updateeventId,
															edtTitle.getText()
																	.toString(),
															edtContent
																	.getText()
																	.toString(),
															timePicker
																	.getCurrentHour()
																	+ ":"
																	+ timePicker
																			.getCurrentMinute(),
															(cbAlarm.isChecked() ? "true"
																	: "false"));

											if ((cbTrueFalse.equals("true") && cbAlarm
													.isChecked())
													|| (cbTrueFalse
															.equals("false") && cbAlarm
															.isChecked())) {
												Calendar current = Calendar
														.getInstance();

												Calendar cal = Calendar
														.getInstance();
												cal.set(Integer
														.parseInt(dateSeparate[2]),
														Integer.parseInt(dateSeparate[1]) - 1,
														Integer.parseInt(dateSeparate[0]),
														timePicker
																.getCurrentHour(),
														timePicker
																.getCurrentMinute(),
														00);
												if (cal.compareTo(current) <= 0) {
													// The set Date/Time already
													// passed
													Toast.makeText(
															getApplicationContext(),
															"Invalid Date/Time",
															Toast.LENGTH_LONG)
															.show();
												} else {
													setAlarm(
															cal,
															Integer.parseInt(updateeventId));
													Toast.makeText(
															getApplicationContext(),
															"Update succedded!",
															Toast.LENGTH_SHORT)
															.show();
													finish();
												}
											} else if (cbTrueFalse
													.equals("true")
													&& !cbAlarm.isChecked()) {
												deleteAlarm(
														EditEvent_Activity.this,
														Integer.parseInt(updateeventId));
												Toast.makeText(
														getApplicationContext(),
														"Update succedded!",
														Toast.LENGTH_SHORT)
														.show();
												finish();
											}
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Please fill out event information!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void deleteAlarm(Context context, int id) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, id, intent,
				0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	private void setAlarm(Calendar targetCal, int id) {
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), id, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
				pendingIntent);
	}
}
