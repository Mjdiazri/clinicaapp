package co.generation.clinica.model;

import java.util.Objects;
import java.time.LocalDateTime;

public class Turno {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setEstado(estado);
    }

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        this.estado = EstadoTurno.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo.");
        }
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede estar vacio.");
        }
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico no puede estar vacio.");
        }
        this.medico = medico;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden estar vacias.");
        }
        this.fechaHora = fechaHora;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede estar vacio.");
        }
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + estado + "] " +
                paciente.getNombre() + " " + paciente.getApellido() + " - " +
                medico.getNombre() + " " + medico.getApellido() + " (" + medico.getEspecialidad() + ") - " +
                fechaHora;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Turno))
            return false;
        Turno otro = (Turno) obj;
        return medico.equals(otro.medico) && fechaHora.equals(otro.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medico, fechaHora);
    }
}
