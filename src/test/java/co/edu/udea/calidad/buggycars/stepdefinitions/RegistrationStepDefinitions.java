package co.edu.udea.calidad.buggycars.stepdefinitions;

import co.edu.udea.calidad.buggycars.interactions.FillRegistrationForm;
import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.questions.RegistrationResult;
import co.edu.udea.calidad.buggycars.questions.TheRegisterButton;
import co.edu.udea.calidad.buggycars.tasks.GoToRegistration;
import co.edu.udea.calidad.buggycars.tasks.Register;
import co.edu.udea.calidad.buggycars.utils.MemoryKeys;
import co.edu.udea.calidad.buggycars.utils.UserFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * Pasos del feature registro/registro_usuario.feature y el paso de registro
 * del feature e2e/flujo_completo.feature.
 *
 * <p>El usuario registrado queda en la memoria del actor con la clave
 * {@link MemoryKeys#USER}: los pasos de inicio de sesión lo recuperan de ahí.</p>
 */
public class RegistrationStepDefinitions {

    private static final String DIFFERENT_CONFIRMATION_SUFFIX = "_distinta";

    // ---------- Given: preparar (Arrange) ----------

    @Given("que {word} esta en la pagina de registro")
    public void estaEnLaPaginaDeRegistro(String actorName) {
        theActorCalled(actorName).attemptsTo(GoToRegistration.page());
    }

    @Given("que existe una cuenta registrada previamente")
    public void existeUnaCuentaRegistradaPreviamente() {
        registerANewUser(theActorInTheSpotlight());
    }

    /** Paso del E2E: crea el actor y registra una cuenta nueva. */
    @Given("que {word} se registra con datos validos")
    public void queSeRegistraConDatosValidos(String actorName) {
        registerANewUser(theActorCalled(actorName));
    }

    // ---------- When: ejecutar (Act) ----------

    @When("se registra con datos validos")
    public void seRegistraConDatosValidos() {
        registerANewUser(theActorInTheSpotlight());
    }

    @When("intenta registrarse con el mismo nombre de usuario")
    public void intentaRegistrarseConElMismoNombreDeUsuario() {
        Actor actor = theActorInTheSpotlight();
        User alreadyRegistered = actor.recall(MemoryKeys.USER);
        actor.attemptsTo(Register.with(alreadyRegistered));
    }

    @When("intenta registrarse con la contrasena {string}")
    public void intentaRegistrarseConLaContraseña(String password) {
        Actor actor = theActorInTheSpotlight();
        User user = UserFactory.aNewUserNamed(actor.getName()).withPassword(password);
        actor.attemptsTo(Register.with(user));
    }

    @When("diligencia el formulario con una confirmacion de contrasena distinta")
    public void diligenciaElFormularioConUnaConfirmacionDistinta() {
        Actor actor = theActorInTheSpotlight();
        User user = UserFactory.aNewUserNamed(actor.getName());
        actor.attemptsTo(FillRegistrationForm.withConfirmation(
                user, user.getPassword() + DIFFERENT_CONFIRMATION_SUFFIX));
    }

    // ---------- Then: verificar (Assert) ----------

    @Then("deberia ver un error que contenga {string}")
    public void deberiaVerUnErrorQueContenga(String expectedFragment) {
        theActorInTheSpotlight().should(seeThat(
                RegistrationResult.message(), containsString(expectedFragment)));
    }

    @Then("el boton de registro deberia estar deshabilitado")
    public void elBotonDeRegistroDeberiaEstarDeshabilitado() {
        theActorInTheSpotlight().should(seeThat(TheRegisterButton.isEnabled(), is(false)));
    }

    // ----------

    /** Registra un usuario nuevo y lo deja en la memoria del actor. */
    private void registerANewUser(Actor actor) {
        User user = UserFactory.aNewUserNamed(actor.getName());
        actor.remember(MemoryKeys.USER, user);
        actor.attemptsTo(Register.with(user));
    }
}