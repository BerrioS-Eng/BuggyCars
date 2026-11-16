@Votacion
Feature: Votar y comentar un auto
  Como usuario autenticado
  Quiero dejar mi voto y un comentario sobre un auto
  Para compartir mi opinión con la comunidad

  @HappyPath
  Scenario: Votar un auto dejando un comentario
    Given que Sebastián inició sesión con una cuenta nueva
    And selecciona el modelo más popular
    When vota dejando un comentario
    Then debería ver el mensaje "Thank you for your vote!"
    And su comentario debería aparecer registrado con su nombre como autor

  @Limite
  Scenario: Votar sin escribir comentario
    Given que Sebastián inició sesión con una cuenta nueva
    And selecciona el modelo más popular
    When vota sin escribir ningún comentario
    Then el número de votos del modelo debería aumentar en uno

  @Alterno
  Scenario: Intentar votar sin haber iniciado sesión
    Given que Sebastián es un visitante sin sesión iniciada
    When selecciona el modelo más popular
    Then debería ver el mensaje "You need to be logged in to vote."

  @Limite
  Scenario: Intentar votar dos veces el mismo auto
    Given que Sebastián ya votó por el modelo más popular
    When vuelve a ingresar al mismo modelo
    Then no debería poder votar nuevamente
