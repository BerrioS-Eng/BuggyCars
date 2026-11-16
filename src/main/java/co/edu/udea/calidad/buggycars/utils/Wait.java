package co.edu.udea.calidad.buggycars.utils;

/**
 * Pausa la ejecución entre pasos para que, durante la exposición, se pueda
 * observar con detenimiento lo que hace el navegador.
 *
 * <p>Tiene fines académicos: en un proyecto real se espera por condiciones
 * (por ejemplo, que un elemento sea visible) y no por un tiempo fijo.</p>
 *
 * <p>La pausa por defecto es de 2000 ms y se puede cambiar al ejecutar:
 * {@code gradlew clean test aggregate -Dstep.pause=0} (sin pausas) o
 * {@code -Dstep.pause=3000} (3 segundos).</p>
 */
public final class Wait {

    private static final int DEFAULT_PAUSE_MS = 2000;

    private Wait() {
    }

    /**
     * Pausa la ejecución el tiempo configurado en {@code step.pause}
     * (2000 ms si no se indica).
     */
    public static void aLittleBitTime() {
        aLittleBitTime(Integer.getInteger("step.pause", DEFAULT_PAUSE_MS));
    }

    /**
     * Pausa la ejecución la cantidad de milisegundos indicada.
     *
     * @param milliseconds tiempo de la pausa en milisegundos
     */
    public static void aLittleBitTime(int milliseconds) {
        if (milliseconds <= 0) {
            return;
        }
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
