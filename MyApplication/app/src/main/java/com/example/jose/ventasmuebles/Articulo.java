package com.example.jose.ventasmuebles;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Articulo extends Fragment implements AsyncResponse{

Globally g = Globally.getInstance();
    List<alertaInfo> result;
    View rootView;
    RecyclerView recList;
    AsyncTaskRunner myTask;

    public static Articulo newInstance(){
        Articulo fragment = new Articulo();
        return fragment;
    }

    public Articulo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_articulo, container, false);
        //bt = (ImageView) rootView.findViewById(R.id.imageView4);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nuevaVenta = new Intent(getActivity(),Registro_articulo.class);
                startActivity(nuevaVenta);
            }
        });
        return rootView;
    }

    public void response(){
        HashMap postData = new HashMap();
        PostResponseAsyncTask httpost = new PostResponseAsyncTask(getActivity(), this);
        httpost.setPostData(postData);
        httpost.execute(g.getURL()+"/ventas/articulo.php");
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        response();
    }

    @Override
    public void onStop() {
        super.onStop();
        myTask.cancel(true);
    }

    private List<alertaInfo> createList(int size, String json) {
        //result = new ArrayList<>();

        size=5;
        try{
            JSONArray jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                alertaInfo ci = new alertaInfo();
                ci.clave_articulo = jsonobject.getString("clave_articulo");
                ci.descripcionArt = jsonobject.getString("descripcion");
                ci.JsonAr = jsonobject.toString();
                result.add(ci);
            }
        }catch(JSONException e){
            Log.d("json",e.getMessage());}


        return result;
    }


    public void initializeRecyclerView(String s) {
        recList = (RecyclerView) rootView.findViewById(R.id.rvAlertas);
        recList.setHasFixedSize(true);
        result = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        cardViewAlertasAdapter4 ca = new cardViewAlertasAdapter4(createList(100,s));
        recList.setAdapter(ca);
        RecyclerView.ItemAnimator animator = recList.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
    }


    @Override
    public void processFinish(String s) {
        Log.d("asycTask",s);
        if(s.equals("")){
            Toast.makeText(getActivity(), "No hay respuesta del servidor", Toast.LENGTH_LONG).show();
        }else{
            //JSONdata = s;
            initializeRecyclerView(s);

            myTask = new AsyncTaskRunner();
            myTask.execute("0");
        }
    }

    //Aqui inicia la tarea Asyncrona para extraer la dirección
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        Handler handler = new Handler();

        @Override
        protected String doInBackground(String... params) {
            // publishProgress("Sleeping..."); // Calls onProgressUpdate()
            Log.d("task", "execute in back ground");
            try {
                // Do your long operations here and return the result
                int time = Integer.parseInt(params[0]);
                // Sleeping for given time period
                Thread.sleep(0);
                resp = "Slept for " + time + " milliseconds";
                handler.postDelayed(r, 300);

            } catch (InterruptedException e) {
                Log.d("task", "error:" + e.getMessage());
                resp = e.getMessage();
            } catch (Exception e) {
                Log.d("task", "errorE:" + e.getMessage());
                resp = e.getMessage();
            }
            return resp;
        }

        final Runnable r = new Runnable() {
            public void run() {
                if(g.getTag2() == 1) {
                    Intent edit = new Intent(getActivity(),Editar_articulo.class);
                    edit.putExtra("data", g.getJsonArticulo());
                    startActivity(edit);
                    g.setTag2(0);
                }
                Log.d("task", "Task is running");
                handler.postDelayed(this, 300);

            }
        };
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            Log.d("task", "execute on Post");

        }
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            Log.d("task", "execute on pre");
        }
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            Log.d("task", "execute on Update");
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
            // coor(g.getLat(),g.getLong());
        }


    }

}
