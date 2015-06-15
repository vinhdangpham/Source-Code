package com.calendar.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.LogManager;
/**
 * Created by nguyenhoang on 6/1/2015.
 */
public class Doctors_Model {
    DatabaseHandler handler;
    private String doctorId;
    private String doctorDepartment;
    private String doctorFullName;
    private String doctorImage;

    public Doctors_Model(Context context) {
        handler = new DatabaseHandler(context);
    }
    public void Add_Doctors_Model(Doctor dt){
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("doctorId", dt.getDoctorId());
        values.put("doctorDepartment", dt.getDoctorDepartment());
        values.put("doctorFullName", dt.getDoctorFullName());
        values.put("doctorImage", dt.getDoctorImage());

        db.insert(handler.TABLE_DOCTOR, null, values);
        db.close();

    }
    public ArrayList<Doctor> Get_All_Doctors(){
        SQLiteDatabase db = handler.getReadableDatabase();
        String sql = "SELECT * FROM '" + handler.TABLE_DOCTOR + "'";
        ArrayList<Doctor> listDoctor = new ArrayList<Doctor>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

            do {

                Doctor doctor = new Doctor();
                doctor.setDoctorId(cursor.getString(cursor.getColumnIndex("doctorId")));
                doctor.setDoctorDepartment(cursor.getString(cursor.getColumnIndex("doctorDepartment")));
                doctor.setDoctorFullName(cursor.getString(cursor.getColumnIndex("doctorFullName")));
                doctor.setDoctorImage(cursor.getString(cursor.getColumnIndex("doctorImage")));

                listDoctor.add(doctor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listDoctor;
    }

    public void Update_Doctors_Model(Doctor dt) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("doctorId", dt.getDoctorId());
        values.put("doctorDepartment", dt.getDoctorDepartment());
        values.put("doctorFullName", dt.getDoctorFullName());
        values.put("doctorImage", dt.getDoctorImage());

        db.update(handler.TABLE_DOCTOR, values, "doctorId=?",
                new String[]{String.valueOf(dt.getDoctorId())});
        db.close();
    }

    public void Delete__Doctors_Model(Doctor dt) {
        SQLiteDatabase db = handler.getWritableDatabase();
        db.delete(handler.TABLE_DOCTOR, "doctorId=?",
                new String[]{String.valueOf(dt.getDoctorId())});
        db.close();
    }
    public String  GetIdDoctor(String doctorFullName,String doctorDepartment){
       // String sql = "SELECT doctorId FROM '" + handler.TABLE_DOCTOR + "' WHERE tbDOCTOR.doctorFullName='"  +doctorFullName + "' AND  tbDOCTOR.doctorDepartment='" + doctorDepartment + "';";
        String result="aaaaa";
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT doctorId FROM '" + handler.TABLE_DOCTOR + "' WHERE tbDOCTOR.doctorFullName='"  +doctorFullName + "' AND  tbDOCTOR.doctorDepartment='" + doctorDepartment + "'", null);
        if (cursor != null&&cursor.moveToFirst()) {

            result  = cursor.getString(0).toString();
        }

        cursor.close();
        db.close();
        return result;

    }

}
