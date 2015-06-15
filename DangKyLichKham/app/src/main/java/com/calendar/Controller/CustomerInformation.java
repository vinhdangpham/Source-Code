package com.calendar.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.calendar.MainActivity;
import com.calendar.Model.Patient;
import com.calendar.Model.Users_Model;
import com.calendar.R;

import org.apache.commons.logging.Log;

import java.util.ArrayList;


public class CustomerInformation extends ActionBarActivity {

    Users_Model users_model;
    EditText txtFullName, txtBirthday, txtAddress, txtIndent, txtPhone, txtEmail;
    RadioButton rbGenderMale, rbGenderFemale, radioSexButton;
    RadioGroup rg;
    Button btnSave;
    String id, patientId, patientEmail, patientPassword, patientFullName, patientGender, patientAddress, patientIdentification, patientCountry, patientPhone, patientBirthday;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
        String id_patient = pre.getString("id_patients", "");
        if (id_patient.equals("")) {
            Intent iin = getIntent();
            Bundle b = iin.getExtras();

            if (b != null) {
                id = (String) b.get("patientId");

            }
        } else {
            id = id_patient;
        }
        txtFullName = (EditText) findViewById(R.id.edit_Hoten);
        txtBirthday = (EditText) findViewById(R.id.edit_Ngaysinh);
        txtAddress = (EditText) findViewById(R.id.edit_Diachi);
        txtIndent = (EditText) findViewById(R.id.edit_Cmnd);
        txtPhone = (EditText) findViewById(R.id.edit_Sdt);
        txtEmail = (EditText) findViewById(R.id.edit_Email);
        rbGenderMale = (RadioButton) findViewById(R.id.rad_Nam);
        rbGenderFemale = (RadioButton) findViewById(R.id.rad_Nu);
        rg = (RadioGroup) findViewById(R.id.rg);
        btnSave = (Button) findViewById(R.id.btn_save);
        users_model = new Users_Model(this);
        Patient patient = new Patient();
        patient = users_model.getAll_Information_User(id);
        if (patient != null) {
            txtFullName.setText(patient.getFullname().toString());
            txtAddress.setText(patient.getAddress().toString());
            txtBirthday.setText(patient.getBirthday().toString());
            txtIndent.setText(patient.getIndentification().toString());
            txtPhone.setText(patient.getPhonenumber().toString());
            txtEmail.setText(patient.getEmail().toString());
            if (patient.getGender().toString() == "Nam") {
                rbGenderMale.setChecked(false);
                rbGenderFemale.setChecked(true);
            } else {
                rbGenderMale.setChecked(true);
                rbGenderFemale.setChecked(false);
            }
        } else {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = rg.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                patientEmail = txtEmail.getText().toString();
                patientFullName = txtFullName.getText().toString();
                patientGender = radioSexButton.getText().toString();
                patientAddress = txtAddress.getText().toString();
                patientIdentification = txtIndent.getText().toString();
                patientPhone = txtPhone.getText().toString();
                patientBirthday = txtBirthday.getText().toString();
                users_model.Update_Patient(id, patientEmail, patientFullName, patientGender, patientAddress, patientIdentification, patientPhone, patientBirthday);
                Intent intent = new Intent(CustomerInformation.this, MainActivity.class);
                startActivity(intent);

            }
        });

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
                } else if (position == 3) {
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
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
