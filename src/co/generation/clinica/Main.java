package co.generation.clinica;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;
import co.generation.clinica.model.Paciente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);
        // Entrada de datos
        Scanner usuario = new Scanner(System.in);
        int opcionMenu;

        // Menu
        do {
            System.out.println("""
                    ▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
                    ▫     CLINICA-APP MENU          ▫
                    ▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
                    ▫  1.  Registrar paciente        ▫
                    ▫  2.  Registrar médico          ▫
                    ▫  3.  Asignar turno             ▫
                    ▫  4.  Listar turnos del día     ▫
                    ▫  5.  Cancelar turno            ▫
                    ▫  6.  Ver turnos por médico     ▫
                    ▫  7.  Ver turnos por paciente   ▫
                    ▫  8.  Cambiar estado de turno   ▫
                    ▫  9.  Listar pacientes          ▫
                    ▫  10. Listar médicos            ▫
                    ▫  0.  Salir                     ▫
                    ▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
                    """);
            System.out.println("Selecciona una opcion: ");
            opcionMenu = usuario.nextInt();
            usuario.nextLine();
            switch (opcionMenu) {
                case 1:
                    System.out.println("1. Registra los datos del paciente");
                    registrarPaciente(usuario, servicio);
                    break;
                case 2:
                    System.out.println("2. Registra los datos del medico");
                    registrarMedico(usuario, servicio);
                    break;
                case 3:
                    registrarTurno(usuario, servicio);
                    break;
                case 4:
                    listarTurnosDelDia(servicio);
                    break;
                case 5:
                    cancelarTurno(usuario, servicio);
                    break;
                case 6:
                    System.out.println("6");
                    break;
                case 7:
                    verTurnosPorPaciente(usuario, servicio);
                    break;
                case 8:
                    cambiarEstadoTurno(usuario, servicio);
                    break;
                case 9:
                    servicio.listarPacientes();
                    break;
                case 10:
                    System.out.println("10");
                    break;
                case 0:
                    DatosCSV.guardar(servicio);
                    System.out.println("Hasta pronto. Datos Guardados");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcionMenu != 0);
        usuario.close();

    }

    private static void registrarPaciente(Scanner scanner, ClinicaService service) {
        try {
            System.out.println("Ingresa tu cedula: ");
            String cedula = scanner.nextLine();

            System.out.println("Ingresa tu nomre: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingresa tu appellido: ");
            String apellido = scanner.nextLine();

            System.out.println("Ingresa tu telefono: ");
            String telefono = scanner.nextLine();

            service.registrarPaciente(new Paciente(cedula, nombre, apellido, telefono));

        } catch (IllegalArgumentException exception) {
            System.out.println("Error de validación: " + exception.getMessage());
        }
    }

    //Metodo que muestra los turnos de un paciente
    private static void verTurnosPorPaciente(Scanner scanner, ClinicaService servicio) {
        System.out.println("Ingrese la cedula del paciente: ");
        String cedula = scanner.nextLine();
        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        List<Turno> turnosPaciente = servicio.buscarPorPaciente(paciente);
        if (turnosPaciente.isEmpty()) {
            System.out.println("El paciente no tiene turnos registrados.");
            return;
        }

        for (Turno turno : turnosPaciente) {
            System.out.println(turno);
        }
    }

    //Metodo que cambia el estado de un turno
    private static void cambiarEstadoTurno(Scanner scanner, ClinicaService servicio) {
        System.out.println("Ingrese el id del turno: ");
        int idTurno = leerEntero(scanner);
        if (!servicio.existeTurno(idTurno)) {
            System.out.println("Turno no encontrado.");
            return;
        }

        System.out.println("Ingrese el nuevo estado (PENDIENTE, ATENDIDO o CANCELADO): ");
        String estadoIngresado = scanner.nextLine();

        try {
            EstadoTurno nuevoEstado = EstadoTurno.valueOf(estadoIngresado.trim().toUpperCase());
            servicio.cambiarEstadoTurno(idTurno, nuevoEstado);
        } catch (IllegalArgumentException exception) {
            System.out.println("Estado no valido.");
        }
    }

    //Metodo que lee un numero entero del menu
    private static int leerEntero(Scanner scanner) {
        String valor = scanner.nextLine().trim();
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException exception) {
            System.out.println("Debe ingresar un numero valido.");
            return -1;
        }
    }

    private static void registrarMedico(Scanner scanner, ClinicaService service) {
        try {

            System.out.println("Ingresa tu nomre: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingresa tu apellido: ");
            String apellido = scanner.nextLine();

            System.out.println("Ingresa tu especialidad: ");
            String nombreEspecialidad = scanner.nextLine();
            Especialidad especialidad = Especialidad.valueOf(nombreEspecialidad.toUpperCase());

            service.registrarMedico(new Medico(nombre, apellido, especialidad));

        } catch (IllegalArgumentException exception) {
            System.out.println("Error de validación: " + exception.getMessage());
        }
    }

    private static void registrarTurno(Scanner scanner, ClinicaService service) {
        try {
            System.out.println("Ingresa la cedula del paciente: ");
            String cedula = scanner.nextLine();

            Paciente paciente = service.buscarPorCedula(cedula);
            if (paciente == null) {
                System.out.println("No se encontro un paciente con esa cedula.");
                return;
            }

            System.out.println("Ingresa el nombre del medico: ");
            String nombreMedico = scanner.nextLine();
            System.out.println("Ingresa el apellido del medico: ");
            String apellidoMedico = scanner.nextLine();

            Medico medico = service.buscarPorNombreApellido(nombreMedico, apellidoMedico);
            if (medico == null) {
                System.out.println("No se encontro un medico con ese nombre y apellido.");
                return;
            }

            System.out.println("Ingresa el anio: ");
            int anio = scanner.nextInt();
            System.out.println("Ingresa el mes: ");
            int mes = scanner.nextInt();
            System.out.println("Ingresa el dia: ");
            int dia = scanner.nextInt();
            System.out.println("Ingresa la hora: ");
            int hora = scanner.nextInt();
            System.out.println("Ingresa el minuto: ");
            int minuto = scanner.nextInt();
            scanner.nextLine();

            LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
            Turno nuevoTurno = new Turno(paciente, medico, fechaHora);
            service.asignarTurno(nuevoTurno);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarTurnosDelDia(ClinicaService service) {
        List<Turno> turnos = service.listarTurnosDelDia(LocalDate.now());
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para el dia de hoy.");
            return;
        }
        System.out.println("Turnos del dia:");
        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    private static void cancelarTurno(Scanner scanner, ClinicaService service) {
        System.out.println("Ingresa el ID del turno a cancelar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        service.cancelarTurno(id);
    }
}
