package Mujer.Modelos;

public class Usuario {

    private String Uid,Nombre, Apellidos, Correo, Contraseña;

    public Usuario() {
    }
    public Usuario(String Uid, String Nombre, String Apellidos, String Correo) {
        this.Uid = Uid;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Correo = Correo;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }
    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }
    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return Contraseña;
    }
    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
