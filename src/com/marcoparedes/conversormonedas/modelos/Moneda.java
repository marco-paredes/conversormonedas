package com.marcoparedes.conversormonedas.modelos;

import com.google.gson.annotations.SerializedName;

public class Moneda {

    private String pais;
    private String nombre;
    @SerializedName("base_code")
    public String simboloIn;
    @SerializedName("target_code")
    public String simboloOut;
    @SerializedName("conversion_rate")
    private double conversion;
    private double valorConversion;

    public Moneda(MonedaExRate miMonedaExRate) {
        this.simboloIn = miMonedaExRate.base_code();
        this.simboloOut = miMonedaExRate.target_code();
        this.conversion = miMonedaExRate.conversion_rate();
    }

    public String getPais() {
        return pais;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimboloIn() {
        return simboloIn;
    }

    public double getConversion() {
        return conversion;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSimbolo(String simbolo) {
        this.simboloIn = simboloIn;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    //el siguiente metodo solo sirve cuando la variable es de tipo private
    public double getValorConversion(){
        return valorConversion;
    }

    public void muestraTipo (){
        System.out.println("La moneda es de: " + pais);
        System.out.println("La moneda se llama: " + nombre);
        System.out.println("Un dólar equivale a: " + conversion + " " + simboloIn);
    }

    public void convierte(double valorMoneda){
        valorConversion = valorMoneda*conversion;
    }

    @Override
    public String toString() {
        return "El valor de cambio de " +
                simboloIn + " a " +
                simboloOut + " es = " +
                conversion;

    }
}
