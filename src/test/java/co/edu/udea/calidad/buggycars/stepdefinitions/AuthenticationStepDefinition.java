package co.edu.udea.calidad.buggycars.stepdefinitions;

import co.edu.udea.calidad.buggycars.interactions.FillLoginForm;
import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.tasks.LogIn;
import co.edu.udea.calidad.buggycars.tasks.OpenTheBrowser;
import co.edu.udea.calidad.buggycars.utils.MemoryKeys;
import co.edu.udea.calidad.buggycars.utils.TestAccounts;
import co.edu.udea.calidad.buggycars.utils.UserFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;

import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.LBL_ERROR_MESSAGE;
import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.LBL_GREETING;
import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.LNK_LOGOUT;
import static co.edu.udea.calidad.buggycars.userinterfaces.LoginForm.TXT_LOGIN;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.questions.Text.of;
import static net.serenitybdd.screenplay.waits.WaitUntil.the;
import static org.hamcrest.Matchers.containsString;

/**
 * Pasos del feature autenticacion/inicio_sesion.feature.
 *
 * <p>El escenario feliz usa la tarea {@link LogIn}, que ya espera al saludo.
 * Los escenarios alternos no pueden usarla: como el saludo nunca aparece, la
 * espera terminaría en timeout. Por eso diligencian el formulario con
 * {@link FillLoginForm} y esperan el mensaje de error.</p>
 */
public class AuthenticationStepDefinition {

    private static final String ACTOR_NAME = "Jhon";
    private static final String WRONG_PASSWORD_SUFFIX = "_incorrecta";
    private static final int MAX_WAIT_SECONDS = 10;

    // ---------- Given: preparar (Arrange) ----------

    @Given("que Jhon tiene una cuenta registrada")
    public void queJhonTieneUnaCuentaRegistrada() {
        // TEMPORAL: mientras no exista el registro automatizado se usa una
        // cuenta creada a mano (variables BUGGY_* o cuenta.local.properties).
        User user = TestAccounts.preexistingAccount();
        Actor actor = theActorCalled(ACTOR_NAME);
        actor.remember(MemoryKeys.USER, user);
        actor.attemptsTo(OpenTheBrowser.onTheHomePage());
        ensureLoggedOut(actor);
    }

    // ---------- When: ejecutar (Act) ----------

    @When("inicia sesion con sus credenciales")
    public void iniciaSesionConSusCredenciales() {
        theActorInTheSpotlight().attemptsTo(LogIn.withCredentials(recallUser()));
    }

    @When("inicia sesion con una contrasena erronea")
    public void iniciaSesionConUnaContrasenaErronea() {
        User registeredUser = recallUser();
        attemptLoginWith(registeredUser.withPassword(
                registeredUser.getPassword() + WRONG_PASSWORD_SUFFIX));
    }

    @When("inicia sesion con un usuario inexistente")
    public void iniciaSesionConUnUsuarioInexistente() {
        User registeredUser = recallUser();
        User unknownUser = new User(
                UserFactory.uniqueUsername("inexistente"),
                registeredUser.getFirstName(),
                registeredUser.getLastName(),
                registeredUser.getPassword());
        attemptLoginWith(unknownUser);
    }

    // ---------- Then: verificar (Assert) ----------

    @Then("deberia ver el saludo con su nombre")
    public void deberiaVerElSaludoConSuNombre() {
        String expectedGreeting = "Hi, " + recallUser().getFirstName();
        theActorInTheSpotlight().should(seeThat(
                "el saludo con el nombre del usuario",
                of(LBL_GREETING),
                containsString(expectedGreeting)));
    }

    // ---------- Apoyo ----------

    /** Diligencia el formulario con credenciales inválidas y espera el error. */
    private void attemptLoginWith(User user) {
        theActorInTheSpotlight().attemptsTo(
                FillLoginForm.with(user),
                the(LBL_ERROR_MESSAGE, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
    }

    /**
     * Cierra la sesión que pudo quedar de otro escenario. El proyecto reutiliza
     * el navegador ({@code use.unique.browser = false}) y el token se guarda en
     * localStorage: sin esto los escenarios de error no verían el formulario.
     */
    private void ensureLoggedOut(Actor actor) {
        if (LNK_LOGOUT.resolveAllFor(actor).stream().anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(
                    Click.on(LNK_LOGOUT),
                    the(TXT_LOGIN, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds());
        }
    }

    /** Recupera la cuenta que el Background dejó en la memoria del actor. */
    private User recallUser() {
        return theActorInTheSpotlight().recall(MemoryKeys.USER);
    }
}
