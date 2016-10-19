package com.example.jose.ventasmuebles;
import android.annotation.SuppressLint;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
public class cardViewAlertasAdapter4 extends RecyclerView.Adapter <cardViewAlertasAdapter4.cardViewAlertasHolder>{
    Globally g = Globally.getInstance();
    private List<alertaInfo> listaAlertas;
    public cardViewAlertasAdapter4(List<alertaInfo> alertaInfo) {
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
        holder.descripcion.setText(ci.descripcionArt);
        holder.clave.setText(ci.clave_articulo);


        final int index=i;
/*
        holder.boton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
*/



    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_articulo, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView clave,descripcion;

        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            clave =  (TextView)  v.findViewById(R.id.claveArt);
            descripcion =  (TextView)  v.findViewById(R.id.desc);







        }
    }



}
