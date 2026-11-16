
@Registro
Feature: Registro de usuarios en Buggy Cars Rating
  Como visitante del sitio
  Quiero crear una cuenta
  Para poder seleccionar y comentar un auto.

  Background:
    Given que Jhon está en la página de registro

  @HappyPath
  Scenario: Registro exitoso con datos válidos
    When se registra con datos válidos
    Then debería ver el mensaje "Registration is successfull"

  @Alterno
  Scenario: Registro con un nombre de usuario ya existente
    Given que existe una cuenta registrada previamente
    When intenta registrarse con el mismo nombre de usuario
    Then deberia ver el mensaje "UsernameExistsException: User already exists"

  @Limite
  Scenario Outline: Contraseña que no cumple la política de seguridad
    When intenta registrarse con la contraseña "<contrasena>"
    Then debería ver un error que contenga "<mensaje>"

    Examples:

      | contrasena | mensaje                                  |
      | Ab1!       | Password not long enough                 |
      | password1! | Password must have uppercase characters  |
      | Password12 | Password must have symbol characters     |

  @Alterno
  Scenario: Confirmación de contraseña que no coincide
      When diligencia el formulario con una confirmación de contraseña distinta
      Then el boton de registro debería estar deshabilitado