package com.calendar.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by nguyenhoang on 6/1/2015.
 */
public class Medical_Schedules_Model {
    DatabaseHandler handler;

    public Medical_Schedules_Model(Context context) {
        handler = new DatabaseHandler(context);
    }

    public void Add_Medical_Schedules(Medical_Schedules ms) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("scheduleId", ms.getScheduleId());
        values.put("scheduleDate", ms.getScheduleDate());
        values.put("scheduleNamePatient", ms.getScheduleNamePatient());
        values.put("scheduleIdDoctor", ms.getScheduleIdDoctor());
        values.put("scheduleDetail", ms.getScheduleDetail());
        values.put("scheduleImage", ms.getScheduleImage());
        values.put("scheduleAcceptation", ms.getScheduleAcceptation());
        values.put("scheduleAlarm", ms.getScheduleAlarm());

        db.insert(handler.TABLE_MEDICAL_SCHEDULES, null, values);
        db.close();

    }


    public void Update_Medical_Schedules(Medical_Schedules ms) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("scheduleId", ms.getScheduleId());
        values.put("scheduleDate", ms.getScheduleDate());
        values.put("scheduleNamePatient", ms.getScheduleNamePatient());
        values.put("scheduleIdDoctor", ms.getScheduleIdDoctor());
        values.put("scheduleDetail", ms.getScheduleDetail());
        values.put("scheduleImage", ms.getScheduleImage());
        values.put("scheduleAcceptation", ms.getScheduleAcceptation());
        values.put("scheduleAlarm", ms.getScheduleAlarm());

        db.update(handler.TABLE_MEDICAL_SCHEDULES, values, "scheduleId=?",
                new String[]{String.valueOf(ms.getScheduleId())});
        db.close();
    }

    public void Delete_Medical_Schedules(Medical_Schedules ms) {
        SQLiteDatabase db = handler.getWritableDatabase();
        db.delete(handler.TABLE_MEDICAL_SCHEDULES, "scheduleId=?",
                new String[]{String.valueOf(ms.getScheduleId())});
        db.close();
    }

    public ArrayList<Medical_Schedules> getSchedules() {
        ArrayList<Medical_Schedules> scheduleList = new ArrayList<Medical_Schedules>();

        String selectQuery = "SELECT * from tbMEDICAL_SCHEDULES";
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                Medical_Schedules medical_schedules = new Medical_Schedules();
                medical_schedules.setScheduleId(cursor.getString(cursor.getColumnIndex("scheduleId")));
                medical_schedules.setScheduleDate(cursor.getString(cursor.getColumnIndex("scheduleDate")));
                medical_schedules.setScheduleNamePatient(cursor.getString(cursor.getColumnIndex("scheduleNamePatient")));
                medical_schedules.setScheduleIdDoctor(cursor.getString(cursor.getColumnIndex("scheduleIdDoctor")));
                medical_schedules.setScheduleDetail(cursor.getString(cursor.getColumnIndex("scheduleDetail")));
                medical_schedules.setScheduleImage(cursor.getString(cursor.getColumnIndex("scheduleImage")));
                medical_schedules.setScheduleAcceptation(true);
                medical_schedules.setScheduleAlarm(cursor.getString(cursor.getColumnIndex("scheduleAlarm")));


                scheduleList.add(medical_schedules);
            } while (cursor.moveToNext());
        }
        return scheduleList;
    }


}
