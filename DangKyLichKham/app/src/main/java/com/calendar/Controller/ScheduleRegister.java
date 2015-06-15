package com.calendar.Controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.calendar.MainActivity;
import com.calendar.Model.Constants;
import com.calendar.Model.Doctor;
import com.calendar.Model.Doctors_Model;
import com.calendar.Model.Medical_Schedules;
import com.calendar.Model.Medical_Schedules_Model;
import com.calendar.Model.Patient;
import com.calendar.Model.ServerRequest;
import com.calendar.Model.Users_Model;
import com.calendar.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ScheduleRegister extends ActionBarActivity {
    Spinner sn_Doctor, sn_Chuyen_Khoa;
    List<Doctor> listDoctor, listDepartment;
    Doctors_Model doctors_model;
    Constants constants;
    List<NameValuePair> params;
    JSONArray doctors = null;
    JSONArray department = null;
    ImageButton btnDate, btnTime;
    EditText edit_Date, edit_Time, edit_Detail, edit_FullName;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;
    Button btn_Accept, btn_Cancel;
    String imc_met, fullNameDoctor, fullNamePatient = "";
    Users_Model users_model;
    Medical_Schedules_Model medical_schedules_model;
    Patient patient;
    RadioGroup radioGroupPatient;
    RadioButton rbPatient, rbFamily;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_register);

        getFormWidgets();
        getDefaultInfor();
        medical_schedules_model=new Medical_Schedules_Model(this);
        users_model = new Users_Model(this);
        patient = new Patient();
        SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
        String id_patient = pre.getString("id_patients", "");
        if (id_patient.equals("")) {
            Intent iin = getIntent();
            Bundle b = iin.getExtras();

            if (b != null) {
                id = (String) b.get("id_patients");

            }
        } else {
            id = id_patient;
        }
        patient = users_model.getAll_Information_User(id);
        if (!patient.equals("")) {
            fullNamePatient = patient.getFullname().toString();
            edit_FullName.setText(fullNamePatient);
        }


        doctors_model = new Doctors_Model(this);
        //listDoctor=new ArrayList<Doctor>();
        // listDoctor=doctors_model.Get_All_Doctors();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btn_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_Detail.getText().toString().equals("") && edit_FullName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Tên Và Triệu Chứng", Toast.LENGTH_SHORT).show();
                }else {

                    if (edit_Detail.getText().toString().equals("")) {
                        Toast toast=Toast.makeText(getApplicationContext(), "Vui Lòng Điền Triệu Chứng Để Thuận Tiện Cho Việc Khám Bệnh", Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        if (edit_FullName.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Vui Lòng Điền Tên Bệnh Nhân Cần Khám", Toast.LENGTH_SHORT).show();
                        } else
                            result();
                    }
                }

            }
        });

        ServerRequest sr = new ServerRequest();
        JSONObject jsonDoctor = sr.getJSONDOCTOR(constants.CONST_URL_GET_DOCTOR);
        ArrayList<String> listDepartment = new ArrayList<String>();
        if (jsonDoctor != null) {
            try {
                Doctor dt = new Doctor();

                department = jsonDoctor.getJSONArray("data");
                for (int i = 0; i < department.length(); i++) {
                    JSONObject value = department.getJSONObject(i);
                    dt.setDoctorId(value.getString("_id"));
                    dt.setDoctorDepartment(value.getString("department"));
                    dt.setDoctorFullName(value.getString("fullname"));
                    dt.setDoctorImage("");

                    doctors_model.Add_Doctors_Model(dt);
                    if (listDepartment.contains(value.getString("department"))) {

                    } else listDepartment.add(value.getString("department"));

                }
                sn_Chuyen_Khoa.setAdapter(new ArrayAdapter<String>(
                        getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item, listDepartment));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        rbPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPatient.isChecked()) {
                    edit_FullName.setText(fullNamePatient);
                } else edit_FullName.setText("");
            }
        });
        rbFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbFamily.isChecked()) {
                    edit_FullName.setText("");
                } else edit_FullName.setText("fullNamePatient");
            }
        });

        sn_Chuyen_Khoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imc_met = sn_Chuyen_Khoa.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), imc_met, Toast.LENGTH_LONG).show();
