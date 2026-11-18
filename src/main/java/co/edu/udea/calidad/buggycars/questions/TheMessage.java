package co.edu.udea.calidad.buggycars.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Indica si en la página hay un mensaje visible con exactamente el texto
 * esperado. Es genérica: sirve para los mensajes de registro, de inicio de
 * sesión y de votación.
 */
public class TheMessage implements Question<Boolean> {

    /** Elemento más interno cuyo texto completo es igual al mensaje. */
    private static final Target MESSAGE = Target.the("message '{0}'")
            .locatedBy("//*[normalize-space(.)='{0}' and not(*[normalize-space(.)='{0}'])]");

    private final String expectedText;

    public TheMessage(String expectedText) {
        this.expectedText = expectedText;
    }

    public static TheMessage displayed(String expectedText) {
        return new TheMessage(expectedText);
    }

    @Override
    @Subject("the message '#expectedText' is displayed")
    public Boolean answeredBy(Actor actor) {
        return MESSAGE.of(expectedText).resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible);
    }
}
