package com.calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import com.calendar.Adapter.MedicalSchedulesAdapter;
import com.calendar.Controller.ActivityChangePass;
import com.calendar.Controller.Activity_Login;
import com.calendar.Controller.CustomerInformation;
import com.calendar.Controller.ScheduleRegister;
import com.calendar.Model.Constants;
import com.calendar.Model.DatabaseHandler;
import com.calendar.Model.ExamSchedule;
import com.calendar.Model.Medical_Schedules_Model;
import com.calendar.Model.Users_Model;


public class MainActivity extends ActionBarActivity {
    DatabaseHandler db;
    Medical_Schedules_Model medical_schedules_model;
    MedicalSchedulesAdapter adapter;
    ListView lstView;
    String id_patient;
    Users_Model users_model;
    Constants constants;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
        id_patient= pre.getString("id_patients", "");
        if (id_patient.equals("")) {
            Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
            startActivity(intent);
        }
        else {
            db = new DatabaseHandler(this);
            users_model = new Users_Model(this);
            medical_schedules_model = new Medical_Schedules_Model(this);
            adapter = new MedicalSchedulesAdapter(this, medical_schedules_model.getSchedules());
            lstView = (ListView) findViewById(R.id.list_item_schedule);
            lstView.setAdapter(adapter);
            mDrawerList = (ListView) findViewById(R.id.navList);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mActivityTitle = getTitle().toString();

            addDrawerItems();
            setupDrawer();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#519fe1")));
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void addDrawerItems() {
        String[] osArray = {"Đổi Mật Khẩu", "Cập Nhật Thông Tin", "Đăng Xuất", "Trợ Giúp"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), ActivityChangePass.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), CustomerInformation.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                    startActivity(intent);
                }
                else if(position==3){
                    Toast.makeText(getApplication(), "Help", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), ScheduleRegister.class);
            intent.putExtra("patientId",id_patient);
            startActivity(intent);
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
