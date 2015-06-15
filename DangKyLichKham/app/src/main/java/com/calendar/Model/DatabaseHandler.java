package com.calendar.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.calendar.DataBaseHelper;

/**
 * Created by nguyenhoang on 5/22/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "HOSPITAL.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DOCTOR = "tbDOCTOR";
    public static final String TABLE_MEDICAL_SCHEDULES = "tbMEDICAL_SCHEDULES";
    public static final String TABLE_PATIENT="tbPATIENT";
    //DatabaseHandler handler;
    private DataBaseHelper dbHelper;
    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MEDICAL_SCHEDULES = "CREATE TABLE " + TABLE_MEDICAL_SCHEDULES + "("
                + "scheduleId TEXT NOT NULL PRIMARY KEY,"
                + "scheduleDate TEXT, "
                + "scheduleNamePatient TEXT, "
                + "scheduleIdDoctor TEXT, "
                + "scheduleTitle TEXT, "
                + "scheduleDetail TEXT,"
                + "scheduleImage TEXT, "
                + "scheduleAcceptation BOOL DEFAULT 0,"
                + "scheduleAlarm TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_MEDICAL_SCHEDULES);

        String CREATE_TABLE_DOCTOR = "CREATE TABLE " + TABLE_DOCTOR + "("
                + "doctorId TEXT NOT NULL PRIMARY KEY,"
                + "doctorDepartment TEXT, "
                + "doctorFullName TEXT, "
                + "doctorImage TEXT "
                + ")";
        db.execSQL(CREATE_TABLE_DOCTOR);

        String CREATE_TABLE_PATIENT = "CREATE TABLE " + TABLE_PATIENT + "("
                + "patientId TEXT NOT NULL PRIMARY KEY,"
                + "patientEmail TEXT, "
                + "patientPassword TEXT, "
                + "patientFullName TEXT, "
                + "patientGender TEXT, "
                + "patientAddress TEXT, "
                + "patientIdentification TEXT, "
                + "patientBirthday TEXT, "
                + "patientPhone TEXT, "
                + "patientCountry TEXT "
                + ")";
        db.execSQL(CREATE_TABLE_PATIENT);




    }
    public void insertEntry(String patientId,String  patientEmail,String patientPassword,String patientFullName,String patientGender,String patientAddress,String patientIdentification,String patientCountry,String patientPhone,String patientBirthday)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();

        newValues.put("patientId",patientId );
        newValues.put("patientEmail",patientEmail );
        newValues.put("patientPassword",patientPassword);
        newValues.put("patientFullName",patientFullName );
        newValues.put("patientGender",patientGender );
        newValues.put("patientAddress", patientAddress);
        newValues.put("patientIdentification",patientIdentification );
        newValues.put("patientCountry",patientCountry );
        newValues.put("patientPhone",patientPhone );
        newValues.put("patientBirthday",patientBirthday );
        db.insert(TABLE_PATIENT, null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        onCreate(db);
    }
}
