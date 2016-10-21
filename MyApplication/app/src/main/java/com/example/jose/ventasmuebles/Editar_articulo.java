package com.example.jose.ventasmuebles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class Editar_articulo extends AppCompatActivity implements AsyncResponse {

    Random generator;
    String clave;
    TextView clav,fech;
    EditText des,precio,modelo,existencia;
    Button saved;
    Globally g = Globally.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_articulo);
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

        String data = getIntent().getStringExtra("data");
        try {
            JSONObject date = new JSONObject(data);
            existencia.setText(date.getString("existencia").toString());
            des.setText(date.getString("descripcion"));
            precio.setText(date.getString("precio"));
            modelo.setText(date.getString("modelo"));
            Log.d("json", date.toString());
            clav.setText("clave:"+date.getString("clave_articulo"));
            clave = date.getString("clave_articulo");
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

    public void GuardarArticulo(View v){
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
            httpost.execute("http://single-lemonjuice.netau.net/ventas/editar_articulo.php");
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
        String st[] = s.split("-");
        Log.d("msg",st[0]);
        if(st[0].equals("")){
            Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Bien Hecho. Se han editado los datos del cliente", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
