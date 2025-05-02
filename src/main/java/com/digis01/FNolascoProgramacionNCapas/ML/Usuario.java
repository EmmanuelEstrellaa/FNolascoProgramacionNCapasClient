
package com.digis01.FNolascoProgramacionNCapas.ML;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;



public class Usuario {

    private int IdUsuario;
    
    @NotNull
    @NotEmpty(message ="*El nombre de usuario es obligatorio.")
    private String UserName;
    
    @NotNull
    @NotEmpty(message = "*¿Cual es tu nombre?.")
    @Pattern(regexp = "^[a-zA-Z\s]+$", message = "*No puedes escribir numeros aqui.")
    private String Nombre;
    
    @NotNull
    @NotEmpty(message = "*¿Cual es tu apellido?.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "*No puedes escribir numeros aqui.")
    private String ApellidoPaterno;
    
    @Email(message = "*El correo no es valido.", regexp = "^[a-zA-Z0-9_.-]+@+[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "*¿Tu correo?")
    private String Email;
    
    @NotNull
    @Pattern(regexp = "^[H+M]$", message = "*Solo escribe H o M.")
    private String Sexo;
    
    @NotNull
    @NotEmpty(message = "*¿Tu telefono?")
    @Pattern(regexp = "^[0-9]+$", message = "*El numero no es valido.")
    private String Telefono;
    
    private String Celular;
    
    @NotNull
    @NotEmpty(message = "*¿Cual es tu CURP?.")
    @Size(min = 3, max = 50, message= "*CURP invalido.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "*No puedes escribir espacios aqui.")
    private String Curp;
    
    @NotNull
    @NotEmpty(message = "*¿Cual es tu apellido?.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "*No puedes escribir numeros aqui.")
    private String ApellidoMaterno;
    
    @NotNull
    @NotEmpty(message = "*¿Tu contraseña?")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$.!%*?&])[A-Za-z\\d@$!.%*?&]{8,}$", message = "*Tu contraseña debe tener minimo 8 caracteres, al menos una mayuscula, un numero y un caracter especial.")
    private String Password;
    
    public Roll Roll;
    
    @NotNull
    @Past(message = "*Ingresa una fecha valida.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    private String Imagen;

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    

    public Usuario(){       
    }

    public int getIdUsuario(){
        return IdUsuario;
    }

    public String getUserName(){
        return UserName;
    }
        
    public String getNombre(){
        return Nombre;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public String getCurp(){
        return Curp;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public String getPassword(){
        return Password;
    }
    

    
    public Roll getRoll(){
        return Roll;
    }
  
   

    
    
    public void setRoll(Roll Roll){
        this.Roll = Roll;
    }
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public void setCurp(String Curp){
        this.Curp = Curp;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
    

    

    

    
}
