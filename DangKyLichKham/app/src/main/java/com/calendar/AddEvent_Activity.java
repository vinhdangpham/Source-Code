package com.calendar;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddEvent_Activity extends Activity {

	SqliteDataSource datasource;
	EditText editTitle;
	EditText editContent;
	DatePicker datepicker;
	Button btnSave;
	Button btnCancel;
	String dateToAddEvent;
	CheckBox cbAlarm;
	TimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);

		datasource = new SqliteDataSource(this);

		Intent intent = getIntent();
		dateToAddEvent = intent.getStringExtra("date");

		editTitle = (EditText) findViewById(R.id.editTitle);
		editContent = (EditText) findViewById(R.id.editContent);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		
		datepicker = (DatePicker) findViewById(R.id.dp);
		timePicker = (TimePicker) findViewById(R.id.tp);
		
		cbAlarm = (CheckBox) findViewById(R.id.cbAlarm);
		String[] separatedDate = dateToAddEvent.split("-");
		datepicker.updateDate(Integer.parseInt(separatedDate[0]),
				Integer.parseInt(separatedDate[1]) - 1,
				Integer.parseInt(separatedDate[2]));

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!editContent.getText().toString().equals("")
						&& !editTitle.getText().toString().equals("")) {
					Save();
					
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
		
		timePicker.setEnabled(false);
		
		cbAlarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(cbAlarm.isChecked())
					timePicker.setEnabled(true);
				else
					timePicker.setEnabled(false);
			}
		});
	}

	protected void Save() {
		String ngay = datepicker.getDayOfMonth() + "";
		String thang = (datepicker.getMonth() + 1) + "";
		if (datepicker.getDayOfMonth() < 10)
			ngay = "0" + datepicker.getDayOfMonth();
		if (datepicker.getMonth() + 1 < 10)
			thang = "0" + (datepicker.getMonth() + 1);
		datasource.insertEvent(editTitle.getText().toString(), editContent
				.getText().toString(), datepicker.getYear() + "-" + thang + "-"
				+ ngay, timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), (cbAlarm.isChecked())?"true":"false");
		
		Calendar current = Calendar.getInstance();
		
		Calendar cal = Calendar.getInstance();
		cal.set(datepicker.getYear(), 
				datepicker.getMonth(), 
				datepicker.getDayOfMonth(), 
				timePicker.getCurrentHour(), 
				timePicker.getCurrentMinute(), 
				00);
		
		
		if(cbAlarm.isChecked()) {
			if(cal.compareTo(current) <= 0){
				//The set Date/Time already passed
			    Toast.makeText(getApplicationContext(), 
			    		"Invalid Date/Time", 
			    		Toast.LENGTH_LONG).show();
			}else{
				setAlarm(cal);
				Toast.makeText(getApplicationContext(),
						"Adding succedded!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		else {
			Toast.makeText(getApplicationContext(),
					"Adding succedded!", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	private void setAlarm(Calendar targetCal){
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), datasource.getMaxId(), intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);	
		Log.d("BBB", datasource.getMaxId() + "haha");
	}

}
