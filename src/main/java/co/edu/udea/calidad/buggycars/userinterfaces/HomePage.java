package co.edu.udea.calidad.buggycars.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Elementos de la página principal de Buggy Cars Rating.
 */
public final class HomePage {

    /** Enlace (imagen) de la tarjeta "Popular Model". */
    public static final Target POPULAR_MODEL_LINK = Target.the("popular model link")
            .located(By.xpath("//h2[normalize-space()='Popular Model']/following-sibling::a"));

    private HomePage() {
    }
}
