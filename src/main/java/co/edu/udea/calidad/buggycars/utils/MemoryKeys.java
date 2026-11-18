package co.edu.udea.calidad.buggycars.utils;

/**
 * Nombres con los que el actor guarda y recupera datos entre pasos
 * ({@code actor.remember(...)} y {@code actor.recall(...)}).
 * Al centralizarlos se evitan errores de escritura en textos repetidos.
 */
public final class MemoryKeys {

    public static final String USER = "user";
    public static final String COMMENT = "comment";
    public static final String VOTES_BEFORE = "votesBefore";

    private MemoryKeys() {
    }
}
