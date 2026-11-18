@E2E
Feature: Flujo completo de un nuevo usuario
  Como nuevo usuario
  Quiero registrarme, autenticarme y comentar un auto
  Para validar el recorrido principal de la plataforma

  @HappyPath
  Scenario: Nuevo usuario se registra, inicia sesion y comenta un auto
    Given que Sebastian se registra con datos validos
    And inicia sesion con la cuenta recien creada
    When selecciona el modelo mas popular
    And vota dejando un comentario
    Then deberia ver el mensaje "Thank you for your vote!"
    And su comentario deberia aparecer en la lista de comentarios
