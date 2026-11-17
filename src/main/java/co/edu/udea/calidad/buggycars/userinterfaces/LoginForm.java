package co.edu.udea.calidad.buggycars.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Elementos del formulario de inicio de sesión, ubicado en el encabezado
 * de todas las páginas.
 */
public final class LoginForm {

    public static final Target TXT_LOGIN = Target.the("login field")
            .located(By.name("login"));

    public static final Target TXT_PASSWORD = Target.the("password field")
            .located(By.name("password"));

    public static final Target BTN_LOGIN = Target.the("login button")
            .located(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

    /** Saludo "Hi, nombre" que aparece cuando la sesión está iniciada. */
    public static final Target LBL_GREETING = Target.the("greeting")
            .located(By.xpath("//span[contains(@class,'nav-link') and starts-with(normalize-space(),'Hi,')]"));

    private LoginForm() {
    }
}
