package com.example.jose.ventasmuebles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class editar_usuario extends AppCompatActivity implements AsyncResponse{
    Random generator;
    String clave;
    TextView clav,fecha;
    EditText nombre,AP,AM,rfc;
    Button saved;
    Globally g = Globally.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clav = (TextView) findViewById(R.id.claveC);

        nombre = (EditText) findViewById(R.id.nombre);
        AP = (EditText) findViewById(R.id.AP);
        AM = (EditText) findViewById(R.id.AM);
        rfc = (EditText) findViewById(R.id.rfc);

        saved = (Button) findViewById(R.id.save);

        rfc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rfc.getText().toString().matches("([a-zA-Z0-9]{9,})+[0-9]{1,}") && s.length() > 0) {
                    //  passval.setTextColor(Color.GREEN);
                    // Toast.makeText(getApplicationContext(),"E-mail válido",Toast.LENGTH_LONG).show();
                    saved.setVisibility(View.VISIBLE);
                } else {
                    rfc.setError("Curp no valido.");
                    // Toast.makeText(getApplicationContext(),"E-mail no válido",Toast.LENGTH_LONG).show();
                    saved.setVisibility(View.INVISIBLE);
                }
            }
        });



        String data = getIntent().getStringExtra("data");
        try {
            JSONObject date = new JSONObject(data);
            nombre.setText(date.getString("nombre").toString());
            AP.setText(date.getString("apellido_p"));
            AM.setText(date.getString("apellido_m"));
            rfc.setText(date.getString("rfc"));
            Log.d("json", date.toString());
            clav.setText("clave:"+date.getString("clave_cliente"));
            clave = date.getString("clave_cliente");
        } catch (JSONException e) {
            e.getMessage();
        }

    }


    public void alert(){

        AlertDialog alert = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro de salir de la pantalla actual?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                }).setTitle("Alerta!").setIcon(android.R.drawable.ic_menu_report_image).create();
        alert = builder.create();
        alert.show();
    }

    public void registrarCliente(View v){
        String nom = nombre.getText().toString();
        String App = AP.getText().toString();
        String Apm = AM.getText().toString();
        String rf = rfc.getText().toString();
        if(nom.equals("") || App.equals("") || Apm.equals("") || rf.equals("")){
            Toast.makeText(this, "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(nom.equals("")){nombre.setError("Campo obligatorio!");}
            if(App.equals("")){AP.setError("Campo obligatorio!");}
            if(Apm.equals("")){AM.setError("Campo obligatorio!");}
            if(rf.equals("")){rfc.setError("Campo obligatorio!");}
        }else{
            HashMap postData = new HashMap();
            PostResponseAsyncTask httpost = new PostResponseAsyncTask(this, this);
            postData.put("nombre",nom);
            postData.put("app",App);
            postData.put("apm",Apm);
            postData.put("rfc",rf);
            postData.put("key",clave);
            Log.d("msg",postData.toString());
            httpost.setPostData(postData);
            httpost.execute(g.getURL()+"/ventas/editar_cliente.php");
        }

    }


    @Override
    public void onBackPressed()
    {
        alert();
    }

    public void close(View v){
        alert();
    }


    @Override
    public void processFinish(String s) {
        String st[] = s.split("-");
       Log.d("msg",st[0]);
       if(st[0].equals("1")){
            Toast.makeText(this, "Bien Hecho. Se han editado los datos del cliente", Toast.LENGTH_SHORT).show();
           finish();
        }else if(st[0].equals("2")){
            Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
        }else if(st[0].equals("")){
            Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
        }

    }
}
