package com.calendar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent arg1) {
		Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
		
		Intent alarmIntent = new Intent("android.intent.action.MAIN"); 
		alarmIntent.setClass(context, CalendarView.class);  
		alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(alarmIntent);
	}
}
