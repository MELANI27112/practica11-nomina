package modelo;

/** Empleado que cobra por hora trabajada en el mes. */
public class EmpleadoPorHora extends Empleado {

    private double pagoPorHora;
    private double horasTrabajadas;

    public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre);
        setPagoPorHora(pagoPorHora);
        setHorasTrabajadas(horasTrabajadas);
    }

    @Override
    public double calcularPago() {
        return pagoPorHora * horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }

    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setPagoPorHora(double pagoPorHora) {
        if (!esMontoValido(pagoPorHora)) {
            throw new IllegalArgumentException("El pago por hora debe ser un número finito mayor o igual a 0.");
        }
        this.pagoPorHora = pagoPorHora;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        if (!esMontoValido(horasTrabajadas)) {
            throw new IllegalArgumentException("Las horas deben ser un número finito mayor o igual a 0.");
        }
        this.horasTrabajadas = horasTrabajadas;
    }
}
