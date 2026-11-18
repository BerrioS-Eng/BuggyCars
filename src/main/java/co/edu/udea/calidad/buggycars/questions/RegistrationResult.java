package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;

import static co.edu.udea.calidad.buggycars.userinterfaces.RegistrationPage.LBL_RESULT;

/**
 * Texto del mensaje de resultado del registro, sea de éxito o de error.
 *
 * <p>Devuelve el texto y no un booleano: así el step decide cómo compararlo
 * (igual, contiene, etc.) y, si falla, el reporte muestra el texto real.</p>
 */

public class RegistrationResult implements Question<String> {

    public static RegistrationResult message() {
        return new RegistrationResult();
    }

    @Override
    @Subject("the registration result message")
    public String answeredBy(Actor actor) {
        return Text.of(LBL_RESULT).answeredBy(actor).trim();
    }


}