//                ServerRequest sr = new ServerRequest();
//                JSONObject jsonDoctor = sr.getJSONDOCTOR(constants.CONST_URL_GET_LIST_DOCTOR + imc_met);
//                ArrayList<String> listDoctor = new ArrayList<String>();
//                if (jsonDoctor != null) {
//                    try {
//
//                        doctors = jsonDoctor.getJSONArray("data");
//                        for (int i = 0; i < doctors.length(); i++) {
//                            JSONObject value = doctors.getJSONObject(i);
//                            listDoctor.add(value.getString("fullname"));
//                        }
//                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
//                                getBaseContext(),
//                                android.R.layout.simple_spinner_dropdown_item, listDoctor);
//                        sn_Doctor.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                //               }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item, getSpinner(imc_met));
                sn_Doctor.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sn_Doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fullNameDoctor = sn_Doctor.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

    }

    private void getFormWidgets() {
        edit_Date = (EditText) findViewById(R.id.edit_Date);
        edit_Time = (EditText) findViewById(R.id.edit_Time);
        edit_Detail = (EditText) findViewById(R.id.edit_Trieuchung);
        edit_FullName = (EditText) findViewById(R.id.txt_tenbenhnhan);
        btnDate = (ImageButton) findViewById(R.id.btn_img_Date);
        btnTime = (ImageButton) findViewById(R.id.btn_img_Time);
        sn_Doctor = (Spinner) findViewById(R.id.combo_bacsi);
        sn_Chuyen_Khoa = (Spinner) findViewById(R.id.combo_Chuyenkhoa);
        btn_Accept = (Button) findViewById(R.id.btn_Xacnhan);
        btn_Cancel = (Button) findViewById(R.id.btn_Huy);
        radioGroupPatient = (RadioGroup) findViewById(R.id.radioPatient);
        rbPatient = (RadioButton) findViewById(R.id.rad_Banthan);
        rbFamily = (RadioButton) findViewById(R.id.rad_Nguoithan);

    }

    private void Reset() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getDefaultInfor() {
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        edit_Date.setText(strDate);
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        edit_Time.setText(strTime);
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        edit_Time.setTag(dft.format(cal.getTime()));

        dateFinish = cal.getTime();
        hourFinish = cal.getTime();
    }

    private ArrayList<String> getSpinner(String sss) {

        ServerRequest sr = new ServerRequest();
        JSONObject jsonDoctor = sr.getJSONDOCTOR(constants.CONST_URL_GET_LIST_DOCTOR + sss);
        ArrayList<String> listDoctor = new ArrayList<String>();
        if (jsonDoctor != null) {
            try {
                doctors = jsonDoctor.getJSONArray("data");

                for (int i = 0; i < doctors.length(); i++) {
                    JSONObject value = doctors.getJSONObject(i);
                    listDoctor.add(value.getString("fullname"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listDoctor;

    }

    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                edit_Date.setText(
                        (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        String s = edit_Date.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                ScheduleRegister.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                edit_Time.setText
                        (hourTam + ":" + minute + (hourOfDay > 12 ? " PM" : " AM"));
                edit_Time.setTag(s);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish = cal.getTime();
            }
        };
        String s = edit_Time.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        TimePickerDialog time = new TimePickerDialog(
                ScheduleRegister.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }

    private void result() {
        Medical_Schedules medical_schedules=new Medical_Schedules();

        String doctorId = "";
        String patientId = "";
        String scheduleId="";
        doctorId = doctors_model.GetIdDoctor(fullNameDoctor, imc_met);
        edit_Date.getText();
        edit_Detail.getText();
        patientId = id;
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("fullname", edit_FullName.getText().toString()));
        params.add(new BasicNameValuePair("date", edit_Date.getText().toString()));
        params.add(new BasicNameValuePair("detail", edit_Detail.getText().toString()));
        params.add(new BasicNameValuePair("id_doctor", doctorId));
        params.add(new BasicNameValuePair("id_user", patientId));
        params.add(new BasicNameValuePair("department",imc_met));
        medical_schedules.setScheduleNamePatient(edit_FullName.getText().toString());
        medical_schedules.setScheduleDate(edit_Date.getText().toString());
        medical_schedules.setScheduleDetail(edit_Detail.getText().toString());
        medical_schedules.setScheduleIdDoctor(doctorId);
        medical_schedules.setScheduleImage("");
        medical_schedules.setScheduleAcceptation(true);
        medical_schedules.setScheduleAlarm("");
        ServerRequest sr = new ServerRequest();
        JSONObject json = sr.getJSON(constants.CONST_URL_POST_SCHEDULE, params);
        //Toast.makeText(getApplicationContext(),"DDIen máu",Toast.LENGTH_SHORT).show();
        if (json != null) {
            try {
                int jsonstr = json.getInt("status");

              //  String message = json.getString("message");
                scheduleId= json.getString("id");
                json.getString("number");
                json.getString("time");

                if (jsonstr > 0) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ScheduleRegister.this);
                    builder1.setTitle("Kết Quả Đăng Ký");
                    builder1.setMessage("Bạn Đã Đăng Ký Khám Bệnh Thành Công,\n Số Thứ Tự Và Thời Gian Khám Của Bạn Là :"+json.getString("number")+" và "+json.getString("time")+" \n Vui Lòng Đến Trước 10' . Cảm ơn !");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(ScheduleRegister.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ScheduleRegister.this);
                    builder1.setTitle("Kết Quả Đăng Ký");
                    builder1.setMessage("Bạn Đã Đăng Ký Khám Bệnh Thất Bại. Vui Lòng Đăng Ký Lại. Cảm ơn bạn rất nhiều !");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Reset();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplication(), "Null json", Toast.LENGTH_LONG).show();
        }
        medical_schedules.setScheduleId(scheduleId);
       medical_schedules_model.Add_Medical_Schedules(medical_schedules);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dangky, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
