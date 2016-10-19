package com.example.jose.ventasmuebles;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.os.Handler;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import java.util.List;

@SuppressLint("NewApi")
public class cardViewAlertasAdapter3 extends RecyclerView.Adapter <cardViewAlertasAdapter3.cardViewAlertasHolder>{
    Globally g = Globally.getInstance();
    private List<alertaInfo> listaAlertas;
    public cardViewAlertasAdapter3(List<alertaInfo> alertaInfo) {

        this.listaAlertas = alertaInfo;



    }



    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }


    @Override
    public void onBindViewHolder(final cardViewAlertasHolder holder, int i) {
        alertaInfo ci = listaAlertas.get(i);
        holder.nombre.setText(ci.nombre_cliente);
        holder.clave.setText(ci.clave_cliente);
        holder.hiden.setText(ci.JsonCl);


        final int index=i;

        holder.boton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                holder.boton.setBackgroundResource(android.R.color.darker_gray);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.boton.setBackgroundResource(android.R.drawable.ic_menu_edit);
                    }
                },300);

                g.setJsonCliente(holder.hiden.getText().toString());
                Log.d("json",g.getJsonCliente());
                g.setTagC(1);
            }
        });

    }



    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_clientes, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView clave,nombre,boton,hiden;
        protected Button cantidad;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            nombre =  (TextView)  v.findViewById(R.id.name);
            clave =  (TextView)  v.findViewById(R.id.clave);
            boton =  (TextView)  v.findViewById(R.id.close);
            hiden =  (TextView)  v.findViewById(R.id.hiden);






        }
    }





}
