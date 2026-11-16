package co.edu.udea.calidad.buggycars.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Configuración común a todos los escenarios (hooks de Cucumber).
 *
 * <p>Antes de cada escenario prepara el "escenario" de Screenplay con un
 * {@link OnlineCast}: cada actor que se llame con
 * {@code OnStage.theActorCalled("Jhon")} recibe automáticamente la habilidad
 * de navegar en la web (BrowseTheWeb) con el navegador configurado en
 * {@code serenity.conf}.</p>
 *
 * <p>Uso desde los StepDefinitions de cada feature:</p>
 * <pre>
 *   OnStage.theActorCalled("Jhon").attemptsTo(OpenTheBrowser.onTheHomePage());
 *   OnStage.theActorInTheSpotlight().attemptsTo(...);   // el último actor llamado
 * </pre>
 *
 * <p>Para compartir datos entre pasos (por ejemplo, el usuario creado con
 * {@code UserFactory}), use la memoria del actor:
 * {@code actor.remember("user", user)} y {@code actor.recall("user")}.</p>
 */
public class StepDefinition {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
