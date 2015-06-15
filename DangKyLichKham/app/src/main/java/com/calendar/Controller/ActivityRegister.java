package com.calendar.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calendar.LoginDataBaseAdapter;
import com.calendar.Model.Constants;
import com.calendar.Model.DatabaseHandler;
import com.calendar.Model.ServerRequest;
import com.calendar.Model.Users_Model;
import com.calendar.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class ActivityRegister extends Activity {
    EditText editTextEmail,editTextPassword,editTextConfirmPassword,editTextHoTen;
    Button btnCreateAccount;
    LoginDataBaseAdapter loginDataBaseAdapter;
    DatabaseHandler databaseHandler;
    List<NameValuePair> params;
    Constants constants;
    Context context;
    Users_Model users_model;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHandler=new DatabaseHandler(context);
//        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
//        loginDataBaseAdapter=loginDataBaseAdapter.open();
        users_model=new Users_Model(this);
        editTextEmail=(EditText) findViewById(R.id.emaildangnhap);
        editTextPassword=(EditText) findViewById(R.id.matkhau);
        editTextHoTen=(EditText) findViewById(R.id.hoten);
        editTextConfirmPassword=(EditText) findViewById(R.id.nhaplaimatkhau);

        btnCreateAccount=(Button) findViewById(R.id.btndangky);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String hoten=editTextHoTen.getText().toString();

                if(!checkEmail(email)){
                    Toast.makeText(getApplication(), "Email không hợp lệ!", Toast.LENGTH_LONG).show();
                    return;
                }

                String confirmPassword=editTextConfirmPassword.getText().toString();
                if(email.equals("") || password.equals("") || confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin yêu cầu.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    //loginDataBaseAdapter.insertEntry(username,password);
                   // Toast.makeText(getApplicationContext(),"Account Successfully Created", Toast.LENGTH_LONG).show();
                }
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(constants.UR_EMAIL, email));
                params.add(new BasicNameValuePair(constants.UR_PASS, password));
                params.add(new BasicNameValuePair(constants.UR_FULL_NAME, hoten));
                ServerRequest sr = new ServerRequest();
                JSONObject json = sr.getJSON(constants.CONST_URL_SIGN_UP, params);
                if(json != null){
                    try{
                        int jsonstr = json.getInt("status");
                        String jsonid=json.getString("id");
                        if(jsonstr>0){
                            users_model.Add_Patient(jsonid,email,password,hoten,"","","","","","");
                            Toast.makeText(getApplicationContext(),"Account Successfully Created", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplication(), jsonstr+"", Toast.LENGTH_LONG).show();
                            Intent activity_login = new Intent(ActivityRegister.this,Activity_Login.class);
                            startActivity(activity_login);
                            finish();
                        }
                        else Toast.makeText(getApplication(), "Thất bại", Toast.LENGTH_LONG).show();

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
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
