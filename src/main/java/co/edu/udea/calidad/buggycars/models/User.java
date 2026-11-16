package co.edu.udea.calidad.buggycars.models;

/**
 * Datos de un usuario de Buggy Cars Rating. Corresponde a los campos del
 * formulario de registro: Login, First Name, Last Name, Password y
 * Confirm Password.
 *
 * <p>Es inmutable: para crear usuarios use {@code UserFactory}.</p>
 */
public final class User {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;

    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Devuelve una copia del usuario con otra contraseña. Sirve para los
     * escenarios de contraseña inválida o de credenciales erróneas.
     *
     * @param newPassword contraseña que tendrá la copia
     * @return un usuario igual a este, pero con la contraseña indicada
     */
    public User withPassword(String newPassword) {
        return new User(username, firstName, lastName, newPassword);
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', firstName='" + firstName
                + "', lastName='" + lastName + "'}";
    }
}
