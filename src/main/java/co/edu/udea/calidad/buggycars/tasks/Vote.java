package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.interactions.WriteComment;
import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.BTN_VOTE;
import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.LBL_VOTE_CONFIRMATION;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Vota por el modelo que está abierto, con o sin comentario, y espera el
 * mensaje de confirmación "Thank you for your vote!".
 */
public class Vote implements Task {

    private static final int MAX_WAIT_SECONDS = 10;

    private final String comment;

    public Vote(String comment) {
        this.comment = comment;
    }

    public static Vote withComment(String comment) {
        return Tasks.instrumented(Vote.class, comment);
    }

    public static Vote withoutComment() {
        return Tasks.instrumented(Vote.class, "");
    }

    @Override
    @Step("{0} votes for the model")
    public <T extends Actor> void performAs(T actor) {
        if (!comment.isBlank()) {
            actor.attemptsTo(WriteComment.withText(comment));
        }
        actor.attemptsTo(
                Click.on(BTN_VOTE),
                WaitUntil.the(LBL_VOTE_CONFIRMATION, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}
