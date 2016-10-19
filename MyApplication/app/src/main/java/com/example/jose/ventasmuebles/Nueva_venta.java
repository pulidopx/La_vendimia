package com.example.jose.ventasmuebles;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.view.menu.CascadingMenuPopup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Nueva_venta extends AppCompatActivity implements AsyncResponse {

    AutoCompleteTextView cliente,articulo;
    Globally g = Globally.getInstance();
    List<alertaInfo> result;
    RecyclerView recList;
    TextView rfc,enganche,bonificacion,total,fol,fech;
    TextView a3,b3,c3,d3,a6,b6,c6,d6,a9,b9,c9,d9,a12,b12,c12,d12;
    RadioButton e3,e6,e9,e12;
    LinearLayout eng,abono,end,next,l3,l6,l9,l12;
    Double importes,plazosMeses,meses3,meses6,meses9,meses12;
    DecimalFormat decimal;
    Random generator;
    //generar folios
    String folio,nombre;
    Button save;

    public void txt(){
        a3 = (TextView) findViewById(R.id.a3);
        b3 = (TextView) findViewById(R.id.b3);
        c3 = (TextView) findViewById(R.id.c3);
        d3 = (TextView) findViewById(R.id.d3);
        e3 = (RadioButton) findViewById(R.id.e3);
        //
        a6 = (TextView) findViewById(R.id.a6);
        b6 = (TextView) findViewById(R.id.b6);
        c6 = (TextView) findViewById(R.id.c6);
        d6 = (TextView) findViewById(R.id.d6);
        e6 = (RadioButton) findViewById(R.id.e6);
        //
        a9 = (TextView) findViewById(R.id.a9);
        b9 = (TextView) findViewById(R.id.b9);
        c9 = (TextView) findViewById(R.id.c9);
        d9 = (TextView) findViewById(R.id.d9);
        e9 = (RadioButton) findViewById(R.id.e9);
        //
        a12 = (TextView) findViewById(R.id.a12);
        b12 = (TextView) findViewById(R.id.b12);
        c12 = (TextView) findViewById(R.id.c12);
        d12 = (TextView) findViewById(R.id.d12);
        e12 = (RadioButton) findViewById(R.id.e12);
        //
        l3 = (LinearLayout) findViewById(R.id.l3);
        l6 = (LinearLayout) findViewById(R.id.l6);
        l9 = (LinearLayout) findViewById(R.id.l9);
        l12 = (LinearLayout) findViewById(R.id.l12);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_venta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cliente = (AutoCompleteTextView) findViewById(R.id.cliente);
        articulo = (AutoCompleteTextView) findViewById(R.id.articulo);
        eng = (LinearLayout) findViewById(R.id.enganche);
        abono = (LinearLayout) findViewById(R.id.abono);
        rfc = (TextView) findViewById(R.id.rfc);
        fol = (TextView) findViewById(R.id.fol);
        enganche = (TextView) findViewById(R.id.enganches);
        end = (LinearLayout) findViewById(R.id.end);
        next = (LinearLayout) findViewById(R.id.next);
        bonificacion = (TextView) findViewById(R.id.bonificacion);
        total = (TextView) findViewById(R.id.total);
        save = (Button) findViewById(R.id.save);
        save.setEnabled(false);
        String[] fecha =g.getFecha().split("-");
        fech = (TextView) findViewById(R.id.fecha);
        fech.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
        txt();
       String clientes = g.getClientes();
        String articulos = g.getArticulos();
        final List<String> arrays = new ArrayList<String>();
        final List<String> arrays2 = new ArrayList<String>();
     try {
         JSONArray arr = new JSONArray(clientes.toString());
         for(int i=0;i<=arr.length();i++){
             JSONObject ob = arr.getJSONObject(i);
           arrays.add(ob.getString("nombre")+"-"+ob.getString("rfc"));

             //final String rfcs = ob.getString("rfc");
             final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrays);
             cliente.setAdapter(adapter);

           cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   String str[] = ((TextView)view).getText().toString().split("-");
                   rfc.setText(str[1]);
                   cliente.setText(str[0]);
                   nombre = str[0];
               }
           });
         }

     }catch(JSONException e){e.getMessage();}

        try {
            JSONArray arr2 = new JSONArray(articulos.toString());
            for (int l = 0; l <= arr2.length(); l++) {
                JSONObject ob2 = arr2.getJSONObject(l);
                arrays2.add(ob2.getString("descripcion"));
                //final String rfcs = ob.getString("rfc");
                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrays2);
                articulo.setAdapter(adapter2);

                articulo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

        }catch(JSONException w){Log.d("json",w.getMessage());}
        rand();
    }


    private List<alertaInfo> createList(int size, String json) {

        result = new ArrayList<>();

        size=5;
        try{

                decimal = new DecimalFormat("0.00");
                JSONObject jsonobject = new JSONObject(json);
                alertaInfo ci = new alertaInfo();
                ci.descripcion = jsonobject.getString("descripcion");
                ci.modelo = jsonobject.getString("modelo");
                ci.cantidad = 1;
                ci.precio = jsonobject.getString("precio");
                double pre = Double.parseDouble(jsonobject.getString("precio"));
                double importe = pre * ci.cantidad;
                ci.importe = decimal.format(importe) + "";
                importes = importe;
                g.setCantidad(importes);
            result.add(ci);

        }catch(JSONException e){Log.d("json",e.getMessage());}


        return result;
    }



