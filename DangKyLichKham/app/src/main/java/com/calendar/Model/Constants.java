package com.calendar.Model;

/**
 * Created by nguyenhoang on 5/30/2015.
 */
public class Constants {
    public static String patientId="";
    //Table DOCTOR
    public static final String TABLE_DOCTOR="Doctor";
    public static final String DR_EXPERIENCE="experience";
    public static final String DR_CERTIFICATE="certificate";
    public static final String DR_ADDRESS="address";
    public static final String DR_GENDER ="gender";
    public static final String DR_BIRTH_DAY="birthday";
    public static final String DR_FULL_NAME="fullname";
    public static final String DR_DEPARTMENT="department";
    public static final String DR_DOCTOR="doctor";

    // TABLE USERS
    public static final String TABLE_USER="user";
    public static final String UR_EMAIL="email";
    public static final String UR_PASS="password";
    public static final String UR_COUNTRY="country";
    public static final String UR_INDENTIFICATION = "indentification";
    public static final String UR_ADDRESS="address";
    public static final String UR_GENDER ="gender";
    public static final String UR_FULL_NAME="fullname";
    public static final String UR_PHONE="phone";



    public static final String CONST_SERVER="http://10.0.3.2:3000/";

    public static final String CONST_URL_LOGIN=CONST_SERVER + "login";
    public static final String CONST_URL_SIGN_UP=CONST_SERVER + "user";
    public static final String CONST_URL_GET_DOCTOR=CONST_SERVER + "doctor";
    public static final String CONST_URL_GET_LIST_DOCTOR=CONST_SERVER +"department/";
    public static final String CONST_URL_POST_SCHEDULE=CONST_SERVER + "exam/";
    public static final String CONST_URL_POST_CHANGE_PASSWORD=CONST_SERVER + "user/infor/";
    public static final String CONST_URL_CHANGE_PASS=CONST_SERVER + "user/password";


}
