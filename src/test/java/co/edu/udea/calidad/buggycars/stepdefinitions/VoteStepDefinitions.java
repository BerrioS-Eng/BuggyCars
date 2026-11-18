package co.edu.udea.calidad.buggycars.stepdefinitions;

import co.edu.udea.calidad.buggycars.interactions.ReloadThePage;
import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.questions.CommentPublished;
import co.edu.udea.calidad.buggycars.questions.CommentRegistered;
import co.edu.udea.calidad.buggycars.questions.VoteIsAvailable;
import co.edu.udea.calidad.buggycars.questions.VotesCount;
import co.edu.udea.calidad.buggycars.tasks.LogIn;
import co.edu.udea.calidad.buggycars.tasks.OpenTheBrowser;
import co.edu.udea.calidad.buggycars.tasks.Register;
import co.edu.udea.calidad.buggycars.tasks.SelectMostPopularModel;
import co.edu.udea.calidad.buggycars.tasks.Vote;
import co.edu.udea.calidad.buggycars.utils.CommentFactory;
import co.edu.udea.calidad.buggycars.utils.UserFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.calidad.buggycars.utils.MemoryKeys.COMMENT;
import static co.edu.udea.calidad.buggycars.utils.MemoryKeys.USER;
import static co.edu.udea.calidad.buggycars.utils.MemoryKeys.VOTES_BEFORE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Pasos del feature votacion/comentar_auto.feature.
 */
public class VoteStepDefinitions {

    // ---------- Given: preparar (Arrange) ----------

    @Given("que {word} inicio sesion con una cuenta nueva")
    public void inicioSesionConUnaCuentaNueva(String actorName) {
        // Cuenta nueva en cada ejecución: Buggy solo deja votar una vez por
        // cuenta, así el escenario se puede repetir (la R de FIRST).
        User user = UserFactory.aNewUserNamed(actorName);
        Actor actor = theActorCalled(actorName);
        actor.remember(USER, user);
        actor.attemptsTo(Register.with(user));
        logInWithTheRememberedUser(actor);
    }

    /** Paso del E2E: el usuario ya se registró en el paso anterior. */
    @Given("inicia sesion con la cuenta recien creada")
    public void iniciaSesionConLaCuentaRecienCreada() {
        logInWithTheRememberedUser(theActorInTheSpotlight());
    }

    @Given("que {word} es un visitante sin sesion iniciada")
    public void esUnVisitanteSinSesionIniciada(String actorName) {
        theActorCalled(actorName).attemptsTo(OpenTheBrowser.onTheHomePage());
    }

    @Given("que {word} ya voto por el modelo mas popular")
    public void yaVotoPorElModeloMasPopular(String actorName) {
        // Prepara la precondición dentro del mismo escenario, sin depender de
        // que otro escenario haya votado antes (la I de FIRST).
        inicioSesionConUnaCuentaNueva(actorName);
        theActorInTheSpotlight().attemptsTo(
                SelectMostPopularModel.fromTheHomePage(),
                Vote.withoutComment());
    }

    // ---------- When: ejecutar (Act) ----------

    @When("selecciona el modelo mas popular")
    public void seleccionaElModeloMasPopular() {
        theActorInTheSpotlight().attemptsTo(SelectMostPopularModel.fromTheHomePage());
    }

    @When("vota dejando un comentario")
    public void votaDejandoUnComentario() {
        String comment = CommentFactory.aUniqueComment();
        Actor actor = theActorInTheSpotlight();
        actor.remember(COMMENT, comment);
        actor.attemptsTo(Vote.withComment(comment));
    }

    @When("vota sin escribir ningun comentario")
    public void votaSinEscribirNingunComentario() {
        Actor actor = theActorInTheSpotlight();
        actor.remember(VOTES_BEFORE, VotesCount.ofTheModel().answeredBy(actor));
        actor.attemptsTo(Vote.withoutComment());
    }

    @When("vuelve a ingresar al mismo modelo")
    public void vuelveAIngresarAlMismoModelo() {
        theActorInTheSpotlight().attemptsTo(
                OpenTheBrowser.onTheHomePage(),
                SelectMostPopularModel.fromTheHomePage());
    }

    // ---------- Then: verificar (Assert) ----------

    @Then("su comentario deberia aparecer en la lista de comentarios")
    public void suComentarioDeberiaAparecerEnLaLista() {
        Actor actor = theActorInTheSpotlight();
        String comment = actor.recall(COMMENT);
        // El sitio no actualiza la tabla al votar: hay que recargar para verla.
        actor.attemptsTo(ReloadThePage.ofTheModel());
        actor.should(seeThat(CommentPublished.withText(comment)));
    }

    @Then("su comentario deberia mostrar su nombre como autor")
    public void suComentarioDeberiaMostrarSuNombreComoAutor() {
        Actor actor = theActorInTheSpotlight();
        String comment = actor.recall(COMMENT);
        User user = actor.recall(USER);
        actor.attemptsTo(ReloadThePage.ofTheModel());
        actor.should(seeThat(CommentRegistered.withTextAndAuthor(comment, user.getFirstName())));
    }

    @Then("el numero de votos del modelo deberia aumentar en uno")
    public void elNumeroDeVotosDeberiaAumentarEnUno() {
        Actor actor = theActorInTheSpotlight();
        int votesBefore = actor.recall(VOTES_BEFORE);
        actor.should(seeThat(VotesCount.ofTheModel(), equalTo(votesBefore + 1)));
    }

    @Then("no deberia poder votar nuevamente")
    public void noDeberiaPoderVotarNuevamente() {
        theActorInTheSpotlight().should(seeThat(VoteIsAvailable.forTheModel(), is(false)));
    }

    // ---------- Apoyo ----------

    /**
     * Va a la página principal e inicia sesión con el usuario guardado en la
     * memoria del actor. Se abre la página principal primero porque en la
     * página de registro hay dos campos con name="password" (el del encabezado
     * y el del formulario).
     */
    private void logInWithTheRememberedUser(Actor actor) {
        User user = actor.recall(USER);
        actor.attemptsTo(
                OpenTheBrowser.onTheHomePage(),
                LogIn.withCredentials(user));
    }
}
