package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

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

        // Procedo a guardar el identificador recibido
        this.id = id;

        // Aqui se guardara el nombre recibido
        this.nombre = nombre;

        // Aqui se guardara el apellido recibido
        this.apellido = apellido;

        // Aqui se guardara la especialidad recibida
        this.especialidad = especialidad;


        }

    // Aqui se crea el segundo constructor para registrar un medico nuevo
    public Medico(String nombre, String apellido, Especialidad especialidad) {

        //Aqui se guardara el nombre recibido nuevo
        this.nombre = nombre;

        // Aqui se guardara el apellido recibido nuevo
        this.apellido = apellido;

        // Aqui se guardara la especialidad recibida nueva
        this.especialidad = especialidad;

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

}
