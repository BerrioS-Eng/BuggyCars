package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.LBL_VOTES;
import static co.edu.udea.calidad.buggycars.userinterfaces.HomePage.POPULAR_MODEL_LINK;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Desde la página principal, entra al modelo de la tarjeta "Popular Model"
 * y espera a que cargue su página de detalle.
 */
public class SelectMostPopularModel implements Task {

    private static final int MAX_WAIT_SECONDS = 10;

    public static SelectMostPopularModel fromTheHomePage() {
        return Tasks.instrumented(SelectMostPopularModel.class);
    }

    @Override
    @Step("{0} selects the most popular model")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(POPULAR_MODEL_LINK, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Click.on(POPULAR_MODEL_LINK),
                WaitUntil.the(LBL_VOTES, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}
