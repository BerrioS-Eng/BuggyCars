
@Autenticacion
Feature: Inicio de sesion
  Como usuario registrado
  Quiero autenticarme
  Para acceder a las funciones de votacion

  Background:
    Given que Jhon tiene una cuenta registrada

  @HappyPath
  Scenario: Inicio de sesion exitoso
    When inicia sesion con sus credenciales
    Then deberia ver el saludo con su nombre

  @Alterno
  Scenario Outline: Inicio de sesion fallido
    When inicia sesion con <tipo de credencial>
    Then deberia ver el mensaje "Invalid username/password"

    Examples:
      | tipo de credencial      |
      | una contrasena erronea  |
      | un usuario inexistente  |
