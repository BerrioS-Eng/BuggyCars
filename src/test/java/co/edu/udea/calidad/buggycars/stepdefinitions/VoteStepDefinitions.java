package co.edu.udea.calidad.buggycars.stepdefinitions;

import co.edu.udea.calidad.buggycars.models.User;
import co.edu.udea.calidad.buggycars.questions.CommentRegistered;
import co.edu.udea.calidad.buggycars.questions.VoteIsAvailable;
import co.edu.udea.calidad.buggycars.questions.VotesCount;
import co.edu.udea.calidad.buggycars.tasks.LogIn;
import co.edu.udea.calidad.buggycars.tasks.OpenTheBrowser;
import co.edu.udea.calidad.buggycars.tasks.SelectMostPopularModel;
import co.edu.udea.calidad.buggycars.tasks.Vote;
import co.edu.udea.calidad.buggycars.utils.CommentFactory;
import co.edu.udea.calidad.buggycars.utils.TestAccounts;
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
        // TEMPORAL: usa una cuenta creada a mano. Cuando exista el registro,
        // cambiar por UserFactory.aNewUserNamed(actorName) + la tarea de registro.
        User user = TestAccounts.preexistingAccount();
        Actor actor = theActorCalled(actorName);
        actor.remember(USER, user);
        actor.attemptsTo(
                OpenTheBrowser.onTheHomePage(),
                LogIn.withCredentials(user));
    }

    @Given("que {word} es un visitante sin sesion iniciada")
    public void esUnVisitanteSinSesionIniciada(String actorName) {
        theActorCalled(actorName).attemptsTo(OpenTheBrowser.onTheHomePage());
    }

    @Given("que {word} ya voto por el modelo mas popular")
    public void yaVotoPorElModeloMasPopular(String actorName) {
        inicioSesionConUnaCuentaNueva(actorName);
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(SelectMostPopularModel.fromTheHomePage());
        // Garantiza la precondición sin depender de otro escenario (FIRST):
        // si la cuenta todavía no ha votado, vota ahora.
        if (Boolean.TRUE.equals(VoteIsAvailable.forTheModel().answeredBy(actor))) {
            actor.attemptsTo(Vote.withoutComment());
        }
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

    @Then("su comentario deberia aparecer registrado con su nombre como autor")
    public void suComentarioDeberiaAparecerRegistrado() {
        Actor actor = theActorInTheSpotlight();
        String comment = actor.recall(COMMENT);
        User user = actor.recall(USER);
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
}
