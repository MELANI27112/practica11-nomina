package modelo;

/** Empleado con sueldo mensual fijo más una bonificación del mes. */
public class EmpleadoFijo extends Empleado {

    private double sueldoMensual;
    private double bonificacion;

    // Parámetros: dpi, nombre, sueldoMensual, bonificacion.
    // Los valores que llegan desde Main son los ARGUMENTOS.
    public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion) {
        super(dpi, nombre); // inicializa los datos comunes en la clase base
        setSueldoMensual(sueldoMensual);
        setBonificacion(bonificacion);
    }

    @Override
    public double calcularPago() {
        return sueldoMensual + bonificacion;
    }

    @Override
    public String obtenerTipo() {
        return "Fijo";
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setSueldoMensual(double sueldoMensual) {
        if (!esMontoValido(sueldoMensual)) {
            throw new IllegalArgumentException("El sueldo debe ser un número finito mayor o igual a 0.");
        }
        this.sueldoMensual = sueldoMensual;
    }

    public void setBonificacion(double bonificacion) {
        if (!esMontoValido(bonificacion)) {
            throw new IllegalArgumentException("La bonificación debe ser un número finito mayor o igual a 0.");
        }
        this.bonificacion = bonificacion;
    }
}
