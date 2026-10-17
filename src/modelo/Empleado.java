package modelo;

/**
 * Clase abstracta: concentra los datos comunes (DPI y nombre).
 * No se pueden crear objetos directamente de esta clase (new Empleado(...) no compila).
 */
public abstract class Empleado {

    // ATRIBUTOS: estado que pertenece al objeto. Todos private (encapsulación).
    private String dpi;
    private String nombre;

    // CONSTRUCTOR con parámetros (dpiParam y nombreParam son PARÁMETROS).
    // "this" distingue el atributo del parámetro cuando se llaman igual.
    public Empleado(String dpi, String nombre) {
        setDpi(dpi);
        setNombre(nombre);
    }

    // Método abstracto: cada clase hija define su propia fórmula.
    public abstract double calcularPago();

    // Cada hija sobrescribe este método para indicar su tipo.
    public abstract String obtenerTipo();

    // ---------- Validaciones reutilizables (métodos que devuelven resultado) ----------

    /** DPI válido: exactamente 13 dígitos. */
    public static boolean esDpiValido(String dpi) {
        return dpi != null && dpi.matches("\\d{13}");
    }

    /** Nombre válido: no vacío ni solo espacios. */
    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    /** Monto válido: número finito mayor o igual a 0. */
    public static boolean esMontoValido(double valor) {
        return !Double.isNaN(valor) && !Double.isInfinite(valor) && valor >= 0;
    }

    // ---------- Getters ----------
    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    // ---------- Setters (conservan las validaciones) ----------
    private void setDpi(String dpi) {
        if (!esDpiValido(dpi)) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 dígitos.");
        }
        this.dpi = dpi;
    }

    private void setNombre(String nombre) {
        if (!esNombreValido(nombre)) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }
}
