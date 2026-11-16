package co.edu.udea.calidad.buggycars.utils;

import co.edu.udea.calidad.buggycars.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Crea usuarios de prueba con un nombre de usuario único en cada ejecución.
 *
 * <p>Es necesario porque Buggy Cars Rating no permite registrar dos veces el
 * mismo nombre de usuario ni votar dos veces por el mismo auto con la misma
 * cuenta. Con un usuario nuevo en cada ejecución, las pruebas son
 * independientes y repetibles, como lo exige el principio FIRST.</p>
 *
 * <p>Ejemplo de nombre generado: {@code jhon_261116183045_042}
 * (nombre, fecha y hora en formato yyMMddHHmmss y tres dígitos aleatorios).</p>
 */
public final class UserFactory {

    /**
     * Contraseña que cumple la política del sitio: mínimo 8 caracteres,
     * mayúsculas, minúsculas, números y símbolos.
     */
    public static final String VALID_PASSWORD = "Buggy2026*Test";

    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    private static final String DEFAULT_LAST_NAME = "Tester";

    private UserFactory() {
    }

    /**
     * Crea un usuario nuevo con datos válidos y un nombre de usuario único.
     *
     * @param firstName nombre del actor, por ejemplo "Jhon" o "Sebastian"
     * @return usuario listo para registrarse en el sitio
     */
    public static User aNewUserNamed(String firstName) {
        return new User(uniqueUsername(firstName), firstName, DEFAULT_LAST_NAME, VALID_PASSWORD);
    }

    /**
     * Genera un nombre de usuario único a partir de un prefijo.
     *
     * @param prefix texto inicial del nombre de usuario
     * @return nombre de usuario en minúsculas, con la fecha, la hora y un sufijo aleatorio
     */
    public static String uniqueUsername(String prefix) {
        int randomSuffix = ThreadLocalRandom.current().nextInt(1000);
        return String.format("%s_%s_%03d",
                prefix.toLowerCase(), LocalDateTime.now().format(TIMESTAMP), randomSuffix);
    }
}
