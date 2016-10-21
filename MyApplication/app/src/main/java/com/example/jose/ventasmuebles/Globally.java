package com.example.jose.ventasmuebles;


/**
 * Created by Jose on 06/07/16.
 */
public class Globally {

    private static Globally instance;
    private static String clientes,arts,articulos,exis,url,tasaF,enganche,plazoMaximo,jsoncl,jsonar,fecha;
    private static double cantidad;
    private static int tc,tc2;


    private Globally(){}

    public void setClientes(String id){
        Globally.clientes = id;
    }

    public String getClientes(){

        return Globally.clientes;

    }
    //---------------------------------------

    public void setArticulos(String id){
        Globally.articulos = id;
    }

    public String getArticulos(){

        return Globally.articulos;

    }

    //---------------------------------------

    public void setURL(String id){
        Globally.url = id;
    }

    public String getURL(){

        return Globally.url;

    }

    //---------------------------------------

    public void setTasaF(String id){
        Globally.tasaF = id;
    }

    public String getTasaF(){

        return Globally.tasaF;

    }

    //---------------------------------------

    public void setEnganche(String id){
        Globally.enganche = id;
    }

    public String getEnganche(){

        return Globally.enganche;

    }


    //---------------------------------------

    public void setPlazoMaximo(String id){
        Globally.plazoMaximo = id;
    }

    public String getPlazoMaximo(){

        return Globally.plazoMaximo;

    }
    //---------------------------------------

    public void setCantidad(double id){
        Globally.cantidad = id;
    }

    public double getCantidad(){

        return Globally.cantidad;

    }
    //---------------------------------------
    public void setTagC(int id){
        Globally.tc = id;
    }

    public int getTagC(){

        return Globally.tc;

    }
    //----------------------

    public void setTag2(int id){
        Globally.tc2 = id;
    }

    public int getTag2(){

        return Globally.tc2;

    }
    //----------------------

    public void setJsonCliente(String id){
        Globally.jsoncl = id;
    }

    public String getJsonCliente(){

        return Globally.jsoncl;

    }
    //----------------------

    public void setJsonArticulo(String id){
        Globally.jsonar = id;
    }

    public String getJsonArticulo(){

        return Globally.jsonar;

    }
    //----------------------

    public void setFecha(String id){
        Globally.fecha = id;
    }

    public String getFecha(){

        return Globally.fecha;

    }

    //----------------------

    public void setExistencia(String id){
        Globally.exis = id;
    }

    public String getExistencia(){

        return Globally.exis;

    }

    //----------------------

    public void setArticulo(String id){
        Globally.arts = id;
    }

    public String getArticulo(){

        return Globally.arts;

    }



    public static synchronized Globally getInstance() {

        if (instance == null) {

            instance = new Globally();

        }

        return instance;

    }
}
