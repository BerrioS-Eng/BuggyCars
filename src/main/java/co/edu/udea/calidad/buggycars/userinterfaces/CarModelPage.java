package co.edu.udea.calidad.buggycars.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Elementos de la página de detalle de un modelo de auto, donde se vota y
 * se ven los comentarios.
 */
public final class CarModelPage {

    public static final String VOTE_CONFIRMATION_TEXT = "Thank you for your vote!";

    /** Número de votos: el texto en negrita dentro de "Votes: N". */
    public static final Target LBL_VOTES = Target.the("number of votes")
            .located(By.xpath("//h4[contains(normalize-space(),'Votes:')]/strong"));

    public static final Target TXT_COMMENT = Target.the("comment field")
            .located(By.id("comment"));

    public static final Target BTN_VOTE = Target.the("vote button")
            .located(By.xpath("//button[normalize-space()='Vote!']"));

    public static final Target LBL_VOTE_CONFIRMATION = Target.the("vote confirmation")
            .located(By.xpath("//p[@class='card-text' and normalize-space()='" + VOTE_CONFIRMATION_TEXT + "']"));

    /**
     * Celda "Author" de la fila cuyo comentario es exactamente el texto
     * indicado. Uso: {@code COMMENT_AUTHOR.of("texto del comentario")}.
     */
    public static final Target COMMENT_AUTHOR = Target.the("author of the comment '{0}'")
            .locatedBy("//table[contains(@class,'table')]//tr[td[3][normalize-space()='{0}']]/td[2]");

    private CarModelPage() {
    }
}
