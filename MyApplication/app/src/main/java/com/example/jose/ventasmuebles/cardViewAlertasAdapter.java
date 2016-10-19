package com.example.jose.ventasmuebles;

/**
 * Created by User-Z on 16/10/2016.
 */
import android.annotation.SuppressLint;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import java.util.List;

@SuppressLint("NewApi")
public class cardViewAlertasAdapter extends RecyclerView.Adapter <cardViewAlertasAdapter.cardViewAlertasHolder>{

    private List<alertaInfo> listaAlertas;
    public cardViewAlertasAdapter(List<alertaInfo> alertaInfo) {
        this.listaAlertas = alertaInfo;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }
    @Override
    public void onBindViewHolder(cardViewAlertasHolder holder, int i) {
        alertaInfo ci = listaAlertas.get(i);
        holder.folio.setText(ci.folio);
        holder.clave.setText(ci.clave);
        holder.nombre.setText(ci.nombre);
        holder.total.setText(ci.total);
        holder.fecha.setText(ci.fecha);


        final int index=i;
/*
        holder.boton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaAlertas.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, listaAlertas.size());

                Toast.makeText(v.getContext(), "HOLA: "+index, Toast.LENGTH_SHORT).show();

            }
        });
 */

    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_alertas, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView folio,clave,nombre,total,fecha;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            folio =  (TextView)  v.findViewById(R.id.FolioVentas);
            clave =  (TextView)  v.findViewById(R.id.ClaveCliente);
            nombre =  (TextView)  v.findViewById(R.id.Nombre);
            total =  (TextView)  v.findViewById(R.id.Total);
            fecha =  (TextView)  v.findViewById(R.id.Fecha);
            // boton= (ImageView) v.findViewById(R.id.imageView7);



        }
    }



}
