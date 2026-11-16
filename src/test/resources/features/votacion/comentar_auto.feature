@Votacion
Feature: Votar y comentar un auto
  Como usuario autenticado
  Quiero dejar mi voto y un comentario sobre un auto
  Para compartir mi opinion con la comunidad

  @HappyPath
  Scenario: Votar un auto dejando un comentario
    Given que Sebastian inicio sesion con una cuenta nueva
    And selecciona el modelo mas popular
    When vota dejando un comentario
    Then deberia ver el mensaje "Thank you for your vote!"
    And su comentario deberia aparecer registrado con su nombre como autor

  @Limite
  Scenario: Votar sin escribir comentario
    Given que Sebastian inicio sesion con una cuenta nueva
    And selecciona el modelo mas popular
    When vota sin escribir ningun comentario
    Then el numero de votos del modelo deberia aumentar en uno

  @Alterno
  Scenario: Intentar votar sin haber iniciado sesion
    Given que Sebastian es un visitante sin sesion iniciada
    When selecciona el modelo mas popular
    Then deberia ver el mensaje "You need to be logged in to vote."

  @Limite
  Scenario: Intentar votar dos veces el mismo auto
    Given que Sebastian ya voto por el modelo mas popular
    When vuelve a ingresar al mismo modelo
    Then no deberia poder votar nuevamente
