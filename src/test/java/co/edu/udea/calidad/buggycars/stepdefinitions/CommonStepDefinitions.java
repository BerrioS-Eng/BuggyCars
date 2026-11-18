package co.edu.udea.calidad.buggycars.stepdefinitions;

import co.edu.udea.calidad.buggycars.questions.TheMessage;
import io.cucumber.java.en.Then;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

/**
 * Pasos que se repiten en varios features (registro, inicio de sesión y
 * votación). Cucumber no permite definir dos veces el mismo texto de paso,
 * por eso viven aquí una sola vez.
 */
public class CommonStepDefinitions {

    @Then("deberia ver el mensaje {string}")
    public void deberiaVerElMensaje(String expectedMessage) {
        theActorInTheSpotlight().should(seeThat(TheMessage.displayed(expectedMessage)));
    }
}
