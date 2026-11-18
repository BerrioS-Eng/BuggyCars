
@Registro
Feature: Registro de usuarios en Buggy Cars Rating
  Como visitante del sitio
  Quiero crear una cuenta
  Para poder seleccionar y comentar un auto.

  Background:
    Given que Jhon esta en la pagina de registro

  @HappyPath
  Scenario: Registro exitoso con datos validos
    When se registra con datos validos
    Then deberia ver el mensaje "Registration is successful"

  @Alterno
  Scenario: Registro con un nombre de usuario ya existente
    Given que existe una cuenta registrada previamente
    When intenta registrarse con el mismo nombre de usuario
    Then deberia ver el mensaje "UsernameExistsException: User already exists"

  @Limite
  Scenario Outline: Contrasena que no cumple la politica de seguridad
    When intenta registrarse con la contrasena "<contrasena>"
    Then deberia ver un error que contenga "<mensaje>"

    Examples:
      | contrasena | mensaje                                 |
      | Ab1!       | minimum field size of 6                 |
      | Ab1!xy     | Password not long enough                |
      | password1! | Password must have uppercase characters |
      | Password12 | Password must have symbol characters    |

  @Alterno
  Scenario: Confirmacion de contrasena que no coincide
      When diligencia el formulario con una confirmacion de contrasena distinta
      Then el boton de registro deberia estar deshabilitado