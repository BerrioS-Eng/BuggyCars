package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_USERNAME;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Abre la página de registro por su URL ({@code pages.buggyCarsRegister} en
 * serenity.conf) y espera a que el formulario esté listo.
 *
 * <p>Se usa la URL y no el botón del encabezado porque Angular ignora la
 * navegación a la ruta actual: el formulario y el mensaje del intento
 * anterior seguirían en pantalla. Abrir la URL garantiza un formulario
 * limpio.</p>
 */

public class GoToRegistration implements Task {
    private static final String REGISTRATION_PAGE = "pages.buggyCarsRegister";
    private static final int MAX_WAIT_SECONDS = 10;

    public static GoToRegistration page() {
        return Tasks.instrumented(GoToRegistration.class);
    }

    @Override
    @Step("{0} goes to the registration page")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.browserOn().thePageNamed(REGISTRATION_PAGE),
                WaitUntil.the(TXT_USERNAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}