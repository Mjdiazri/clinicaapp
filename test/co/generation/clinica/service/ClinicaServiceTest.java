package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClinicaServiceTest {
    private ClinicaService servicio;
    private Paciente paciente;
    private Medico medico;

    @BeforeEach
    void prepararDatos() {
        servicio = new ClinicaService();
        paciente = new Paciente("1020304050", " Maria ", " Garcia ", "3001234567");
        medico = new Medico(" Carlos ", " Perez ", Especialidad.CARDIOLOGIA);
    }

    @Test
    void implementaElContratoConsultable() {
        assertInstanceOf(Consultable.class, servicio);
    }

    @Test
    void registraPacienteNormalizaDatosYEvitaCedulaDuplicada() {
        assertTrue(servicio.registrarPaciente(paciente));
        assertEquals(1, paciente.getId());
        assertEquals("Maria", paciente.getNombre());
        assertEquals("Garcia", paciente.getApellido());
        assertSame(paciente, servicio.buscarPorCedula(" 1020304050 "));
        assertFalse(servicio.registrarPaciente(new Paciente("1020304050", "Otra", "Persona", "3007654321")));
        assertEquals(1, servicio.getPacientes().size());
    }

    @Test
    void validaFormatosDePaciente() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente("ABC", "Maria", "Garcia", "3001234567"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("123", "Maria", "Garcia", "12345"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("123", "", "Garcia", "3001234567"));
    }

    @Test
    void registraMedicoNormalizaDatosYEvitaDuplicadosSinDistinguirMayusculas() {
        assertTrue(servicio.registrarMedico(medico));
        assertEquals(1, medico.getId());
        assertEquals("Carlos", medico.getNombre());
        assertEquals("Perez", medico.getApellido());
        assertSame(medico, servicio.buscarPorNombreApellido("carlos", "PEREZ"));
        assertFalse(servicio.registrarMedico(new Medico("CARLOS", "perez", Especialidad.GENERAL)));
        assertThrows(IllegalArgumentException.class, () -> new Medico("", "Perez", Especialidad.GENERAL));
        assertThrows(IllegalArgumentException.class, () -> new Medico("Carlos", "Perez", null));
    }

    @Test
    void asignaTurnoConEstadoPendienteIdYEvitaConflictoDeAgenda() {
        servicio.registrarPaciente(paciente);
        servicio.registrarMedico(medico);
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 10, 9, 30);
        Turno turno = new Turno(paciente, medico, fecha);

        servicio.asignarTurno(turno);
        servicio.asignarTurno(new Turno(paciente, medico, fecha));

        assertAll(
                () -> assertEquals(1, turno.getId()),
                () -> assertEquals(EstadoTurno.PENDIENTE, turno.getEstado()),
                () -> assertEquals(1, servicio.getTurnos().size()),
                () -> assertEquals("[PENDIENTE] Maria Garcia - Carlos Perez (CARDIOLOGIA) - 2026-06-10T09:30", turno.toString())
        );
    }

    @Test
    void consultaTurnosOrdenadosYCambiaEstadosSegunReglas() {
        servicio.registrarPaciente(paciente);
        servicio.registrarMedico(medico);
        Turno tarde = new Turno(paciente, medico, LocalDateTime.of(2026, 6, 10, 15, 0));
        Turno manana = new Turno(paciente, medico, LocalDateTime.of(2026, 6, 10, 9, 0));
        servicio.asignarTurno(tarde);
        servicio.asignarTurno(manana);

        List<Turno> delDia = servicio.listarTurnosDelDia(LocalDate.of(2026, 6, 10));
        assertEquals(List.of(manana, tarde), delDia);
        assertEquals(List.of(manana, tarde), servicio.buscarPorMedico(medico));
        assertEquals(List.of(manana, tarde), servicio.buscarPorPaciente(paciente));

        servicio.cancelarTurno(manana.getId());
        assertEquals(EstadoTurno.CANCELADO, manana.getEstado());
        servicio.cambiarEstadoTurno(tarde.getId(), EstadoTurno.ATENDIDO);
        assertEquals(EstadoTurno.ATENDIDO, tarde.getEstado());
        servicio.cancelarTurno(tarde.getId());
        assertEquals(EstadoTurno.ATENDIDO, tarde.getEstado());
    }

}
