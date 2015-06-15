package com.calendar.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.calendar.Model.Constants;
import com.calendar.Model.ServerRequest;
import com.calendar.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityFogetPass extends Activity {

    List<NameValuePair> params;
    int jsonstr;
    String jsonid;
    Constants constants;
    Button btnLayMK;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pass);
        btnLayMK=(Button) findViewById(R.id.btnLayMK);
        editText=(EditText) findViewById(R.id.emailFogetPass);
        btnLayMK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.getText().toString();
                if(!editText.getText().toString().equals("")){
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("email", editText.getText().toString()));
                    ServerRequest sr = new ServerRequest();
                    JSONObject json = sr.getJSON(constants.CONST_URL_POST_CHANGE_PASSWORD,params);
                    if(json != null){
                        try{

                            jsonstr = json.getInt("status");
                            if(jsonstr>0){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityFogetPass.this);
                                builder1.setTitle("Ket Qua Reset Password");
                                builder1.setMessage("Mật khẩu đã được gửi tới Email của bạn!");
                                builder1.setCancelable(true);
                                builder1.setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent activity_login = new Intent(ActivityFogetPass.this,Activity_Login.class);
                                                startActivity(activity_login);
                                                finish();
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
                }




            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_foget_pass, menu);
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
