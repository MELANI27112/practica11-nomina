# Práctica 11 – Nómina mensual (Programación II, UMG)

Estructura del proyecto:

```
src/modelo/Empleado.java          (clase abstracta)
src/modelo/EmpleadoFijo.java
src/modelo/EmpleadoPorHora.java
src/aplicacion/Main.java          (menús, captura, reporte)
```

Compilar y ejecutar (desde la carpeta del proyecto):

```
javac -encoding UTF-8 -d out src/modelo/*.java src/aplicacion/*.java
java -cp out aplicacion.Main
```

## Dónde y por qué se usó cada concepto

| Concepto | Dónde | Por qué |
|---|---|---|
| Proyecto, package, import | `package modelo;` y `package aplicacion;`. En `Main`: `import java.util.ArrayList;`, `java.util.Scanner`, `modelo.*` | Separar el modelo (empleados) de la aplicación (menús) y usar clases de otros paquetes. |
| Clase, objeto, instancia, instanciación | Cada clase en su archivo. `new EmpleadoFijo(...)` / `new EmpleadoPorHora(...)` en `Main.capturarEmpleado...` | Cada empleado es una instancia independiente que luego se agrega a la lista. |
| Constructores, parámetros, argumentos, this | `EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion)`; en `Empleado` se usa `this.dpi = dpi` | **Parámetros**: `dpi, nombre, sueldoMensual, bonificacion` (declarados en el constructor). **Argumentos**: los valores `dpi, nombre, sueldo, bonificacion` que `Main` envía en `new EmpleadoFijo(dpi, nombre, sueldo, bonificacion)`. `this` distingue atributo de parámetro. Los constructores no tienen tipo de retorno. |
| Atributos y modificadores de acceso | Todos los atributos son `private`, incluidos `meses`, `empleados` y `scanner` en `Main` | Encapsulación: solo se accede por getters/setters. Parámetros y variables locales no llevan `private`. |
| Getters y setters | `getDpi()`, `getNombre()`, `getSueldoMensual()`, `setBonificacion(...)`, etc. | Los setters conservan las validaciones (lanzan `IllegalArgumentException` si el dato no es válido). |
| Clase y método abstractos | `abstract class Empleado` con `abstract double calcularPago()` | La base solo tiene datos comunes; no se puede hacer `new Empleado(...)`. |
| Herencia y sobrescritura | `extends Empleado`, `super(dpi, nombre)`, `@Override calcularPago()` y `@Override obtenerTipo()` | Las hijas reutilizan DPI y nombre y definen su propia fórmula. |
| Polimorfismo | `ArrayList<Empleado>`; en `imprimirNomina()`: `for (Empleado e : empleados) { e.calcularPago(); }` | El reporte no usa `if`, `switch` ni `instanceof` para elegir la fórmula; la clase concreta decide. |
| `String[] meses` | `private static final String[] meses = {...}` en `Main` | La cantidad de meses es fija (12), por eso un arreglo simple. |
| `meses[numeroMes - 1]` | `Main.mostrarEncabezado()` | Los índices empiezan en 0. Se valida 1–12 con `leerOpcion(..., 1, 12)` antes de usarlo; así ningún dato inválido es índice. |
| `ArrayList`, `add()`, `size()` | `empleados.add(nuevo)` en `ingresoNomina()`; `empleados.size()` en el submenú, la confirmación y el reporte | La cantidad de empleados crece; el contador sale de `size()` y solo aumenta tras guardar. |
| Métodos y main | `main` solo orquesta; delega en `mostrarMenuPrincipal`, `ingresoNomina`, `capturarEmpleadoFijo`, `imprimirNomina`, `leerDpi`, etc. | Evita concentrar todo el programa en `main`. Con parámetros: `leerOpcion(String, int, int)`. Con retorno: `leerMonto(...)` (double), `leerDpi()` (String), `formatearPago(Empleado)` (String). |
| Ámbito y alcance | **Atributo**: `numeroMes` / `empleados` (de `Main`) o `sueldoMensual` (de `EmpleadoFijo`): viven durante todo el programa u objeto y se usan en todos los métodos de su clase. **Parámetro**: `mensaje` en `leerMonto(String mensaje)`: solo existe dentro de ese método. **Variable local**: `total` en `imprimirNomina()` o `texto` en `leerMonto`: solo existen en su bloque. | Estado del objeto en atributos, datos temporales en variables locales, comunicación entre métodos por argumentos o retorno. |

## Comprobación (sección 11)

1. Ingresar mes `3` → "NÓMINA DEL MES: MARZO" (`meses[2]`). `1` → Enero, `12` → Diciembre.
2. Opción 1 → Empleado fijo: DPI `1234567890101`, Ana López, sueldo 4000, bonificación 300 → `Pago de Ana López = Q 4300.00`.
3. Opción 1 → Empleado por hora: DPI `9876543210101`, Luis Pérez, tarifa 25, 120 horas → `Pago de Luis Pérez = Q 3000.00`.
4. Opción 2 → 2 trabajadores, total `Q 7300.00`.
5. Con la lista vacía, la opción 2 muestra "No hay empleados registrados en la nómina de este mes", 0 trabajadores y `Q 0.00`.
6. Entradas inválidas (mes 0 o 13, texto en campos numéricos, valores negativos, DPI sin 13 dígitos, nombre vacío) muestran el error y vuelven a pedir el dato sin cerrar el programa.
