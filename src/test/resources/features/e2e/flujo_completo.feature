@E2E
Feature: Flujo completo de un nuevo usuario
  Como nuevo usuario
  Quiero registrarme, autenticarme y comentar un auto
  Para validar el recorrido principal de la plataforma

  @HappyPath
  Scenario: Nuevo usuario se registra, inicia sesión y comenta un auto
    Given que Sebastián se registra con datos válidos
    And inicia sesión con la cuenta recién creada
    When selecciona el modelo más popular
    And vota dejando un comentario
    Then su comentario debería aparecer registrado con su nombre como autor
