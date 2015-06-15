package com.calendar.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.calendar.Adapter.MedicalSchedulesAdapter;
import com.calendar.Model.Constants;
import com.calendar.Model.DatabaseHandler;
import com.calendar.Model.Medical_Schedules_Model;
import com.calendar.Model.Patient;
import com.calendar.Model.ServerRequest;
import com.calendar.Model.Users_Model;
import com.calendar.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityChangePass extends ActionBarActivity {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Button btnChangePass;
    int jsonstr;
    String jsonid;
    String id_patient;
    Constants constants;
    Users_Model users_model;
    Patient patient;
    EditText edit_OldPass, edit_NewPass, edit_Re_NewPass;
    String txt_old_pass, txt_new_pass, txt_re_new_pass;
    List<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getEditText();
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
        id_patient = pre.getString("id_patients", "");
        users_model = new Users_Model(this);
        patient = new Patient();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEditText();
                if (checkNull()) {
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id", id_patient));
                    params.add(new BasicNameValuePair("password", txt_new_pass));
                    ServerRequest sr = new ServerRequest();
                    JSONObject json = sr.getJSON(constants.CONST_URL_CHANGE_PASS, params);
                    json=json;
                    if (json != null) {
                        try {
                            String aa=json.getString("status");
                            jsonstr=Integer.parseInt(aa);
                         //   jsonstr = json.getInt("status");


                            if (jsonstr > 0) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
                                builder1.setTitle("Kết quả thay đổi mật khẩu");
                                builder1.setMessage("Lấy Mật khẩu Thành công!");
                                builder1.setCancelable(true);
                                builder1.setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                users_model.UpdatePass(id_patient,txt_new_pass);
                                                edit_OldPass.setText("");
                                                edit_NewPass.setText("");
                                                edit_Re_NewPass.setText("");
                                                Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                                                startActivity(intent);
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            } else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
                                builder1.setTitle("Kết Quả Thay đổi Mật khẩu");
                                builder1.setMessage("Bạn nên Thay đổi vào Lần sau");
                                builder1.setCancelable(true);
                                builder1.setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
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
                    Toast.makeText(getApplication(), jsonstr + "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Boolean checkNull() {
        callEditText();
        if (txt_old_pass.equals("")) {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
            builder1.setTitle("L?i");
            builder1.setMessage("Vui Lòng nhập Mật khẩu mới");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else if (txt_new_pass.equals("")) {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
            builder1.setTitle("L?i");
            builder1.setMessage("Vui Lòng nhập Mật khẩu mới!");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else if (txt_old_pass.equals("")) {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
            builder1.setTitle("L?i");
            builder1.setMessage("Vui Lòng nhập lại Mật khẩu mới!");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
            String id_patient = pre.getString("id_patients", "");
            Users_Model users_model = new Users_Model(this);
            Patient patient = new Patient();
            String pass = "";
            if (!id_patient.equals("")) {
                patient = users_model.getAll_Information_User(id_patient);
                pass = patient.getPassword().toString();
                if (!pass.equals(txt_old_pass)) {
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
                    builder1.setTitle("Lỗi");
                    builder1.setMessage("Mật Khẩu Sai,Vui Lòng Nhập Lại!");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    edit_OldPass.setText("");
                                    edit_NewPass.setText("");
                                    edit_Re_NewPass.setText("");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if (!txt_new_pass.equals(txt_re_new_pass)) {
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityChangePass.this);
                    builder1.setTitle("Lỗi");
                    builder1.setMessage("Mật Khẩu Không Giống Nhau!");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    edit_NewPass.setText("");
                                    edit_Re_NewPass.setText("");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    return true;
                }
            }

        }
        return false;
    }

    private void getEditText() {
        edit_NewPass = (EditText) findViewById(R.id.matkhaumoi);
        edit_Re_NewPass = (EditText) findViewById(R.id.nhaplaimatkhaumoi);
        edit_OldPass = (EditText) findViewById(R.id.matkhauhientai);
        btnChangePass = (Button) findViewById(R.id.btndangky);
    }

    private void callEditText() {
        txt_old_pass = edit_OldPass.getText().toString();
        txt_new_pass = edit_NewPass.getText().toString();
        txt_re_new_pass = edit_Re_NewPass.getText().toString();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_change_pass, menu);
        return true;
    }
}
