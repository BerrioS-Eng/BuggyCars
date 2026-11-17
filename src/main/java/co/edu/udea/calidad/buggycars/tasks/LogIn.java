package co.edu.udea.calidad.buggycars.tasks;

import co.edu.udea.calidad.buggycars.interactions.FillLoginForm;
import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.LBL_GREETING;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Inicia sesión con credenciales válidas y espera a que aparezca el saludo
 * "Hi, nombre", que confirma que la sesión quedó iniciada.
 */
public class LogIn implements Task {

    private static final int MAX_WAIT_SECONDS = 10;

    private final User user;

    public LogIn(User user) {
        this.user = user;
    }

    public static LogIn withCredentials(User user) {
        return Tasks.instrumented(LogIn.class, user);
    }

    @Override
    @Step("{0} logs in as #user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                FillLoginForm.with(user),
                WaitUntil.the(LBL_GREETING, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        Wait.aLittleBitTime();
    }
}
