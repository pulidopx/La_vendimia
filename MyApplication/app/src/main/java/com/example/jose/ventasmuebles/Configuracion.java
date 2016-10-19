package com.example.jose.ventasmuebles;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class Configuracion extends Fragment {
Globally g = Globally.getInstance();
    EditText taza,eng;
    TextView plazo;
    View rootView;
    Button save;
    String pMa;
    int tag = 0;

    public static Configuracion newInstance(){
        Configuracion fragment = new Configuracion();
        return fragment;
    }

    public Configuracion(){}

    public void Conf(){
        String tasa = taza.getText().toString();
        String enga = eng.getText().toString();
        String plazom = pMa.toString();

        if(tasa.equals("") || enga.equals("") || plazom.equals("")){
            Toast.makeText(getActivity(), "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(tasa.equals("")){taza.setError("Campo obligatorio!");}
            if(enga.equals("")){eng.setError("Campo obligatorio!");}
        }else{
            try {
                JSONObject obj = new JSONObject();
                obj.put("tasa", tasa);
                obj.put("enganche", enga);
                obj.put("plazoM", plazom);
                g.setTasaF(tasa);
                g.setPlazoMaximo(plazom);
                g.setEnganche(enga);
                    plazo.setText("Plazo Maximo a " + plazom + ":");
                Write(obj.toString());
                SaveSharedPreference.setUserName(getActivity(), "ventas");
                Toast.makeText(getActivity(), "Bien Hecho, La configuración ha sido registrada", Toast.LENGTH_SHORT).show();
            }catch (JSONException e ){e.getMessage();}
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_configuracion, container, false);
        //bt = (ImageView) rootView.findViewById(R.id.imageView4);

        taza = (EditText) rootView.findViewById(R.id.tasa);
        save = (Button) rootView.findViewById(R.id.button);
        eng = (EditText) rootView.findViewById(R.id.enganche);
        plazo = (TextView) rootView.findViewById(R.id.pla);
        taza.setText(g.getTasaF());
        eng.setText(g.getEnganche());
        plazo.setText("Plazo Maximo a "+g.getPlazoMaximo()+":");

        Spinner pm = (Spinner) rootView.findViewById(R.id.plazo);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.plazos, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pm.setAdapter(adapter);

        pm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                pMa = selectedItemText+"";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conf();
            }
        });
        return rootView;
    }

    public void Write(String data){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState())) {

            try {

                File txtfile = new File(getActivity().getExternalFilesDir(null), "data.venta");
                FileWriter writer = new FileWriter(txtfile);
                BufferedWriter out = new BufferedWriter(writer);
                out.write(data);
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
