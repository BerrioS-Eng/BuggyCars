package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.LBL_VOTES;

/**
 * Devuelve el número de votos que muestra la página del modelo.
 */
public class VotesCount implements Question<Integer> {

    public static VotesCount ofTheModel() {
        return new VotesCount();
    }

    @Override
    @Subject("the number of votes of the model")
    public Integer answeredBy(Actor actor) {
        return Integer.parseInt(LBL_VOTES.resolveFor(actor).getText().trim());
    }
}
