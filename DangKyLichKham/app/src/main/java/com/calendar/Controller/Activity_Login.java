package com.calendar.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calendar.MainActivity;
import com.calendar.Model.Constants;
import com.calendar.Model.ServerRequest;
import com.calendar.LoginDataBaseAdapter;
import com.calendar.R;

import org.apache.commons.logging.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Activity_Login extends Activity {
    Button btnLogin,btnSignup, btnFogetPass;
    EditText email,password;
    String  emailtxt,passwordtxt;
    List<NameValuePair> params;
    int jsonstr;
    String jsonid;
    Constants constants;
    JSONArray doctors = null;
    SharedPreferences.Editor edit;
    public static String id_patients;

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        edit=pre.edit();
        ServerRequest sr = new ServerRequest();
//        JSONObject jsonDoctor = sr.getJSON(constants.CONST_URL_GET_DOCTOR,params);
//        ArrayList<String> listDoctor = new ArrayList<String>();
//        if(jsonDoctor!=null){
//            try{
//
//                doctors = jsonDoctor.getJSONArray("data");
//                for(int i = 0; i < doctors.length(); i++){
//                    JSONObject value = doctors.getJSONObject(i);
//                    listDoctor.add(value.getString("fullname"));
//
//                }
//                spiner.setAdapter(new ArrayAdapter<String>(
//                        getBaseContext(),
//                        android.R.layout.simple_spinner_dropdown_item, listDoctor));
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//
//        }
        setContentView(R.layout.activity__login);
        email=(EditText)findViewById(R.id.emaillogin);
        password=(EditText)findViewById(R.id.pwlogin);
        btnLogin=(Button)findViewById(R.id.btnsignin);
        btnSignup=(Button)findViewById(R.id.btnsignup);
        btnFogetPass=(Button) findViewById(R.id.btnFogetPass);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_sign_up = new Intent(Activity_Login.this,ActivityRegister.class);
                startActivity(activity_sign_up);
                finish();
            }
        });
        btnFogetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Activity_Login.this,ActivityFogetPass.class));
                finish();
            }
        });

        //check email regex
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();

                //check email by regex
                if(!checkEmail(emailtxt)){
                   Toast.makeText(getApplication(), "Email không hợp lệ", Toast.LENGTH_LONG).show();
                    return;
                }
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", emailtxt));
                params.add(new BasicNameValuePair("password", passwordtxt));
                ServerRequest sr = new ServerRequest();
                JSONObject json = sr.getJSON(constants.CONST_URL_LOGIN,params);
                if(json != null){
                    try{

                        jsonstr = json.getInt("status");

                         jsonid=json.getString("id");
                        id_patients=jsonid;
                        edit.putString("id_patients",id_patients);
                        edit.commit();
                        if(jsonstr>0){
                            Intent intent =new Intent(getApplicationContext(),CustomerInformation.class);
                            intent.putExtra("patientId",jsonid);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Login.this);
                            builder1.setTitle("Đăng Nhập");
                            builder1.setMessage("Bạn Chưa Có Tài Khoản, Vui Lòng Đăng Ký");
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

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplication(), "Null json", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplication(), jsonstr+"", Toast.LENGTH_LONG).show();
            }

        });





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

        return super.onOptionsItemSelected(item);
    }
}
