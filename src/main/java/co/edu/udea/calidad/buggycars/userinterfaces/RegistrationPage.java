package co.edu.udea.calidad.buggycars.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Elementos del formulario de registro (/register).
 * <p>Los campos se ubican por id y no por name: el formulario de login del
 * encabezado sigue visible en esta página y también tiene un campo
 * name="password".</p>
 */

public final class RegistrationPage {

    public static final Target TXT_USERNAME = Target.the("login field")
            .located(By.id("username"));

    public static final Target TXT_FIRST_NAME = Target.the("first name field")
            .located(By.id("firstName"));

    public static final Target TXT_LAST_NAME = Target.the("last name field")
            .located(By.id("lastName"));

    public static final Target TXT_PASSWORD = Target.the("password field")
            .located(By.id("password"));

    public static final Target TXT_CONFIRM_PASSWORD = Target.the("confirm password field")
            .located(By.id("confirmPassword"));

    public static final Target BTN_REGISTER = Target.the("register button")
            .located(By.xpath("//form//button[@type='submit' and normalize-space()='Register']"));

    /** Mensaje de resultado: éxito (alert-success) o error (alert-danger). */
    public static final Target LBL_RESULT = Target.the("registration result")
            .located(By.cssSelector("div.result.alert"));

    private RegistrationPage() {
    }
}