public void add(View v){
 String cl = cliente.getText().toString();
    String ar = articulo.getText().toString();
    if(cl.equals("") || ar.equals("")) {
        if(ar.equals("")){
            articulo.setError("Campos vacios!");
        }
        if(cl.equals("")){
            cliente.setError("Campos vacios!");
        }

    }else{
        HashMap postData = new HashMap();
        postData.put("descripcion", ar);
        PostResponseAsyncTask httpost = new PostResponseAsyncTask(this, this);
        httpost.setPostData(postData);
        httpost.execute("http://clementepruebas.000webhostapp.com/ventas/venta_articulo.php");
    }

}



        public void rand(){
        generator = new Random();
        int n1 = Math.abs(generator.nextInt() % 6);
            int n2 = Math.abs(generator.nextInt() % 6);
            int n3 = Math.abs(generator.nextInt() % 6);
            folio = ""+n1+n2+n3;
            fol.setText("folio:"+n1+n2+n3);
        }



    public void Success(View v){
        String fl = folio.toString();
        String nm = nombre.toString();
        String pm = plazosMeses.toString();
        String ar = articulo.getText().toString();
        if(fl.equals("") || nm.equals("") || pm.equals("") || ar.equals("")) {

        }else{
            HashMap postData = new HashMap();
            postData.put("folio", fl);
            postData.put("nombre", nm);
            postData.put("total", pm);
            postData.put("descripcion", ar);
            PostResponseAsyncTask httpost = new PostResponseAsyncTask(this, this);
            httpost.setPostData(postData);
            httpost.execute("http://clementepruebas.000webhostapp.com/ventas/venta_registrada.php");
        }
    }

    public void formulas(){

       decimal = new DecimalFormat("0.00");

        //Formula Enganche
        int PE = Integer.parseInt(g.getEnganche());
        double pas1 = (double) PE/100;
        double pas2 = pas1*g.getCantidad();
        enganche.setText("Enganche: "+decimal.format(pas2));
        //Formula Bonificación enganche
        int plazo = Integer.parseInt(g.getPlazoMaximo());
        double tasa = Double.parseDouble(g.getTasaF());
        double TXP = (double)tasa * plazo;
        double Tpas2 = TXP / 100;
        double Tpas3 = pas2 *Tpas2;
        //Total
        bonificacion.setText("Bonificación Enganche: "+decimal.format(Tpas3));
        double totals = g.getCantidad() - pas2 - Tpas3;
        total.setText("Total: "+decimal.format(totals));
        //Precio contado
        double cont1 = 1+TXP/100;
        double precioCont = totals / cont1;
        //A 3 meses
        double TXM3 = tasa * 3;
        double mes13 = 1+TXM3 / 100;
        meses3 = precioCont * mes13;
        c3.setText("TOTAL A PAGAR $"+decimal.format(meses3));

        //A 6 meses
        double TXM6 = tasa * 6;
        double mes16 = 1+TXM6 / 100;
        meses6 = precioCont * mes16;
        c6.setText("TOTAL A PAGAR $"+decimal.format(meses6));

        //A 9 meses
        double TXM9 = tasa * 9;
        double mes19 = 1+TXM9 / 100;
        meses9 = precioCont * mes19;
        c9.setText("TOTAL A PAGAR $"+decimal.format(meses9));

        //A 12 meses
        double TXM12 = tasa * 12;
        double mes112 = 1+TXM12 / 100;
        meses12 = precioCont * mes112;
        c12.setText("TOTAL A PAGAR $"+decimal.format(meses12));

        //importe Abono

        //3 meses
        double imA3 = totals / 3;
        b3.setText("$"+decimal.format(imA3));
        //6 meses
        double imA6 = totals / 6;
        b6.setText("$"+decimal.format(imA6));
        //9 meses
        double imA9= totals / 9;
        b9.setText("$"+decimal.format(imA9));
        //12 meses
        double imA12 = totals / 12;
        b12.setText("$"+decimal.format(imA12));

        //importe Ahorro de la columna - SE AHORRA -
        //a 3 meses
        double imAh3 = totals - meses3;
        d3.setText("SE AHORRA $"+decimal.format(imAh3));
        //a 6 meses
        double imAh6 = totals - meses6;
        d6.setText("SE AHORRA $"+decimal.format(imAh6));
        //a 9 meses
        double imAh9 = totals - meses9;
        d9.setText("SE AHORRA $"+decimal.format(imAh9));
        //a 12 meses
        double imAh12 = totals - meses12;
        d12.setText("SE AHORRA $"+decimal.format(imAh12));

    }


    public void Reload(View v){
        formulas();
    }

  public void radioClick(View v){
      // Is the button now checked?
      boolean checked = ((RadioButton) v).isChecked();
      save.setEnabled(true);
      // Check which radio button was clicked
      switch(v.getId()) {
          case R.id.e3:
              if (checked)
                  e6.setChecked(false);
                  e9.setChecked(false);
                  e12.setChecked(false);
                  plazosMeses = meses3;
                  break;
          case R.id.e6:
              if (checked)
                  e3.setChecked(false);
                  e9.setChecked(false);
                  e12.setChecked(false);
                   plazosMeses = meses6;
                  break;
          case R.id.e9:
              if (checked)
                  e6.setChecked(false);
                 e3.setChecked(false);
                 e12.setChecked(false);
              plazosMeses = meses9;
                  break;
          case R.id.e12:
              if (checked)
                  e6.setChecked(false);
                  e9.setChecked(false);
                  e3.setChecked(false);
              plazosMeses = meses12;
                  break;
      }

  }


    public void next(View v){
        abono.setVisibility(View.VISIBLE);
        next.setVisibility(View.INVISIBLE);
        end.setVisibility(View.VISIBLE);
    }

    public void Cancelar(View v){
        abono.setVisibility(View.INVISIBLE);
        eng.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onBackPressed()
    {
       alert();
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


    @Override
    public void processFinish(String s) {
        if(s.equals("0")){
            Toast.makeText(this, "Articulo sin existencia", Toast.LENGTH_SHORT).show();
        }else if(s.equals("")){
            Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
        }else if(s.equals("1")){
            Toast.makeText(this, "Bien Hecho, Tu venta ha sido registrada correctamente", Toast.LENGTH_LONG).show();
            finish();
        }else if(s.equals("2")){
            Toast.makeText(this, "Hubo un error y no se inserto correctamente", Toast.LENGTH_LONG).show();
        }else if(s.equals("3")){
            Toast.makeText(this, "error inesperado", Toast.LENGTH_LONG).show();
        }else{
            recList = (RecyclerView) findViewById(R.id.rvAlertas2);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this.getApplication());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            cardViewAlertasAdapter2 ca = new cardViewAlertasAdapter2(createList(100,s));
            recList.setAdapter(ca);
            RecyclerView.ItemAnimator animator = recList.getItemAnimator();
            animator.setAddDuration(1000);
            animator.setRemoveDuration(500);
            eng.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);

                switch(g.getPlazoMaximo()){

                    case "3":
                        l6.setVisibility(View.INVISIBLE);
                        l9.setVisibility(View.INVISIBLE);
                        l12.setVisibility(View.INVISIBLE);
                        break;
                    case "6":
                        l9.setVisibility(View.INVISIBLE);
                        l12.setVisibility(View.INVISIBLE);
                        break;
                    case "9":
                        l12.setVisibility(View.INVISIBLE);
                        break;
                    case "12":

                        break;

                }

            formulas();

        }

    }
}
