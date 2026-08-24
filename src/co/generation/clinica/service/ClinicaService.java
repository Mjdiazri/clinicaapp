package co.generation.clinica.service;

import co.generation.clinica.model.Paciente;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService {

    private final List<Paciente> pacientes = new ArrayList<>();

    //Metodo que registra un paciente en la clinica
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

    //Metodo que busca un paciente por cedula
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

    //Metodo que devuelve los pacientes registrados
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    //Metodo que lista los pacientes registrados
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

    //Metodo que calcula el siguiente id de paciente
    private int siguienteId() {
        return pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
    }
}
