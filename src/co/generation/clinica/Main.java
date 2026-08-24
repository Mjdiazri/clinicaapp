package co.generation.clinica;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;
import co.generation.clinica.model.Paciente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
                    System.out.println("2");
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
                // PARTE CASO 6 LIZETH LONDOÑO
                case 6:
                    System.out.println("6. Ver turnos por medico");

                    // Aqui se llama al metodo que permitira consultar los turnos de un medico
                    verTurnosPorMedico(usuario, servicio);

                    break;
                // ---------------------------------
                case 7:
                    System.out.println("7");
                    break;
                case 8:
                    System.out.println("8");
                    break;
                case 9:
                    System.out.println("9");
                    break;
                // PARTE CASO 10 LIZETH LONDOÑO
                case 10:
                    System.out.println("10. Listar medicos");

                    // Aqui se mostraran los medicos que se encuentran registrados
                    servicio.listarMedicos();
                    break;
                // ---------------------------------
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

    // PARTE CASO 6 LIZETH LONDOÑO
    // Aqui se consultaran los turnos que pertenecen a un medico
    private static void verTurnosPorMedico(Scanner scanner, ClinicaService service) {

        // Aqui se pedira el nombre del medico
        System.out.println("Ingresa el nombre del medico: ");
        String nombre = scanner.nextLine();

        // Aqui se pedira el apellido del medico
        System.out.println("Ingresa el apellido del medico: ");
        String apellido = scanner.nextLine();

        // Aqui se buscara el medico registrado por su nombre y apellido
        Medico medico = service.buscarPorNombreApellido(nombre, apellido);

        // Aqui se revisara si el medico existe
        if (medico == null) {

            // Aqui se mostrara un mensaje si el medico no se encuentra registrado
            System.out.println("El medico no existe.");
            return;
        }

        // Aqui se buscaran los turnos que pertenecen al medico
        List<Turno> turnosMedico = service.buscarPorMedico(medico);

        // Aqui se revisara si el medico no tiene turnos registrados
        if (turnosMedico.isEmpty()) {

            // Aqui se mostrara un mensaje si no existen turnos para el medico
            System.out.println("El medico no tiene turnos registrados.");
            return;
        }

        // Aqui se recorrera la lista de turnos encontrados
        for (Turno turno : turnosMedico) {

            // Aqui se mostrara cada turno encontrado
            System.out.println(turno);
        }
    }
    // ---------------------------------



}
