package com.calendar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "dbCalendar";
	public static final String DBTABLE = "Event";
	public static final String COLID = "eventId";
	public static final String COLTITLE = "eventTitle";
	public static final String COLCONTENT = "eventContent";
	public static final String COLDATE = "eventDate";
	public static final String COLTIME = "eventTime";
	public static final String COLALARM = "eventAlarm";
	
	Context context;
	String path = "";

	public DBHelper(Context context) {
		super(context, DBNAME, null, 1);
		this.context = context;
		path = context.getFilesDir().getParent() + "/databases/" + DBNAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	public void createDatabase() {

		boolean checked = checkDatabase();
		if (checked) {
			Log.d("calendar", "Database da ton tai.");

		} else {
			Log.d("calendar", "Database chua ton tai. Tien hanh copy.");
			this.getWritableDatabase();
			copyDatabase();
		}

	}

	private void copyDatabase() {
		AssetManager assetManager = context.getAssets();
		try {
			InputStream is = assetManager.open(DBNAME);

			byte[] buffer = new byte[1024];
			int lenght;

			OutputStream os = new FileOutputStream(path);

			while ((lenght = is.read(buffer)) > 0) {
				os.write(buffer, 0, lenght);
			}

			os.flush();
			os.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkDatabase() {

		SQLiteDatabase checkDB = null;

		try {
			checkDB = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLException ex) {
			Log.d("Calendar", ex.getMessage());
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

}
