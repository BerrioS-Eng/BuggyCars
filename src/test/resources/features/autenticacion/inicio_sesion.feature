
@Autenticacion
Feature: Inicio de sesión
  Como usuario registrado
  Quiero autenticarme
  Para acceder a las funciones de votación

  Background:
    Given que Jhon tiene una cuenta registrada

  @HappyPath
  Scenario: Inicio de sesión exitoso
    When inicia sesión con sus credenciales
    Then debería ver el saludo con su nombre

  @Alterno
  Scenario Outline: Inicio de sesión fallido
    When inicia sesión con <tipo de credencia>
    Then debería ver el mensaje "Invalid username/password"

    Examples:
      | tipo de credencial      |
      | una contraseña errónea  |
      | un usuario inexistente  |
