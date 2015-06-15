package com.calendar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import android.graphics.Typeface;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calendar.Model.Event;

@SuppressLint("NewApi")
public class CalendarView extends Activity {


	public GregorianCalendar month, itemmonth;

	public CalendarAdapter calendarAdapter;
	public Handler handler;
	public ArrayList<String> items;
	public ArrayList<Event> myListEvent;
	public ArrayList<String> dateEvent;
	SqliteDataSource sqliteDataSource;

	public MediaPlayer mMediaPlayer;

	Event myEvent;
	String idEventDelete;

	Button btn;
	ListView lvEvent;
	Event_Adapter eventAdapter;

	ArrayList<Event> listEvent;

	SimpleDateFormat df;
	String formattedDate;
	Calendar c = Calendar.getInstance();

	float xUp;
	float xDown;

	Dialog dialog;
	int DIALOG_OK = 100;
	TextView txtDate;
	TextView txtEvent;
	Button btnOk;

	String datePutToAdd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		lvEvent = (ListView) findViewById(R.id.lvEvent);

		sqliteDataSource = new SqliteDataSource(getApplicationContext());

		listEvent = new ArrayList<Event>();
		dateEvent = new ArrayList<String>();

		df = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = df.format(c.getTime());

		datePutToAdd = formattedDate;
		listEvent = sqliteDataSource.loadEvent(formattedDate);

		eventAdapter = new Event_Adapter(this, listEvent);
		lvEvent.setAdapter(eventAdapter);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Oswald-Regular.ttf");
		btn = (Button) findViewById(R.id.button1);




        btn.setTypeface(myTypeface);

        Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
		calendarAdapter = new CalendarAdapter(this, month);

