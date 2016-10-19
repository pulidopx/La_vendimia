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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.Random;

public class Registro_clientes extends AppCompatActivity implements AsyncResponse{
Random generator;
    String clave;
    TextView clav,fech;
    EditText nombre,AP,AM,rfc;
    Button saved;
    Globally g = Globally.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clientes);
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
                if (rfc.getText().toString().matches("([a-zA-Z0-9]{17,})+[0-9]{1,}") && s.length() > 0) {
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

        String[] fecha =g.getFecha().split("-");
        fech = (TextView) findViewById(R.id.fecha);
        fech.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);

        rand();
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
            httpost.setPostData(postData);
            httpost.execute("http://clementepruebas.000webhostapp.com/ventas/registrar_cliente.php");
        }

    }

    public void close(View v){
        alert();
    }


    @Override
    public void onBackPressed()
    {
        alert();
    }

    public void rand(){
        generator = new Random();
        int n1 = Math.abs(generator.nextInt() % 6);
        int n2 = Math.abs(generator.nextInt() % 6);
        int n3 = Math.abs(generator.nextInt() % 6);
        clave = ""+n1+n2+n3;
        clav.setText("clave:"+n1+n2+n3);
    }


    @Override
    public void processFinish(String s) {

        if(s.equals("3")){
            Toast.makeText(this, "El usuario ya se encuentra registrado", Toast.LENGTH_SHORT).show();
        }else if(s.equals("1")){
            nombre.setText("");
            Toast.makeText(this, "Bien Hecho. El cliente ha sido registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }else if(s.equals("2")){
            Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
        }else if(s.equals("")){
            Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
        }

    }
}
