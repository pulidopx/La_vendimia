package com.example.jose.ventasmuebles;

import android.content.Intent;
import android.os.Bundle;
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
        httpost.execute("http://clementepruebas.000webhostapp.com/ventas/articulo.php");
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        response();
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
        }
    }
}
