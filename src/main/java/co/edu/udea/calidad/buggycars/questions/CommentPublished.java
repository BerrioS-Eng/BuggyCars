package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.COMMENT_TEXT;

/**
 * Indica si el comentario aparece en la tabla de comentarios del modelo.
 */
public class CommentPublished implements Question<Boolean> {

    private final String comment;

    public CommentPublished(String comment) {
        this.comment = comment;
    }

    public static CommentPublished withText(String comment) {
        return new CommentPublished(comment);
    }

    @Override
    @Subject("the comment '#comment' is published")
    public Boolean answeredBy(Actor actor) {
        return COMMENT_TEXT.of(comment).resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible);
    }
}
