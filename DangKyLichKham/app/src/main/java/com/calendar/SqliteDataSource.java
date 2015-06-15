package com.calendar;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.calendar.Model.Event;

public class SqliteDataSource {

	SQLiteDatabase database;
	DBHelper helper;

	public SqliteDataSource(Context context) {
		DBHelper helper = new DBHelper(context);
		helper.createDatabase();
		database = helper.openDatabase();
	}

	public ArrayList<Event> loadEvent(String today) {
		ArrayList<Event> listE = new ArrayList<Event>();
		Cursor cursor = database.rawQuery(" SELECT * FROM " + DBHelper.DBTABLE
				+ " where " + DBHelper.COLDATE + " >= '" + today
				+ "' order by " + DBHelper.COLDATE + " limit 5", null);
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event();
				event.setIdEvent(cursor.getString(0));
				event.setTitleEvent(cursor.getString(1));
				event.setContentEvent(cursor.getString(2));
				event.setDateEvent(formatDate(cursor.getString(3)));
				event.setTimeEvent(cursor.getString(4));
				event.setAlarmEvent(cursor.getString(5));

				listE.add(event);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listE;
	}

	public ArrayList<String> loadDateEvent() {
		ArrayList<String> listE = new ArrayList<String>();
		Cursor cursor = database.rawQuery(" SELECT * FROM " + DBHelper.DBTABLE,
				null);
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event();
				event.setDateEvent(formatDate(cursor.getString(3)));
				listE.add(event.getDateEvent());
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listE;
	}

	public void insertEvent(String title, String content, String date,
			String time, String alarm) {

		String sql_select_statement = "Insert INTO " + DBHelper.DBTABLE + "('"
				+ DBHelper.COLTITLE + "','" + DBHelper.COLCONTENT + "','"
				+ DBHelper.COLDATE + "','" + DBHelper.COLTIME + "','"
				+ DBHelper.COLALARM + "')" + " VALUES ('" + title + "','"

				+ content + "','" + date + "','" + time + "','" + alarm + "')";

		database.execSQL(sql_select_statement);
	}

	public void deleteEvent(String idEvent) {

		String sql_select_statement = "Delete From " + DBHelper.DBTABLE
				+ " where " + DBHelper.COLID + " = '" + idEvent + "'";
		database.execSQL(sql_select_statement);
	}

	public void updateEvent(String eventId, String eventTitle,
			String eventContent, String eventTime, String eventAlarm) {
		String sql_select_statement = "Update " + DBHelper.DBTABLE + " set "
				+ DBHelper.COLTITLE + " = '" + eventTitle + "', "
				+ DBHelper.COLCONTENT + " = '" + eventContent + "', "
				+ DBHelper.COLTIME + " = '" + eventTime + "', "
				+ DBHelper.COLALARM + " = '" + eventAlarm + "' where "
				+ DBHelper.COLID + " = '" + eventId + "'";
		database.execSQL(sql_select_statement);
	}

	public ArrayList<Event> getEventByDate(String date) {
		ArrayList<Event> eventList = null;

		String sql_select_statement = "SELECT " + DBHelper.COLID + ", "
				+ DBHelper.COLTITLE + ", " + DBHelper.COLCONTENT + ", "
				+ DBHelper.COLDATE + ", " + DBHelper.COLTIME + ", "
				+ DBHelper.COLALARM + " FROM " + DBHelper.DBTABLE + " Where "
				+ DBHelper.COLDATE + " = '" + date + "'";
		Cursor cursor = database.rawQuery(sql_select_statement, null);
		eventList = parseCursorEvent(cursor);
		return eventList;
	}

	public ArrayList<Event> getEventByTime(String date, String time) {
		ArrayList<Event> eventList = null;

		String sql_select_statement = "SELECT " + DBHelper.COLID + ", "
				+ DBHelper.COLTITLE + ", " + DBHelper.COLCONTENT + ", "
				+ DBHelper.COLDATE + ", " + DBHelper.COLTIME + ", "
				+ DBHelper.COLALARM + " FROM " + DBHelper.DBTABLE + " Where "
				+ DBHelper.COLDATE + " = '" + date + "' and "
				+ DBHelper.COLTIME + " = '" + time + "' and "
				+ DBHelper.COLALARM + " = 'true'";
		Cursor cursor = database.rawQuery(sql_select_statement, null);
		eventList = parseCursorEvent(cursor);
		return eventList;
	}

	public int getMaxId() {

		int id = 0;

		String sql_select_statement = "SELECT MAX(" + DBHelper.COLID
				+ ") FROM " + DBHelper.DBTABLE;

		Cursor cursor = database.rawQuery(sql_select_statement, null);
		if (cursor != null && cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		return id;
	}

	private ArrayList<Event> parseCursorEvent(Cursor cursor) {

		ArrayList<Event> eventList = new ArrayList<Event>();

		if (cursor.moveToFirst()) {

			while (!cursor.isAfterLast()) {

				String eventId = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLID));
				String eventTitle = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLTITLE));
				String eventContent = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLCONTENT));
				String eventDate = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLDATE));
				String eventTime = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLTIME));
				String eventAlarm = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLALARM));
				Event l = new Event(eventId, eventTitle, eventContent,
						eventDate, eventTime, eventAlarm);
				eventList.add(l);
				cursor.moveToNext();
			}
		}
		return eventList;
	}

	private String formatDate(String date) {
		String[] separatedDate = date.split("-");
		return separatedDate[2] + "/" + separatedDate[1] + "/"
				+ separatedDate[0];
	}
}
