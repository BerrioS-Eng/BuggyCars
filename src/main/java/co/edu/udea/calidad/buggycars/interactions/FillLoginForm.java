package co.edu.udea.calidad.buggycars.interactions;

import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.BTN_LOGIN;
import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.TXT_LOGIN;
import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.TXT_PASSWORD;

/**
 * Diligencia el formulario de inicio de sesión del encabezado y hace clic
 * en "Login". No verifica el resultado: así sirve tanto para credenciales
 * válidas como para inválidas.
 */
public class FillLoginForm implements Interaction {

    private final User user;

    public FillLoginForm(User user) {
        this.user = user;
    }

    public static FillLoginForm with(User user) {
        return Tasks.instrumented(FillLoginForm.class, user);
    }

    @Override
    @Step("{0} fills the login form as #user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(user.getUsername()).into(TXT_LOGIN));
        Wait.aLittleBitTime();
        // La contraseña se escribe directamente sobre el elemento, sin la acción Enter,
        // para que el reporte de Serenity no la muestre en texto plano.
        TXT_PASSWORD.resolveFor(actor).type(user.getPassword());
        Wait.aLittleBitTime();
        actor.attemptsTo(Click.on(BTN_LOGIN));
    }
}
