package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.BTN_REGISTER;

/**
 * Indica si el botón "Register" está habilitado. El sitio lo deshabilita
 * mientras el formulario no es válido, por ejemplo cuando las contraseñas
 * no coinciden.
 */

public class TheRegisterButton implements Question<Boolean> {

    public static TheRegisterButton isEnabled() {
        return new TheRegisterButton();
    }

    @Override
    @Subject("the register button is enabled")
    public Boolean answeredBy(Actor actor) {
        return BTN_REGISTER.resolveFor(actor).isEnabled();
    }
}