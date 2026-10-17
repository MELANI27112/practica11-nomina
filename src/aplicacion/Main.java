package aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {

    // ATRIBUTOS de la clase (estado compartido por los métodos). Todos private.
    // Arreglo simple: la cantidad de meses es fija (12).
    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    // ArrayList: la cantidad de empleados crece mientras se ingresan.
    private static final ArrayList<Empleado> empleados = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int numeroMes;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // punto decimal en los reportes
        seleccionarMes();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion("Seleccione una opción: ", 1, 3);
            switch (opcion) {
                case 1:
                    ingresoNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                default:
                    limpiarPantalla();
                    System.out.println("Gracias por utilizar el sistema de nómina. ¡Hasta pronto!");
            }
        } while (opcion != 3);
    }

    // ------------------------------------------------------------------
    // Selección del mes
    // ------------------------------------------------------------------
    private static void seleccionarMes() {
        limpiarPantalla();
        System.out.println("CÁLCULO DE NÓMINA");
        // Se valida ANTES de usar el valor como índice de meses[].
        numeroMes = leerOpcion("Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

    // ------------------------------------------------------------------
    // Menús
    // ------------------------------------------------------------------
    private static void mostrarEncabezado() {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
    }

    private static void mostrarMenuPrincipal() {
        limpiarPantalla();
        mostrarEncabezado();
        System.out.println("1 - Ingreso de nómina");
        System.out.println("2 - Impresión de nómina");
        System.out.println("3 - Salir");
        System.out.println();
    }

    // ------------------------------------------------------------------
    // Opción 1: ingreso de nómina
    // ------------------------------------------------------------------
    private static void ingresoNomina() {
        int otro;
        do {
            limpiarPantalla();
            mostrarEncabezado();
            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            System.out.println();
            int tipo = leerOpcion("Seleccione el tipo de empleado: ", 1, 2);

            Empleado nuevo;
            if (tipo == 1) {
                nuevo = capturarEmpleadoFijo();
            } else {
                nuevo = capturarEmpleadoPorHora();
            }

            // Se guarda hasta tener todos los datos válidos.
            empleados.add(nuevo);
            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");
            System.out.println();
            otro = leerOpcion("Seleccione una opción: ", 1, 2);
        } while (otro == 1);
    }

    private static Empleado capturarEmpleadoFijo() {
        limpiarPantalla();
        mostrarEncabezado();
        System.out.println("INGRESO DE EMPLEADO FIJO");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double sueldo = leerMonto("Sueldo mensual (Q): ");
        double bonificacion = leerMonto("Bonificación de este mes (Q): ");

        // Instanciación: un objeto independiente por empleado.
        EmpleadoFijo empleado = new EmpleadoFijo(dpi, nombre, sueldo, bonificacion);
        System.out.println();
        System.out.println("Pago del mes = sueldo mensual + bonificación");
        System.out.println(formatearPago(empleado));
        return empleado;
    }

    private static Empleado capturarEmpleadoPorHora() {
        limpiarPantalla();
        mostrarEncabezado();
        System.out.println("INGRESO DE EMPLEADO POR HORA");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double pagoHora = leerMonto("Pago por hora (Q): ");
        double horas = leerMonto("Horas trabajadas este mes: ");

        EmpleadoPorHora empleado = new EmpleadoPorHora(dpi, nombre, pagoHora, horas);
        System.out.println();
        System.out.println("Pago del mes = pago por hora x horas trabajadas");
        System.out.println(formatearPago(empleado));
        return empleado;
    }

    // Método con parámetro (empleado) que devuelve un resultado (String).
    private static String formatearPago(Empleado empleado) {
        return String.format("Pago de %s = Q %.2f", empleado.getNombre(), empleado.calcularPago());
    }

    // ------------------------------------------------------------------
    // Opción 2: impresión de nómina
    // ------------------------------------------------------------------
    private static void imprimirNomina() {
        limpiarPantalla();
        mostrarEncabezado();
        System.out.println();

        double total = 0;
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes");
        } else {
            System.out.printf("%-15s %-25s %-10s %12s%n", "DPI", "Nombre", "Tipo", "Pago (Q)");
            // Recorrido del ArrayList con referencias de tipo Empleado (polimorfismo).
            for (Empleado e : empleados) {
                double pago = e.calcularPago(); // la clase concreta decide la fórmula
                total += pago;
                System.out.printf("%-15s %-25s %-10s %12.2f%n",
                        e.getDpi(), e.getNombre(), e.obtenerTipo(), pago);
            }
        }
        System.out.println();
        System.out.println("Total de trabajadores: " + empleados.size());
        System.out.printf("Total de nómina: Q %.2f%n", total);
        System.out.println();
        System.out.print("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    // ------------------------------------------------------------------
    // Lectura y validación de datos (variables locales: intento, texto...)
    // ------------------------------------------------------------------

    /** Lee un entero entre minimo y maximo; repite hasta que sea válido. */
    private static int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ex) {
                // se muestra el mensaje de error abajo
            }
            System.out.println("Opción inválida. Ingrese un número entre " + minimo + " y " + maximo + ".");
        }
    }

    private static String leerDpi() {
        while (true) {
            System.out.print("DPI: ");
            String dpi = scanner.nextLine().trim();
            if (Empleado.esDpiValido(dpi)) {
                return dpi;
            }
            System.out.println("DPI inválido. Debe tener exactamente 13 dígitos numéricos.");
        }
    }

    private static String leerNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            if (Empleado.esNombreValido(nombre)) {
                return nombre.trim();
            }
            System.out.println("Nombre inválido. No puede estar vacío ni contener solo espacios.");
        }
    }

    private static double leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (Empleado.esMontoValido(valor)) {
                    return valor;
                }
                System.out.println("Valor inválido. Debe ser un número finito mayor o igual a 0.");
            } catch (NumberFormatException ex) {
                System.out.println("Valor inválido. Ingrese un número (ejemplo: 25 o 120.5).");
            }
        }
    }

    // ------------------------------------------------------------------
    // Limpieza de pantalla
    // ------------------------------------------------------------------
    private static void limpiarPantalla() {
        if (System.console() != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            // Consola de IDE: se simula con líneas en blanco.
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
    }
}
