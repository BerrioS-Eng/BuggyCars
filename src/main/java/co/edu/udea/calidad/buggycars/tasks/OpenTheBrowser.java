package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

/**
 * Abre el navegador en la página principal de Buggy Cars Rating.
 *
 * <p>La URL no está escrita en el código: se toma de {@code pages.buggyCars}
 * en {@code serenity.conf}.</p>
 */
public class OpenTheBrowser implements Task {

    private static final String HOME_PAGE = "pages.buggyCars";

    /**
     * Uso: {@code actor.attemptsTo(OpenTheBrowser.onTheHomePage());}
     *
     * @return la tarea instrumentada para que aparezca en el reporte de Serenity
     */
    public static OpenTheBrowser onTheHomePage() {
        return Tasks.instrumented(OpenTheBrowser.class);
    }

    @Override
    @Step("{0} opens the Buggy Cars Rating home page")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn().thePageNamed(HOME_PAGE));
        Wait.aLittleBitTime();
    }
}
