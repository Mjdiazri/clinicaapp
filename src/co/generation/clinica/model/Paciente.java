package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

import java.util.Objects;

public class Paciente implements Registrable {
    private static final String FORMATO_CEDULA = "^[0-9]{1,10}$";
    private static final String FORMATO_NOMBRE = "^[\\p{L}]+(?:[\\s'-][\\p{L}]+)*$";
    private static final String FORMATO_TELEFONO = "^[0-9]{7,10}$";

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        setId(id);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    //Metodo que devuelve el id del paciente
    public int getId() {
        return id;
    }

    //Metodo que asigna el id del paciente
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo.");
        }
        this.id = id;
    }

    //Metodo que devuelve la cedula del paciente
    public String getCedula() {
        return cedula;
    }

    //Metodo que valida y asigna la cedula del paciente
    public void setCedula(String cedula) {
        if (cedula == null || cedula.isBlank()) {
            throw new IllegalArgumentException("La cedula es obligatoria.");
        }
        if (!cedula.matches(FORMATO_CEDULA)) {
            throw new IllegalArgumentException("La cedula debe contener solo digitos, sin espacios y tener maximo 10 caracteres.");
        }
        this.cedula = cedula;
    }

    //Metodo que devuelve el nombre del paciente
    public String getNombre() {
        return nombre;
    }

    //Metodo que valida y asigna el nombre del paciente
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre, "El nombre es obligatorio y solo puede contener letras.");
    }

    //Metodo que devuelve el apellido del paciente
    public String getApellido() {
        return apellido;
    }

    //Metodo que valida y asigna el apellido del paciente
    public void setApellido(String apellido) {
        this.apellido = validarNombre(apellido, "El apellido es obligatorio y solo puede contener letras.");
    }

    //Metodo que devuelve el telefono del paciente
    public String getTelefono() {
        return telefono;
    }

    //Metodo que valida y asigna el telefono del paciente
    public void setTelefono(String telefono) {
        String telefonoLimpio = validarTexto(telefono, "El telefono es obligatorio.");
        if (!telefonoLimpio.matches(FORMATO_TELEFONO)) {
            throw new IllegalArgumentException("El telefono debe contener entre 7 y 10 digitos.");
        }
        this.telefono = telefonoLimpio;
    }

    //Metodo que devuelve los datos de registro del paciente
    @Override
    public String getDatosRegistro() {
        return toString();
    }

    //Metodo que verifica los datos obligatorios del paciente
    @Override
    public boolean esValido() {
        return cedula != null && cedula.matches(FORMATO_CEDULA)
                && nombre != null && nombre.matches(FORMATO_NOMBRE)
                && apellido != null && apellido.matches(FORMATO_NOMBRE)
                && telefono != null && telefono.matches(FORMATO_TELEFONO);
    }

    //Metodo que compara pacientes por cedula
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Paciente)) {
            return false;
        }
        Paciente paciente = (Paciente) objeto;
        return cedula.equals(paciente.cedula);
    }

    //Metodo que genera el hash del paciente
    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    //Metodo que devuelve la informacion del paciente
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }

    //Metodo que valida un texto obligatorio
    private String validarTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    //Metodo que valida nombres y apellidos
    private String validarNombre(String valor, String mensaje) {
        String valorLimpio = validarTexto(valor, mensaje);
        if (!valorLimpio.matches(FORMATO_NOMBRE)) {
            throw new IllegalArgumentException(mensaje);
        }
        return valorLimpio;
    }
}
