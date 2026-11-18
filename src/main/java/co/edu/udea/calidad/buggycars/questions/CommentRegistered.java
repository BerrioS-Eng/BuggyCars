package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.COMMENT_AUTHOR;

/**
 * Indica si en la tabla de comentarios del modelo hay una fila con el texto
 * del comentario y con el nombre del autor esperado.
 */
public class CommentRegistered implements Question<Boolean> {

    private final String comment;
    private final String authorName;

    public CommentRegistered(String comment, String authorName) {
        this.comment = comment;
        this.authorName = authorName;
    }

    public static CommentRegistered withTextAndAuthor(String comment, String authorName) {
        return new CommentRegistered(comment, authorName);
    }

    @Override
    @Subject("the comment '#comment' is registered by #authorName")
    public Boolean answeredBy(Actor actor) {
        return COMMENT_AUTHOR.of(comment).resolveAllFor(actor).stream()
                .anyMatch(authorCell -> authorCell.getText().contains(authorName));
    }
}
