
package com.digis01.FNolascoProgramacionNCapas.ML;

//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Positive;


public class Colonia {
    
//    @Pattern(regexp = "^\\d+$", message = "*Solo escribe el ID de tu colonia.")
//    @Positive(message = "El número debe ser positivo")
    private int IdColonia;
    
    private String Nombre;
    private String CodigoPostal;
    public Municipio Municipio;

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
    
    
}
