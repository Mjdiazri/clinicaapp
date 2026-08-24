package co.generation.clinica.service;

import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Turno;

// PARTE LIZETH LONDOÑO BLOQUE MEDICO
import co.generation.clinica.model.Medico;
// ---------------------------------
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Turno> turnos = new ArrayList<>();

    public List<Turno> getTurnos() {
        return turnos;
    }

    // Metodo que registra un paciente en la clinica
    public boolean registrarPaciente(Paciente paciente) {
        if (paciente == null || !paciente.esValido()) {
            System.out.println("Los datos del paciente no son validos.");
            return false;
        }
        if (pacientes.contains(paciente)) {
            System.out.println("Ya existe un paciente con la cedula " + paciente.getCedula() + ".");
            return false;
        }

        paciente.setId(siguienteId());
        pacientes.add(paciente);
        System.out.println("Paciente registrado: " + paciente.getDatosRegistro());
        return true;
    }

    // Metodo que busca un paciente por cedula
    public Paciente buscarPorCedula(String cedula) {
        if (cedula == null) {
            return null;
        }
        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula.trim())) {
                return paciente;
            }
        }
        return null;
    }

    // Metodo que devuelve los pacientes registrados
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    // Metodo que lista los pacientes registrados
    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        List<Paciente> pacientesOrdenados = new ArrayList<>(pacientes);
        pacientesOrdenados.sort(Comparator.comparing(Paciente::getApellido)
                .thenComparing(Paciente::getNombre));
        for (Paciente paciente : pacientesOrdenados) {
            System.out.println(paciente);
        }
    }

    // Metodo que calcula el siguiente id de paciente
    private int siguienteId() {
        return pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
    }

    // TURNOS

    public void asignarTurno(Turno nuevoTurno) {
        if (nuevoTurno == null)
            return;

        if (buscarPorCedula(nuevoTurno.getPaciente().getCedula()) == null) {
            System.out.println("El paciente no existe.");
            return;
        }

        if (buscarPorNombreApellido(nuevoTurno.getMedico().getNombre(), nuevoTurno.getMedico().getApellido()) == null) {
            System.out.println("El medico no existe.");
            return;
        }

        if (turnos.contains(nuevoTurno)) {
            System.out.println("El medico ya tiene un turno en esa fecha y hora.");
            return;
        }

        int idMaximo = 0;
        for (Turno t : turnos) {
            if (t.getId() > idMaximo) {
                idMaximo = t.getId();
            }
        }
        int idGenerado = idMaximo + 1;

        nuevoTurno.setId(idGenerado);
        turnos.add(nuevoTurno);
        System.out.println("Turno asignado exitosamente: " + nuevoTurno);
    }

    public void cancelarTurno(int idTurno) {
        Turno turnoEncontrado = buscarTurnoPorId(idTurno);

        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        EstadoTurno estadoActual = turnoEncontrado.getEstado();
        if (estadoActual == EstadoTurno.ATENDIDO || estadoActual == EstadoTurno.CANCELADO) {
            System.out.println("No se puede cancelar el turno.");
            return;
        }

        turnoEncontrado.setEstado(EstadoTurno.CANCELADO);
        System.out.println("Turno cancelado exitosamente.");
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevoEstado) {
        Turno turnoBuscado = buscarTurnoPorId(idTurno);

        if (turnoBuscado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        turnoBuscado.setEstado(nuevoEstado);
        System.out.println("Estado actualizado a " + nuevoEstado);
    }

    public List<Turno> listarTurnosDelDia(LocalDate fechaConsulta) {
        List<Turno> turnosFiltrados = new ArrayList<>();
        if (fechaConsulta == null)
            return turnosFiltrados;

        for (Turno itemTurno : turnos) {
            if (itemTurno.getFechaHora().toLocalDate().equals(fechaConsulta)) {
                turnosFiltrados.add(itemTurno);
            }
        }

        turnosFiltrados.sort(Comparator.comparing(Turno::getFechaHora));
        return turnosFiltrados;
    }

    // PARTE CASO 6 LIZETH LONDOÑO
    // Aqui se buscaran todos los turnos que pertenecen a un medico
    public List<Turno> buscarPorMedico(Medico medico) {

        // Aqui se crea una lista donde se guardaran los turnos encontrados
        List<Turno> turnosMedico = new ArrayList<>();

        // Aqui se revisara que exista un medico para realizar la busqueda
        if (medico == null) {

            // Aqui se retornara la lista vacia porque no hay un medico para buscar
            return turnosMedico;
        }

        // Aqui se recorrera la lista de turnos registrados
        for (Turno turno : turnos) {

            // Aqui se comparara el medico del turno con el medico que se esta buscando
            if (turno.getMedico().equals(medico)) {

                // Aqui se agregara el turno encontrado a la lista
                turnosMedico.add(turno);
            }
        }

        // Aqui se retornaran todos los turnos encontrados para el medico
        return turnosMedico;
    }
    // ---------------------------------

    private Turno buscarTurnoPorId(int idBuscado) {
        for (Turno registro : turnos) {
            if (registro.getId() == idBuscado) {
                return registro;
            }
        }
        return null;
    }

    // PARTE LIZETH LONDOÑO BLOQUE MEDICO

    // Aqui se crea la lista donde se guardaran los medicos registrados
    private final List<Medico> medicos = new ArrayList<>();

    // Aqui se crea el meto que registrara un medico en la clinica
    public boolean registrarMedico(Medico medico) {

        // Aqui se revisara que el medico exista y que sus datos sean validos
        if (medico == null || !medico.esValido()) {

            // Aqui se muestra un mensaje si los datos del medico no son validos
            System.out.println("Los datos del medico no son validos");

            // Aqui retornara false porque el medico no pudo ser registrado por no tener datos validos
            return false;

        }

        // Aqui se revisara si el medico ya se encuentra registrado
        if (medicos.contains(medico)) {

            // Aqui se mostrara un mensaje si ya existe un medico con el mismo nombre y apellido
            System.out.println("El medico ya se encuentra registrado");

            // Aqui retornara false porque no se tiene permitido registrar un medico repetido
            return false;

        }

        // Aqui se asignara el siguiente identificador disponible al medico
        medico.setId(siguienteIdMedico());

        // Aqui se procede a agregar el medico a la lista de medicos registrados
        medicos.add(medico);

        // Aqui se muestran los datos del medico que fue registrado
        System.out.println("Medico registrado: " + medico.getDatosRegistro());

        // Aqui retornara true porque el medico fue registrado correctamente
        return true;


    }

    // Aqui se devolveran los medicos registrados
    public List<Medico> getMedicos(){

        // Aqui se retornara la lista de medicos registrados
        return  medicos;

    }

    // Aqui se buscara un medico por su nombre y apellido
    public Medico buscarPorNombreApellido(String nombre, String apellido) {

        // Aqui se revisara que el nombre y el apellido tengan informacion
        if (nombre == null || apellido == null) {

            // Aqui retornara null porque no hay datos suficientes para realizar la busqueda
            return null;

        }

        // Aqui se recorrera la lista de medicos registrados
        for (Medico medico : medicos) {

            // Aqui se comparara el nombre y apellido sin importar mayusculas o minusculas
            if (medico.getNombre().equalsIgnoreCase(nombre.trim()) && medico.getApellido().equalsIgnoreCase(apellido.trim())) {

                // Aqui se retornara el medico encontrado
                return medico;

            }

        }

        // Aqui retornara null si no se encontro un medico con ese nombre y apellido
        return null;

    }

    // Aqui se listaram los medicos registrados en la clinica
    public void listarMedicos() {

        // Aqui revisara si no hay medicos registrados
        if (medicos.isEmpty()) {

            // Aqui se mostrara un mensaje si la lista de medicos esta vacia
            System.out.println("No hay medicos registrados.");

            // Aqui se terminara el metodo porque no hay medicos para mostrar
            return;

        }

        // Aqui se crea una nueva lista para ordenar los medicos sin modificar la lista original
        List<Medico> medicosOrdenados = new ArrayList<>(medicos);

        // Aqui se ordenaran los medicos primero por especialidad y despues por apellido
        medicosOrdenados.sort(Comparator.comparing(Medico::getEspecialidad).thenComparing(Medico::getApellido));

        // Aqui se recorrera la lista de medicos ya ordenada
        for (Medico medico : medicosOrdenados) {

            // Aqui se mostrara el mesaje de cada medico registrado
            System.out.println(medico);

        }
    }

    // Aqui se calculara el siguiente identificador para registrar un medico
    private int siguienteIdMedico() {

        // Aqui se buscara el identificador mas alto entre los medicos registrados
        return medicos.stream()
                .mapToInt(Medico::getId)
                .max()
                .orElse(0) + 1;

    }

    // ---------------------------------
}
