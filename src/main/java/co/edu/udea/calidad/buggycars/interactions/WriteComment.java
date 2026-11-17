package co.edu.udea.calidad.buggycars.interactions;

import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.TXT_COMMENT;

/**
 * Escribe un texto en el campo "Your Comment" de la página del modelo.
 */
public class WriteComment implements Interaction {

    private final String comment;

    public WriteComment(String comment) {
        this.comment = comment;
    }

    public static WriteComment withText(String comment) {
        return Tasks.instrumented(WriteComment.class, comment);
    }

    @Override
    @Step("{0} writes the comment '#comment'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(comment).into(TXT_COMMENT));
        Wait.aLittleBitTime();
    }
}
