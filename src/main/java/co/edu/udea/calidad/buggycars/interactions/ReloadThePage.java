package co.edu.udea.calidad.buggycars.interactions;

import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.CarModelPage.LBL_VOTES;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Recarga la página del modelo y espera a que vuelva a cargar.
 *
 * <p>Es necesaria porque el sitio no actualiza la tabla de comentarios
 * después de votar: el comentario nuevo solo aparece al recargar.</p>
 */
public class ReloadThePage implements Interaction {

    private static final int MAX_WAIT_SECONDS = 10;

    public static ReloadThePage ofTheModel() {
        return Tasks.instrumented(ReloadThePage.class);
    }

    @Override
    @Step("{0} reloads the model page")
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).getDriver().navigate().refresh();
        actor.attemptsTo(WaitUntil.the(LBL_VOTES, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}
