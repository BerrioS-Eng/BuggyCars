package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.interactions.FillRegistrationForm;
import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.BTN_REGISTER;
import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.LBL_RESULT;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Registra un usuario en Buggy Cars Rating: abre el formulario, lo diligencia,
 * lo envía y espera la respuesta del sitio.
 *
 * <p>Espera a que aparezca el mensaje de resultado, sea de éxito o de error,
 * y no verifica cuál es: eso le corresponde a las Questions. Así la misma
 * tarea sirve para el camino feliz y para los escenarios de error.</p>
 *
 * <p>Uso para crear una cuenta nueva desde cualquier feature:
 * {@code actor.attemptsTo(Register.with(UserFactory.aNewUserNamed("Jhon")));}</p>
 */

public class Register implements Task {

    private static final int MAX_WAIT_SECONDS = 10;

    private final User user;

    public Register(User user) {
        this.user = user;
    }

    public static Register with(User user) {
        return Tasks.instrumented(Register.class, user);
    }

    @Override
    @Step("{0} register as #user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GoToRegistration.page(),
                FillRegistrationForm.with(user),
                Click.on(BTN_REGISTER),
                WaitUntil.the(LBL_RESULT, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}