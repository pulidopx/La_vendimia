package com.example.jose.ventasmuebles;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse {

//aqui utilizo la transacción de los fragments
    private Fragment ventas,clientes,articulos,conf;
    public FragmentTransaction transaction;
    Globally g = Globally.getInstance();
    TextView fech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

  if(getIntent().getStringExtra("data") != null) {
    String data = getIntent().getStringExtra("data");
        try {
            JSONObject date = new JSONObject(data);
            g.setTasaF(date.getString("tasa"));
            g.setEnganche(date.getString("enganche"));
            g.setPlazoMaximo(date.getString("plazoM"));
            Log.d("Confi", date.toString());


        } catch (JSONException e) {
            e.getMessage();
        }
    }

    }

    public void response(){
        HashMap postData = new HashMap();
        PostResponseAsyncTask httpost = new PostResponseAsyncTask(this, this);
        httpost.setPostData(postData);
        httpost.execute("http://single-lemonjuice.netau.net/ventas/clientes.php");

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                response();
            }
        },3000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            ventas = new ventas();
            // Handle the camera action
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragments_childs, ventas);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_conf) {
            conf = new Configuracion();
            // Handle the camera action
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragments_childs, conf);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_share) {
            clientes = new Clientes();
            // Handle the camera action
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragments_childs, clientes);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_send) {
            articulos = new Articulo();
            // Handle the camera action
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragments_childs, articulos);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.salir) {
         System.exit(0);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void processFinish(String s) {
        Log.d("mod",s);
        try{
            JSONObject clar= new JSONObject(s);
            String cl = clar.getString("cliente");
            String ar = clar.getString("articulo");
            String fh = clar.getString("fecha");
            g.setClientes(cl);
            g.setArticulos(ar);
            g.setFecha(fh);
            g.setTagC(0);
            g.setTag2(0);
            Log.d("clar",cl+" : "+ar);
            String[] fecha =g.getFecha().split("-");
            fech = (TextView) findViewById(R.id.fecha);
            fech.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
        }catch(JSONException e){Log.d("json",e.getMessage());}
    }

    }










