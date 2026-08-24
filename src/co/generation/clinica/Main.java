package co.generation.clinica;
import co.generation.clinica.datos.DatosCSV;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

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
                    System.out.println("1");
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
}
