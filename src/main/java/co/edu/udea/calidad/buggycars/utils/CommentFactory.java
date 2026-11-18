package co.edu.udea.calidad.buggycars.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Crea textos de comentario únicos. Así la validación encuentra el
 * comentario de esta ejecución y no uno igual escrito antes por otra persona.
 */
public final class CommentFactory {

    private static final String BASE_TEXT = "Great car for testing";
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    private CommentFactory() {
    }

    /**
     * @return un comentario como "Great car for testing 261116183045"
     */
    public static String aUniqueComment() {
        return BASE_TEXT + " " + LocalDateTime.now().format(TIMESTAMP);
    }
}
