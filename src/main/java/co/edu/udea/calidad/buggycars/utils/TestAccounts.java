package co.edu.udea.calidad.buggycars.utils;

import co.edu.udea.calidad.buggycars.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * TEMPORAL: entrega una cuenta creada a mano en Buggy, mientras se termina
 * la automatización del registro. Cuando exista la tarea de registro, los
 * pasos deben usar {@code UserFactory.aNewUserNamed(...)} y esta clase se
 * puede eliminar.
 *
 * <p>Los datos se leen, en este orden, de:</p>
 * <ol>
 *   <li>Las variables de entorno {@code BUGGY_USER}, {@code BUGGY_PASSWORD}
 *       y {@code BUGGY_FIRST_NAME}.</li>
 *   <li>El archivo {@code cuenta.local.properties} en la raíz del proyecto,
 *       con las claves {@code user}, {@code password} y {@code firstName}.
 *       Ese archivo está en el .gitignore: las credenciales no se suben.</li>
 * </ol>
 */
public final class TestAccounts {

    private static final Path LOCAL_FILE = Path.of("cuenta.local.properties");

    private TestAccounts() {
    }

    /**
     * @return la cuenta creada a mano, lista para iniciar sesión
     * @throws IllegalStateException si no se configuró la cuenta
     */
    public static User preexistingAccount() {
        Properties local = readLocalFile();
        String username = read("BUGGY_USER", "user", local);
        String password = read("BUGGY_PASSWORD", "password", local);
        String firstName = read("BUGGY_FIRST_NAME", "firstName", local);
        return new User(username, firstName, "", password);
    }

    private static String read(String environmentVariable, String fileKey, Properties local) {
        String value = System.getenv(environmentVariable);
        if (value == null || value.isBlank()) {
            value = local.getProperty(fileKey);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta configurar la cuenta temporal: defina la variable "
                    + environmentVariable + " o la clave '" + fileKey + "' en " + LOCAL_FILE);
        }
        return value.trim();
    }

    private static Properties readLocalFile() {
        Properties properties = new Properties();
        if (Files.exists(LOCAL_FILE)) {
            try (InputStream input = Files.newInputStream(LOCAL_FILE)) {
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException("No se pudo leer " + LOCAL_FILE, e);
            }
        }
        return properties;
    }
}
