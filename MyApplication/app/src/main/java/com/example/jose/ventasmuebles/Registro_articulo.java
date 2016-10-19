package com.example.jose.ventasmuebles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.Random;

public class Registro_articulo extends AppCompatActivity implements AsyncResponse {
    Random generator;
    String clave;
    TextView clav,fech;
    EditText des,precio,modelo,existencia;
    Button saved;
    Globally g = Globally.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_articulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clav = (TextView) findViewById(R.id.claveA);

        des = (EditText) findViewById(R.id.descripcion);
        precio = (EditText) findViewById(R.id.precio);
        modelo = (EditText) findViewById(R.id.modelo);
        existencia = (EditText) findViewById(R.id.existencia);

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

    public void registrarArticulo(View v){
        String desc = des.getText().toString();
        String pre = precio.getText().toString();
        String mod = modelo.getText().toString();
        String exis = existencia.getText().toString();
        if(des.equals("") || pre.equals("") || mod.equals("") || exis.equals("") || exis.equals("0")){
            Toast.makeText(this, "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(desc.equals("")){des.setError("Campo obligatorio!");}
            if(pre.equals("")){precio.setError("Campo obligatorio!");}
            if(mod.equals("")){modelo.setError("Campo obligatorio!");}
            if(exis.equals("")){existencia.setError("Campo obligatorio!");}
            if(exis.equals("0")){existencia.setError("No puede agregar articulo sin existencia!");}
        }else{
            HashMap postData = new HashMap();
            PostResponseAsyncTask httpost = new PostResponseAsyncTask(this, this);
            postData.put("des",desc);
            postData.put("pre",pre);
            postData.put("mod",mod);
            postData.put("exis",exis);
            postData.put("key",clave);
            httpost.setPostData(postData);
            httpost.execute("http://clementepruebas.000webhostapp.com/ventas/registrar_articulo.php");
        }

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
    public void onBackPressed()
    {
        alert();
    }

    public void close(View v){
        alert();
    }

    @Override
    public void processFinish(String s) {
       if(s.equals("1")){
            Toast.makeText(this, "Bien Hecho. El Articulo ha sido registrado correctamente", Toast.LENGTH_SHORT).show();
           finish();
        }else if(s.equals("2")){
            Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
        }else if(s.equals("")){
            Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
        }

    }
}
