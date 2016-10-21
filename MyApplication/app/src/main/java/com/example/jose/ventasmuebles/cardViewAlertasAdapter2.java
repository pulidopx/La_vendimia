package com.example.jose.ventasmuebles;
/**
 * Created by User-Z on 16/10/2016.
 */
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
public class cardViewAlertasAdapter2 extends RecyclerView.Adapter <cardViewAlertasAdapter2.cardViewAlertasHolder>{
    Globally g = Globally.getInstance();
    private List<alertaInfo> listaAlertas;
    public cardViewAlertasAdapter2(List<alertaInfo> alertaInfo) {
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
        holder.descripcion.setText(ci.descripcion);
        holder.Modelo.setText(ci.modelo);
        holder.precio.setText(ci.precio);
        holder.importe.setText(ci.importe);
        holder.cantidad.setText(ci.cantidad+"");

        final int index=i;

        holder.boton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaAlertas.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, listaAlertas.size());

            }
        });

      holder.cantidad.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {


          }

          @Override
          public void afterTextChanged(Editable s) {

              try {
                  int cn = Integer.parseInt(s.toString());
                  // int imp = Integer.parseInt(holder.importe.getText().toString());
                  //  imp = imp * cn;
                  //holder.cantidad.setText(imp);
                  Log.d("msg", cn + "");
                  double imp = Double.parseDouble(holder.precio.getText().toString());
                  double result = imp * cn;
                  holder.importe.setText(""+result);
                  g.setCantidad(result);
                  g.setExistencia(cn+"");
              }catch(Exception e){
                  Log.d("msg",e.getMessage());
               holder.importe.setText("");
                  g.setCantidad(0);
              }

          }
      });


    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_venta, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView descripcion,Modelo,precio,importe,boton;
        protected EditText cantidad;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            descripcion =  (TextView)  v.findViewById(R.id.descripcion);
            Modelo =  (TextView)  v.findViewById(R.id.Modelo);
            precio =  (TextView)  v.findViewById(R.id.Precio);
            importe =  (TextView)  v.findViewById(R.id.importe);
            boton =  (TextView)  v.findViewById(R.id.close);
            cantidad = (EditText) v.findViewById(R.id.cantidad);





        }
    }



}
