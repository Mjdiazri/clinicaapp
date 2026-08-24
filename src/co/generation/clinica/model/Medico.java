package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

import java.util.Locale;
import java.util.Objects;

public class Medico implements Registrable {

    // Aqui se guarda el identificador del medico
    private int id;

    // Aqui se guardara el nombre del medico
    private String nombre;

    // Aqui se guardara el apellido del medico
    private String apellido;

    // Aqui se guardara la especialidad del medico
    private Especialidad especialidad;

    // Aqui se crea el constructor donde medico recibe todos sus datos
    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    // Aqui se crea el segundo constructor para registrar un medico nuevo
    public Medico(String nombre, String apellido, Especialidad especialidad) {
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    // Aqui se validara que el nombre del medico no llegue vacio
    public boolean validarNombre() {

        //Aqui se revisa que el nombre no sea null y que tampoco este vacio
        if (nombre == null || nombre.trim().isEmpty()) {

            // Aqui se retorna false porque el nombre no es valido
            return false;

        }

        // Aqui se retornara true porque el nombre si contendria informacion
        return true;

    }

    // Aqui se validara que el Apellido del medico no llegue vacio
    public boolean validarApellido() {

        //Aqui se revisa que el Apellido no sea null y que tampoco este vacio
        if (apellido == null || apellido.trim().isEmpty()) {

            // Aqui se retorna false porque el apellido no es valido
            return false;

        }

        // Aqui se retornara true porque el apellido si contendria informacion
        return true;

    }

    // Aqui se validara que la especialidad del medico tenga informacion
    public boolean validarEspecialidad() {

        // Aqui se revisara que la especialidad no sea null
        if (especialidad == null) {

            // Aqui retoranara un false porque la especialidad no es valida si es null
            return false;

        }

        // Aqui retornara true si la especialidad tiene informacion ingresada
        return true;

    }

    // Aqui se validara que todos los datos obligatorios de medico sean validos
    @Override
    public boolean esValido() {

        // Aqui se revisara que el nombre, el apellido y la especialidad sean validas
        if (validarNombre() && validarApellido() && validarEspecialidad()) {

            // Aqui retornara true si todos los datos del medico son validos
            return true;

        }

        // Aqui retorara false si alguno de los datos del medico no son validos
        return false;

    }

    // Aqui se obtendran los datos del medico para su registro
    @Override
    public String getDatosRegistro() {

        // Aqui retornara los datos del medico separados por comas
        return id + "," + nombre + "," + apellido + "," + especialidad;

    }

    // Aqui se obtendra el identificador del medico
    public int getId() {

        // Aqui retornara el identificador del medico
        return id;

    }

    // Aqui se actualizara el identificador del medico
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo.");
        }
        this.id = id;
    }

    // Aqui se obtendra el nombre del medico
    public String getNombre() {

        // Aqui retornara el nombre del medico
        return nombre;

    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre del medico es obligatorio.");
    }

    // Aqui se obtendra el apellido del medico
    public String getApellido() {

        // Aqui retornara el apellido del medico
        return apellido;

    }

    public void setApellido(String apellido) {
        this.apellido = validarTexto(apellido, "El apellido del medico es obligatorio.");
    }

    //Obtener especialidad


    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad es obligatoria.");
        }
        this.especialidad = especialidad;
    }

    // Aqui se indicara como se mostraran los datos del medico en la consola
    @Override
    public String toString() {

        // Aqui se retornara el nombre, apellido y especialidad del medico
        return  "Dr. " + nombre + " " + apellido + " - " + especialidad;

    }

    // Aqui se comparara si dos medicos tienen el mismo nombre y apellido
    @Override
    public boolean equals(Object object) {

        // Aqui se revisara si se esta comparando exactamente el mismo objeto
        if (this == object) {

            return true;

        }

        // Aqui se revisara que el objeto que se recibio sea un medico
        if (!(object instanceof Medico)) {

            return false;

        }

        // Aqui se convierte el objeto recibido en un medico para poder comparar sus datos
        Medico otroMedico = (Medico) object;

        // Aqui se comparara el nombre y el apellido sin importar mayuscula o minusculas
        return nombre != null && apellido != null
                && nombre.equalsIgnoreCase(otroMedico.nombre)
                && apellido.equalsIgnoreCase(otroMedico.apellido);

    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(Locale.ROOT), apellido.toLowerCase(Locale.ROOT));
    }

    private String validarTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

}
