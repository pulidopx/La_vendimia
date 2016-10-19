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
import android.widget.LinearLayout;
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

public class ventas extends Fragment implements AsyncResponse {

public String JSONdata;
    View rootView;
    List<alertaInfo> result;
    RecyclerView recList;
    Globally g = Globally.getInstance();
    public static ventas newInstance(){
        ventas fragment = new ventas();
        return fragment;
    }

    public ventas(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.activity_ventas, container, false);
        //bt = (ImageView) rootView.findViewById(R.id.imageView4);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabventas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nuevaVenta = new Intent(getActivity(),Nueva_venta.class);
                startActivity(nuevaVenta);
            }
        });
       // if(JSONdata != null) {
         //   initializeRecyclerView(json);
      //  }
            return rootView;
    }

      public void initializeRecyclerView(String s) {
        recList = (RecyclerView) rootView.findViewById(R.id.rvAlertas);
        recList.setHasFixedSize(true);
          result = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        cardViewAlertasAdapter ca = new cardViewAlertasAdapter(createList(100,s));
        recList.setAdapter(ca);
        RecyclerView.ItemAnimator animator = recList.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
    }

    public void response(){
        HashMap postData = new HashMap();
        PostResponseAsyncTask httpost = new PostResponseAsyncTask(getActivity(), this);
        httpost.setPostData(postData);
        httpost.execute("http://clementepruebas.000webhostapp.com/ventas/ventas_activas.php");
    }


    private List<alertaInfo> createList(int size, String json) {


        //result = new ArrayList<>();

        size=5;
        try{
            JSONArray jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                alertaInfo ci = new alertaInfo();
                String[] fecha = jsonobject.getString("fecha").split(" ");
                ci.folio = jsonobject.getString("folio_ventas");
                ci.clave = jsonobject.getString("clave_cliente");
                ci.total = jsonobject.getString("total");
                ci.nombre = jsonobject.getString("nombre");
                ci.fecha = fecha[0];
                result.add(ci);
            }
        }catch(JSONException e){Log.d("json",e.getMessage());}


        return result;
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        response();
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
