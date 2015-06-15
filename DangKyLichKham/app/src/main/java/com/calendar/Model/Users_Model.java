package com.calendar.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.logging.Log;

import java.util.ArrayList;

/**
 * Created by nguyenhoang on 5/30/2015.
 */
public class Users_Model {
   public  DatabaseHandler handler;

    public Users_Model(Context context) {
        handler = new DatabaseHandler(context);
    }
    public void Add_Patient(String patientId,String  patientEmail,String patientPassword,String patientFullName,String patientGender,String patientAddress,String patientIdentification,String patientCountry,String patientPhone,String patientBirthday){
        SQLiteDatabase db = handler.getWritableDatabase();
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
        newValues.put("patientBirthday", patientBirthday);
        db.insert(handler.TABLE_PATIENT, null, newValues);
        db.close();
    }
    public void Update_Patient(String patientId,String  patientEmail,String patientFullName,String patientGender,String patientAddress,String patientIdentification,String patientPhone,String patientBirthday) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("patientEmail",patientEmail );
        newValues.put("patientFullName",patientFullName );
        newValues.put("patientGender",patientGender );
        newValues.put("patientAddress", patientAddress);
        newValues.put("patientIdentification",patientIdentification );
        newValues.put("patientPhone",patientPhone );
        newValues.put("patientBirthday", patientBirthday);

        db.update(handler.TABLE_PATIENT, newValues, "patientId=?",
                new String[]{patientId});
        db.close();
    }

    public Patient getAll_Information_User(String patientId) {

        SQLiteDatabase db = handler.getWritableDatabase();
        String sql = "SELECT * FROM '" + handler.TABLE_PATIENT+ "'" + " WHERE patientId= '" + patientId + "'";
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        Patient c = new Patient();
        while(!cursor.isAfterLast()){
               // c.setEmail(cursor.getString(cursor.getColumnIndex("patientId")));
                c.setPassword(cursor.getString(cursor.getColumnIndex("patientPassword")));
                c.setEmail(cursor.getString(cursor.getColumnIndex("patientEmail")));
                c.setFullname(cursor.getString(cursor.getColumnIndex("patientFullName")));
                c.setGender(cursor.getString(cursor.getColumnIndex("patientGender")));
                c.setAddress(cursor.getString(cursor.getColumnIndex("patientAddress")));
                c.setBirthday(cursor.getString(cursor.getColumnIndex("patientBirthday")));
                c.setPhonenumber(cursor.getString(cursor.getColumnIndex("patientPhone")));
                c.setIndentification(cursor.getString(cursor.getColumnIndex("patientIdentification")));
            cursor.moveToNext();

            }
        return c;
    }
    public Boolean UpdatePass(String patientId,String patientPassword ){
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues contentValue =new ContentValues();
        contentValue.put("patientPassword", patientPassword);
        return db.update(handler.TABLE_PATIENT, contentValue, "patientId='"+patientId+"'", null)>0;
    }

}