		myListEvent = new ArrayList<Event>();
		myEvent = new Event();
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(calendarAdapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		mMediaPlayer = MediaPlayer.create(CalendarView.this, R.raw.dingdong);

		TextView title = (TextView) findViewById(R.id.title);

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		dialog = new Dialog(CalendarView.this);

		Intent intentAlarm = getIntent();

		if (intentAlarm.getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK) {
			myListEvent = sqliteDataSource.getEventByTime(today(), todayHour());

			String txtShowdialog = "";

			Event myEvent = new Event();
			for (int i = 0; i < myListEvent.size(); i++) {
				myEvent = myListEvent.get(i);
				txtShowdialog += "\n" + "Title: " + myEvent.getTitleEvent()
						+ "\nDescription: " + myEvent.getContentEvent() + "\n";
			}

			if (txtShowdialog.equals("")) {
			} else {
				// playSound(this);
				try {
					mMediaPlayer.prepare();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mMediaPlayer.start();
				showCustomDialog(today(), txtShowdialog);

			}
		}

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// add event to calendar
				addCalendarEvent();
			}
		});
		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		lvEvent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				listEvent = sqliteDataSource.loadEvent(formattedDate);
				myEvent = listEvent.get(pos);

				Intent intent = new Intent(CalendarView.this,
						EditEvent_Activity.class);
				intent.putExtra("dateIntent", myEvent.getDateEvent());
				intent.putExtra("contentIntent", myEvent.getContentEvent());
				intent.putExtra("titleIntent", myEvent.getTitleEvent());
				intent.putExtra("dateIntent", myEvent.getDateEvent());
				intent.putExtra("idIntent", myEvent.getIdEvent());
				intent.putExtra("alarmIntent", myEvent.getAlarmEvent());

				startActivity(intent);
			}
		});
		lvEvent.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> arg0,
					View arg1, int pos, long arg3) {
				listEvent = sqliteDataSource.loadEvent(formattedDate);
				idEventDelete = listEvent.get(pos).getIdEvent();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CalendarView.this);

				// set title
				alertDialogBuilder.setTitle("Delete Event");

				// set dialog message
				alertDialogBuilder
				.setMessage("Are you sure to delete this event?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int id) {

						deleteAlarm(CalendarView.this,
								Integer.parseInt(idEventDelete));

						sqliteDataSource
						.deleteEvent(idEventDelete);
						listEvent.clear();
						listEvent = sqliteDataSource
								.loadEvent(formattedDate);
						eventAdapter = new Event_Adapter(
								CalendarView.this, listEvent);
						lvEvent.setAdapter(eventAdapter);
						handler.post(calendarUpdater);
						deleteAlarm(CalendarView.this,
								Integer.parseInt(idEventDelete));
					}
				})
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						dialog.cancel();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
				return true;
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);

				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");
				int gridvalue = Integer.parseInt(gridvalueString);

				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				String myDate = separatedTime[2] + "/" + separatedTime[1] + "/"
						+ separatedTime[0];
				datePutToAdd = selectedGridDate;
				// try {
				myListEvent = sqliteDataSource.getEventByDate(selectedGridDate);

				String txtShowdialog = "";

				Event myEvent = new Event();
				for (int i = 0; i < myListEvent.size(); i++) {
					myEvent = myListEvent.get(i);
					txtShowdialog += "\n" + "Title: " + myEvent.getTitleEvent()
							+ "\nDescription: " + myEvent.getContentEvent()
							+ "\n";
				}

				if (txtShowdialog.equals("")) {
				} else {
					showCustomDialog(myDate, txtShowdialog);
				}
			}
		});

		gridview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					xDown = event.getX();
					return false;
				case MotionEvent.ACTION_CANCEL:
					xUp = event.getX();
					if (xDown - xUp > 20) {
						setNextMonth();
						refreshCalendar();
					} else if (xDown - xUp < -20) {
						setPreviousMonth();
						refreshCalendar();
					}
					break;
				default:
					return false;
				}
				return false;
			}
		});

	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}
	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		calendarAdapter.refreshDays();
		calendarAdapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();
			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			@SuppressWarnings("unused")
			String itemvalue;

			dateEvent = sqliteDataSource.loadDateEvent();

			for (int i = 0; i < dateEvent.size(); i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(formatDate(dateEvent.get(i)));
			}

			calendarAdapter.setItems(items);
			calendarAdapter.notifyDataSetChanged();
		}
	};

	public void addCalendarEvent() {
		Intent intentEvent = new Intent(CalendarView.this,
				AddEvent_Activity.class);
		intentEvent.putExtra("date", datePutToAdd);
		startActivity(intentEvent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		listEvent.clear();
		listEvent = sqliteDataSource.loadEvent(formattedDate);
		eventAdapter = new Event_Adapter(this, listEvent);
		lvEvent.setAdapter(eventAdapter);
		handler.post(calendarUpdater);
	}

	public String formatDate(String date) {
		String[] separatedDate = date.split("/");
		return separatedDate[2] + "-" + separatedDate[1] + "-"
		+ separatedDate[0];
	}

	protected void showCustomDialog(String date, String event) {

		dialog = new Dialog(CalendarView.this,
				android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog);

		txtDate = (TextView) dialog.findViewById(R.id.txtDate);
		txtEvent = (TextView) dialog.findViewById(R.id.txtEvent);
		btnOk = (Button) dialog.findViewById(R.id.btnOk);
		txtDate.setText(date);
		txtEvent.setText(event);

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				mMediaPlayer.stop();
			}
		});

		dialog.show();
	}

	public void deleteAlarm(Context context, int id) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, id, intent,
				0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	public String today() {
		String today = "";
		Time now = new Time();
		now.setToNow();
		today += now.year + "-";
		if ((now.month + 1) < 10)
			today += "0" + String.valueOf((now.month + 1));
		else
			today += (now.month + 1);

		today += "-";

		if (now.monthDay < 10)
			today += "0" + String.valueOf(now.monthDay);
		else
			today += (now.monthDay);
		return today;
	}

	public String todayHour() {

		Time now = new Time();
		now.setToNow();

		String todayHour = now.hour + ":" + now.minute;

		return todayHour;
	}
}
