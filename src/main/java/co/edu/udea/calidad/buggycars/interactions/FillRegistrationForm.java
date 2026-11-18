package co.edu.udea.calidad.buggycars.interactions;

import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.utils.Wait;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;

import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_CONFIRM_PASSWORD;
import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_FIRST_NAME;
import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_LAST_NAME;
import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_PASSWORD;
import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.TXT_USERNAME;

/**
 * Diligencia el formulario de registro, sin hacer clic en "Register" y sin
 * verificar el resultado.
 *
 * <p>No hace clic porque, cuando las contraseñas no coinciden, el botón queda
 * deshabilitado: el escenario solo necesita llenar el formulario y revisar el
 * estado del botón. El envío lo hace la tarea {@code Register}.</p>
 *
 * <p>La confirmación de contraseña es un parámetro y no un campo de
 * {@link User}, porque es un detalle del formulario y no un dato del usuario.</p>
 */

public class FillRegistrationForm implements Interaction {

    private final User user;
    private final String confirmationPassword;

    public FillRegistrationForm(User user, String confirmationPassword) {
        this.user = user;
        this.confirmationPassword = confirmationPassword;
    }

    /**
     * Llena el formulario confirmando la misma contraseña del usuario.
     */
    public static FillRegistrationForm with(User user) {
        return Tasks.instrumented(FillRegistrationForm.class, user, user.getPassword());
    }

    /**
     * Llena el formulario con una confirmación de contraseña distinta.
     */
    public static FillRegistrationForm withConfirmation(User user, String confirmationPassword) {
        return Tasks.instrumented(FillRegistrationForm.class, user, confirmationPassword);
    }

    @Override
    @Step("{0} fills the registration form as #user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(user.getUsername()).into(TXT_USERNAME),
                Enter.theValue(user.getFirstName()).into(TXT_FIRST_NAME),
                Enter.theValue(user.getLastName()).into(TXT_LAST_NAME));
        Wait.aLittleBitTime();
        // Las contraseñas se escriben directamente sobre el elemento, sin Enter,
        // para que el reporte de Serenity no las muestre en texto plano.
        TXT_PASSWORD.resolveFor(actor).type(user.getPassword());
        TXT_CONFIRM_PASSWORD.resolveFor(actor).type(confirmationPassword);
        Wait.aLittleBitTime();
    }
}