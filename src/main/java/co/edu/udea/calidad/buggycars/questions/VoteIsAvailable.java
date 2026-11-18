package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.BTN_VOTE;

/**
 * Indica si el botón "Vote!" está visible, es decir, si el actor todavía
 * puede votar por el modelo abierto.
 */
public class VoteIsAvailable implements Question<Boolean> {

    public static VoteIsAvailable forTheModel() {
        return new VoteIsAvailable();
    }

    @Override
    @Subject("the vote button is available")
    public Boolean answeredBy(Actor actor) {
        return BTN_VOTE.resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible);
    }
}
