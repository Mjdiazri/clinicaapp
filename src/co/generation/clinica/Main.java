package co.generation.clinica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;

public class Main {
    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);

        try (Scanner usuario = new Scanner(System.in)) {
            int opcionMenu;
            do {
                mostrarMenu();
                System.out.print("Selecciona una opcion: ");
                opcionMenu = leerEntero(usuario);
                switch (opcionMenu) {
                    case 1 -> {
                        imprimirTitulo("Registrar paciente");
                        registrarPaciente(usuario, servicio);
                    }
                    case 2 -> {
                        imprimirTitulo("Registrar medico");
                        registrarMedico(usuario, servicio);
                    }
                    case 3 -> {
                        imprimirTitulo("Asignar turno");
                        registrarTurno(usuario, servicio);
                    }
                    case 4 -> {
                        imprimirTitulo("Listar turnos del dia");
                        listarTurnosDelDia(servicio);
                    }
                    case 5 -> {
                        imprimirTitulo("Cancelar turno");
                        cancelarTurno(usuario, servicio);
                    }
                    case 6 -> {
                        imprimirTitulo("Ver turnos por medico");
                        verTurnosPorMedico(usuario, servicio);
                    }
                    case 7 -> {
                        imprimirTitulo("Ver turnos por paciente");
                        verTurnosPorPaciente(usuario, servicio);
                    }
                    case 8 -> {
                        imprimirTitulo("Cambiar estado de turno");
                        cambiarEstadoTurno(usuario, servicio);
                    }
                    case 9 -> {
                        imprimirTitulo("Listar pacientes");
                        servicio.listarPacientes();
                    }
                    case 10 -> {
                        imprimirTitulo("Listar medicos");
                        servicio.listarMedicos();
                    }
                    case 0 -> {
                        DatosCSV.guardar(servicio);
                        System.out.println("Hasta pronto. Datos guardados.");
                    }
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcionMenu != 0);
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                ########################################
                #       CLINICAAPP - MENU              #
                ########################################
                #  1. Registrar paciente               #
                #  2. Registrar medico                 #
                #  3. Asignar turno                    #
                #  4. Listar turnos del dia            #
                #  5. Cancelar turno                   #
                #  6. Ver turnos por medico            #
                #  7. Ver turnos por paciente          #
                #  8. Cambiar estado de turno          #
                #  9. Listar pacientes                 #
                # 10. Listar medicos                   #
                #  0. Salir                            #
                ########################################
                """);
    }

    private static void imprimirTitulo(String titulo) { System.out.println("\n--- " + titulo + " ---"); }

    private static void registrarPaciente(Scanner scanner, ClinicaService service) {
        try {
            System.out.print("Ingresa la cedula: "); String cedula = scanner.nextLine();
            System.out.print("Ingresa el nombre: "); String nombre = scanner.nextLine();
            System.out.print("Ingresa el apellido: "); String apellido = scanner.nextLine();
            System.out.print("Ingresa el telefono: "); String telefono = scanner.nextLine();
            service.registrarPaciente(new Paciente(cedula, nombre, apellido, telefono));
        } catch (IllegalArgumentException exception) { System.out.println("Error de validacion: " + exception.getMessage()); }
    }

    private static void registrarMedico(Scanner scanner, ClinicaService service) {
        try {
            System.out.print("Ingresa el nombre: "); String nombre = scanner.nextLine();
            System.out.print("Ingresa el apellido: "); String apellido = scanner.nextLine();
            System.out.print("Ingresa la especialidad (GENERAL, PEDIATRIA, CARDIOLOGIA o URGENCIAS): ");
            Especialidad especialidad = Especialidad.valueOf(scanner.nextLine().trim().toUpperCase(Locale.ROOT));
            service.registrarMedico(new Medico(nombre, apellido, especialidad));
        } catch (IllegalArgumentException exception) { System.out.println("Error de validacion: " + exception.getMessage()); }
    }

    private static void registrarTurno(Scanner scanner, ClinicaService service) {
        try {
            System.out.print("Ingresa la cedula del paciente: "); Paciente paciente = service.buscarPorCedula(scanner.nextLine());
            if (paciente == null) { System.out.println("No se encontro un paciente con esa cedula."); return; }
            System.out.print("Ingresa el nombre del medico: "); String nombreMedico = scanner.nextLine();
            System.out.print("Ingresa el apellido del medico: "); Medico medico = service.buscarPorNombreApellido(nombreMedico, scanner.nextLine());
            if (medico == null) { System.out.println("No se encontro un medico con ese nombre y apellido."); return; }
            System.out.print("Ingresa el anio: "); int anio = leerEntero(scanner);
            System.out.print("Ingresa el mes: "); int mes = leerEntero(scanner);
            System.out.print("Ingresa el dia: "); int dia = leerEntero(scanner);
            System.out.print("Ingresa la hora: "); int hora = leerEntero(scanner);
            System.out.print("Ingresa el minuto: "); int minuto = leerEntero(scanner);
            service.asignarTurno(new Turno(paciente, medico, LocalDateTime.of(anio, mes, dia, hora, minuto)));
        } catch (IllegalArgumentException exception) { System.out.println("Error de validacion: " + exception.getMessage()); }
    }

    private static void listarTurnosDelDia(ClinicaService service) {
        List<Turno> turnos = service.listarTurnosDelDia(LocalDate.now());
        if (turnos.isEmpty()) { System.out.println("No hay turnos para el dia de hoy."); return; }
        System.out.println("Turnos del dia:"); turnos.forEach(System.out::println);
    }

    private static void cancelarTurno(Scanner scanner, ClinicaService service) {
        System.out.print("Ingresa el ID del turno a cancelar: "); service.cancelarTurno(leerEntero(scanner));
    }

    private static void verTurnosPorMedico(Scanner scanner, ClinicaService service) {
        System.out.print("Ingresa el nombre del medico: "); String nombre = scanner.nextLine();
        System.out.print("Ingresa el apellido del medico: "); Medico medico = service.buscarPorNombreApellido(nombre, scanner.nextLine());
        if (medico == null) { System.out.println("El medico no existe."); return; }
        imprimirTurnos(service.buscarPorMedico(medico), "El medico no tiene turnos registrados.");
    }

    private static void verTurnosPorPaciente(Scanner scanner, ClinicaService service) {
        System.out.print("Ingresa la cedula del paciente: "); Paciente paciente = service.buscarPorCedula(scanner.nextLine());
        if (paciente == null) { System.out.println("Paciente no encontrado."); return; }
        imprimirTurnos(service.buscarPorPaciente(paciente), "El paciente no tiene turnos registrados.");
    }

    private static void cambiarEstadoTurno(Scanner scanner, ClinicaService service) {
        System.out.print("Ingresa el ID del turno: "); int idTurno = leerEntero(scanner);
        if (!service.existeTurno(idTurno)) { System.out.println("Turno no encontrado."); return; }
        System.out.print("Ingresa el nuevo estado (PENDIENTE, ATENDIDO o CANCELADO): ");
        try { service.cambiarEstadoTurno(idTurno, EstadoTurno.valueOf(scanner.nextLine().trim().toUpperCase(Locale.ROOT))); }
        catch (IllegalArgumentException exception) { System.out.println("Estado no valido."); }
    }

    private static void imprimirTurnos(List<Turno> turnos, String mensajeVacio) {
        if (turnos.isEmpty()) { System.out.println(mensajeVacio); return; }
        turnos.forEach(System.out::println);
    }

    private static int leerEntero(Scanner scanner) {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException exception) { System.out.println("Debe ingresar un numero valido."); return -1; }
    }
}
