package co.generation.clinica;
import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;
import co.generation.clinica.model.Paciente;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);
        //Entrada de datos
        Scanner usuario = new Scanner(System.in);
        int opcionMenu;

        //Menu
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
            switch (opcionMenu){
                case 1:
                    System.out.println("1. Registra los datos del paciente");
                    registrarPaciente(usuario, servicio);
                    break;
                case 2:
                    System.out.println("2");
                    break;
                case 3:
                    System.out.println("Por favor ingresa tu cedula");
                    //Pedir cedula
                    int cedula = usuario.nextInt();
                    usuario.nextLine();
                    //Pedir datos doctor
                    System.out.println("Por favor ingresa el nombre y apellido del doctor");
                    String datosDoc = usuario.nextLine();
                    //Validar datos
                    //Pedir Datos Fecha
                    System.out.println("Ingresa el año: ");
                    int anio = usuario.nextInt();
                    System.out.println("Ingresa el numero del mes: ");
                    int mes = usuario.nextInt();
                    System.out.println("Ingresa los dos digitos correspondientes al dia: ");
                    int dia = usuario.nextInt();
                    System.out.println("Ingresa los dos digitos correspondientes a la hora: ");
                    int hora = usuario.nextInt();
                    System.out.println("Ingresa los dos digitos correspondientes al minuto: ");
                    int minuto = usuario.nextInt();
                    //Crear fecha
                    LocalDateTime.of(anio,mes, dia, hora, minuto);
                    break;
                case 4:
                    System.out.println("4");
                    break;
                case 5:
                    System.out.println("5");
                    break;
                case 6:
                    System.out.println("6");
                    break;
                case 7:
                    System.out.println("7");
                    break;
                case 8:
                    System.out.println("8");
                    break;
                case 9:
                    System.out.println("9");
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

        }
        while (opcionMenu != 0);
        usuario.close();



    }
    private static void registrarPaciente(Scanner scanner, ClinicaService service){
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
            System.out.println("Error de validación: " +exception.getMessage());
        }
    }

    private static void registrarMedico(Scanner scanner, ClinicaService service){
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
            System.out.println("Error de validación: " +exception.getMessage());
        }


        private static void registrarTurno(Scanner scanner, ClinicaService service){
            try {

                System.out.println("Por favor ingresa tu cedula");
                //Pedir cedula
                int cedula = usuario.nextInt();
                usuario.nextLine();
                //Pedir datos doctor
                System.out.println("Por favor ingresa el nombre y apellido del doctor");
                String datosDoc = usuario.nextLine();
                //Validar datos
                //Pedir Datos Fecha
                System.out.println("Ingresa el año: ");
                int anio = usuario.nextInt();
                System.out.println("Ingresa el numero del mes: ");
                int mes = usuario.nextInt();
                System.out.println("Ingresa los dos digitos correspondientes al dia: ");
                int dia = usuario.nextInt();
                System.out.println("Ingresa los dos digitos correspondientes a la hora: ");
                int hora = usuario.nextInt();
                System.out.println("Ingresa los dos digitos correspondientes al minuto: ");
                int minuto = usuario.nextInt();
                //Crear fecha
                LocalDateTime.of(anio,mes, dia, hora, minuto);
                Paciente
                Turno nuevoTurno=new Turno()
            } catch (IllegalArgumentException exception) {
                System.out.println("Error de validación: " +exception.getMessage());
            }



        }

