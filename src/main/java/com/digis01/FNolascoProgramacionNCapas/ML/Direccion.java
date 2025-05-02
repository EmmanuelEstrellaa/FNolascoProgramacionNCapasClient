
package com.digis01.FNolascoProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class Direccion {
    private int IdDireccion;
    
   
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "*No puedes escribir caracteres especiales.")
    private String Calle;
    
    @Pattern(regexp = "^[a-zA-Z0-9\\s#]+$", message = "*No puedes escribir caracteres especiales.")
    private String NumeroInterior;
    
    
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "*No puedes escribir caracteres especiales.")
    private String NumeroExterior;
    
    @Valid
    public Colonia Colonia; 

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
    
    
}
