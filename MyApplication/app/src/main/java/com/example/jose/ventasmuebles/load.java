package com.example.jose.ventasmuebles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class load extends AppCompatActivity implements AsyncResponse {
Globally g = Globally.getInstance();
    public static JSONObject jsonData;
    public EditText ts,en;
    Spinner pm;
    String pMa;
    public Button nx;
    int tag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abono_mensuales);
        g.setURL("http://single-lemonjuice.netau.net");
        checkSession();
    }

    public void response(){

        Intent main = new Intent(this,Main.class);
        main.putExtra("data", jsonData.toString());
        startActivity(main);

    }

    public void checkSession(){
        if(SaveSharedPreference.getUserName(load.this).length() == 0)
        {
            FirstUp();
        }
        else
        {
            try{

                jsonData = new JSONObject(fileData());
                Log.d("saved",jsonData.toString());
                Intent main = new Intent(this,Main.class);
                startActivity(main);

                tag = 1;
                response();
            }
            catch (Exception e){
                e.printStackTrace();
                SaveSharedPreference.clearUserName(this);
                Toast.makeText(this, "Error inesperado.", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    }

    public void Write(String data){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState())) {

            try {
                File txtfile = new File(getExternalFilesDir(null), "data.venta");
                FileWriter writer = new FileWriter(txtfile);
                BufferedWriter out = new BufferedWriter(writer);
                out.write(data);
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void conf(View v){
        String tasa = ts.getText().toString();
        String engan = en.getText().toString();

        if(tasa.equals("") || engan.equals("")){

            if(tasa.equals("")){
                ts.setError("No deje campos de texto en blanco");
            }else if(engan.equals("")){
                en.setError("No deje campos de texto en blanco");
            }

        }else{
            g.setTasaF(tasa);
            g.setEnganche(engan);
            g.setPlazoMaximo(pMa);
            try {
                JSONObject obj = new JSONObject();
                obj.put("tasa", tasa);
                obj.put("enganche", engan);
                obj.put("plazoM", pMa);
                Write(obj.toString());
                SaveSharedPreference.setUserName(this, "ventas");
                Intent main = new Intent(this,Main.class);
                startActivity(main);
            }catch(JSONException e){Log.d("json", e.getMessage());}

        }


    }

    public String fileData() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState())) {
            try {
                File jsonFile = new File(getExternalFilesDir(null) + "/data.venta");
                FileInputStream fis = new FileInputStream(jsonFile);
                BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
                String Line;
                String Text = "";
                while ((Line = buf.readLine()) != null) {
                    Text = Text + Line + "\n";
                }
                fis.close();
                return Text;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public void FirstUp(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ts = (EditText) findViewById(R.id.ts);
        en = (EditText) findViewById(R.id.en);
        nx = (Button) findViewById(R.id.nx);

        Spinner pm = (Spinner) findViewById(R.id.pm);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.plazos, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pm.setAdapter(adapter);

        pm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                pMa = selectedItemText+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @Override
    public void processFinish(String s) {
        Log.d("asycTask",s);
        if(s.equals("")){
            Toast.makeText(this, "No hay respuesta del servidor", Toast.LENGTH_LONG).show();
        }else{



        }
    }
}
