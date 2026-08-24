package co.generation.clinica.service;

import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Turno;
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

    private Turno buscarTurnoPorId(int idBuscado) {
        for (Turno registro : turnos) {
            if (registro.getId() == idBuscado) {
                return registro;
            }
        }
        return null;
    }
}
