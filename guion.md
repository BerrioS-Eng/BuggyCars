# Guion de la exposición: taller de automatización BuggyCars

Calidad de Software 2026-2 · Universidad de Antioquia · Profesor Robinson Coronado García

Equipo: Sebastián Berrío (registro), Brayan Jaraba (inicio de sesión) y Jonathan (base del proyecto y votación).

Este es el texto que dice cada integrante, diapositiva por diapositiva, en la presentación `Presentacion_Taller_Automatizacion_BuggyCars.pptx`. Lo que está entre corchetes son indicaciones: no se lee. La exposición vale **55 de los 100 puntos** del taller.

## Reparto y tiempos

Duración total estimada: 15 a 17 minutos, más las preguntas.

| Diapositivas | Tema | Expone | Tiempo |
|---|---|---|---|
| 1 | Portada y equipo | Jonathan abre; cada uno se presenta | 1 min |
| 2–3 | El reto y las herramientas | Sebastián | 2,5 min |
| 4–6 | Screenplay, estructura y recorrido de una ejecución | Brayan | 3 min |
| 7 | Escenarios por feature | Sebastián, Brayan y Jonathan | 1,5 min |
| 8–10 | Gherkin, código y buenas prácticas | Jonathan | 3 min |
| 11–12 | Resultados y defectos encontrados | Jonathan | 2 min |
| 13 | Demostración en vivo y reporte | Jonathan ejecuta; los tres narran | 3 min |
| 14 | Aprendizajes y cierre | Los tres | 1 min |

## Lista de verificación antes de exponer

- [ ] Hacer `git pull` en la rama `build` para tener la última versión.
- [ ] Abrir una terminal en la carpeta `BuggyCars` y activar Java 17 (Gradle 7.6.4 no funciona con Java 21 ni con Java 25). Cambiar la ruta por la de su instalación:
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot"
```
- [ ] Ejecutar una vez todas las pruebas y copiar la carpeta `target\site\serenity` a otro lugar como **reporte de respaldo** (cada ejecución la reemplaza).
- [ ] Tener abiertos la presentación, la terminal y el reporte de respaldo.
- [ ] Revisar que haya internet: las pruebas corren contra https://buggy.justtestit.org.

## Guion

En las partes de Jonathan, las indicaciones *[Archivo: …]* dicen dónde está en el proyecto cada cosa que se menciona, por si el profesor pide verla. Las rutas parten de la carpeta `BuggyCars`, y `.../` abrevia `co/edu/udea/calidad/buggycars`.

### Diapositiva 1 · Portada · Jonathan y el equipo · 1 min

**Jonathan:**

Buenos días, profesor; buenos días a todos. Somos el equipo del numeral 7 del taller de automatización. Hoy les vamos a mostrar cómo automatizamos las pruebas de punta a punta del sitio Buggy Cars Rating, usando el patrón Screenplay con Serenity BDD, Cucumber y Java. Cada uno se va a presentar y les va a contar qué parte construyó.

**Sebastián:**

Soy Sebastián Berrío. Generé la base del proyecto con el scaffold y automaticé el registro de usuarios.

**Brayan:**

Soy Brayan Jaraba y automaticé el inicio de sesión.

**Jonathan:**

Y yo soy Jonathan. Preparé la base común del proyecto y automaticé la votación con comentarios. Le doy la palabra a Sebastián para que nos cuente el reto.

*[Cambiar a la diapositiva 2]*

### Diapositiva 2 · El reto · Sebastián · 1,5 min

**Sebastián:**

Buggy Cars Rating es un sitio que existe para practicar pruebas. En él, los usuarios registrados votan y comentan modelos de autos. Algo importante es que tiene errores puestos a propósito, y más adelante les vamos a mostrar que nuestras pruebas los encontraron.

El enunciado nos pidió una prueba de punta a punta, lo que se conoce como E2E. Es el recorrido que ven en pantalla: primero el usuario se registra; después inicia sesión con esa cuenta; luego elige un auto, en nuestro caso el modelo más popular; y por último vota, deja un comentario y validamos que ese comentario haya quedado registrado.

*[Cambiar a la diapositiva 3]*

### Diapositiva 3 · Herramientas · Sebastián · 1 min

**Sebastián:**

Para construirlo usamos estas herramientas. Java 17, que es el lenguaje que exige el taller; además, la versión de Gradle del proyecto no funciona con versiones más nuevas de Java. Serenity BDD 4.1.0, que nos da el patrón Screenplay, toma una captura en cada acción y genera el reporte. Cucumber con Gherkin, para escribir los escenarios en lenguaje natural. Selenium, que controla el navegador Chrome. Gradle, que compila, ejecuta las pruebas y genera el reporte. Y el arquetipo ScreenplayArchitecture, que es el plugin de Gradle que nos indicó la guía del taller: con él generamos toda la estructura inicial del proyecto. Brayan les va a explicar cómo funciona ese patrón.

*[Cambiar a la diapositiva 4]*

### Diapositiva 4 · Screenplay · Brayan · 1 min

**Brayan:**

Screenplay significa guion, y la mejor forma de entenderlo es como una obra de teatro. El archivo feature, escrito en Gherkin, es el guion. El actor es quien ejecuta la prueba, como lo haría un usuario real, y tiene una habilidad: sabe usar el navegador.

El actor hace cosas en dos niveles. Las Tasks dicen qué hace, en términos del negocio: registrarse, iniciar sesión, votar. Las Interactions dicen cómo lo hace: escribir en un campo, hacer clic en un botón. Los elementos de la página, como el campo de comentario o el botón Vote, son los Targets. Y lo que el actor comprueba al final son las Questions.

Esa separación entre el qué y el cómo es la que hace que el código sea legible y que se pueda reutilizar.

*[Cambiar a la diapositiva 5]*

### Diapositiva 5 · Estructura del proyecto · Brayan · 1 min

**Brayan:**

Así quedó organizado el proyecto. En la carpeta main está el código de la automatización, separado por las capas de Screenplay: tasks, interactions, userinterfaces, questions, y las carpetas de modelos y utilidades. En la carpeta test están el Runner, los StepDefinitions, que son el pegamento entre el lenguaje natural y Java, y los features. La dirección del sitio y la configuración del navegador están en el archivo serenity.conf, no escritas en el código.

*[Cambiar a la diapositiva 6]*

### Diapositiva 6 · Recorrido de una ejecución · Brayan · 1 min

**Brayan:**

Cuando ejecutamos las pruebas pasa lo siguiente. El Runner arranca Cucumber, y antes de cada escenario se crea un actor nuevo con su propio navegador. Cucumber lee el feature y busca el StepDefinition de cada frase. El StepDefinition le pide al actor que ejecute tareas con attemptsTo; las tareas usan interacciones, y las interacciones actúan sobre los Targets de la página. Al final, el actor verifica el resultado con una Question, usando should y seeThat, y Serenity guarda toda la evidencia en el reporte. Ahora cada uno les cuenta sus escenarios.

*[Cambiar a la diapositiva 7]*

### Diapositiva 7 · Escenarios por feature · Los tres · 1,5 min

**Jonathan:**

En total automatizamos dieciséis escenarios en cuatro features, con camino feliz, caminos alternos y casos límite.

**Sebastián:**

En el registro son siete: el registro exitoso, el intento con un usuario que ya existe, cuatro contraseñas que no cumplen la política de seguridad y la confirmación de contraseña distinta. Las cuatro contraseñas son un solo Scenario Outline: un escenario que se ejecuta una vez por cada fila de una tabla de datos.

**Brayan:**

En el inicio de sesión son tres: el inicio exitoso, con contraseña errónea y con un usuario que no existe.

**Jonathan:**

En la votación son cinco: votar con comentario, votar sin comentario, intentar votar sin sesión, intentar votar dos veces y un escenario marcado como defecto, que les explico más adelante. Y el E2E recorre todo: registro, inicio de sesión y voto con comentario, reutilizando los pasos de los tres features.

*[Archivos: votación en `src/test/resources/features/votacion/comentar_auto.feature` · E2E en `src/test/resources/features/e2e/flujo_completo.feature` · el único paso nuevo del E2E, «inicia sesion con la cuenta recien creada», está en `src/test/java/.../stepdefinitions/VoteStepDefinitions.java`]*

*[Cambiar a la diapositiva 8]*

### Diapositiva 8 · Gherkin · Jonathan · 1 min

**Jonathan:**

Este es uno de los escenarios de votación tal como está escrito. Cada palabra clave corresponde al patrón triple A. Given es preparar: el usuario inicia sesión con una cuenta nueva y entra al modelo más popular. When es ejecutar: vota dejando un comentario. Y Then es verificar: debe ver el mensaje de agradecimiento y su comentario debe aparecer en la lista. Lo escribimos sin tildes ni eñes, porque Cucumber puede fallar con esos caracteres.

*[Archivos: el escenario «Votar un auto dejando un comentario» está en `src/test/resources/features/votacion/comentar_auto.feature` · cada frase se conecta con su método en `src/test/java/.../stepdefinitions/VoteStepDefinitions.java` · la frase «deberia ver el mensaje» está en `src/test/java/.../stepdefinitions/CommonStepDefinitions.java`]*

*[Cambiar a la diapositiva 9]*

### Diapositiva 9 · Código · Jonathan · 1 min

**Jonathan:**

Aquí se ve la diferencia entre una tarea y una interacción. A la izquierda está la tarea Vote: decide lo que pasa en el negocio. Si hay comentario lo escribe, hace clic en el botón Vote y espera la confirmación. A la derecha está la interacción WriteComment, que solo escribe el texto en el campo; no sabe nada de votos. Como las clases tienen métodos estáticos con nombres claros, en el paso el código se lee casi como una frase: el actor intenta votar con un comentario.

*[Archivos: tarea `src/main/java/.../tasks/Vote.java` (método `performAs`) · interacción `src/main/java/.../interactions/WriteComment.java` · la frase `actor.attemptsTo(Vote.withComment(comment))` está en `src/test/java/.../stepdefinitions/VoteStepDefinitions.java`, método `votaDejandoUnComentario` · el botón y el campo están en `src/main/java/.../userinterfaces/CarModelPage.java` (`BTN_VOTE`, `TXT_COMMENT`)]*

*[Cambiar a la diapositiva 10]*

### Diapositiva 10 · Buenas prácticas · Jonathan · 1 min

**Jonathan:**

Aplicamos varias buenas prácticas. La más importante: cada ejecución registra un usuario nuevo, porque Buggy solo deja votar una vez por cuenta; así las pruebas se pueden repetir las veces que sea, como pide el principio FIRST. Además, cada escenario abre su propio navegador, para que uno no afecte al otro. Los comentarios llevan fecha y hora, para encontrar exactamente el de esta ejecución. No hay valores escritos en el código: la dirección del sitio está en la configuración y los textos son constantes. Las contraseñas no aparecen en el reporte. Y usamos esperas explícitas, que esperan a que cada elemento esté listo; la pausa de dos segundos que van a ver en la demostración es solo para que se pueda seguir cada paso.

*[Archivos, en el orden en que se mencionan:]*

- *Usuario nuevo en cada ejecución: `src/main/java/.../utils/UserFactory.java`; se registra con la tarea `Register` (de Sebastián) en `VoteStepDefinitions.java`, método `inicioSesionConUnaCuentaNueva`.*
- *Un navegador por escenario: `src/test/resources/serenity.conf` (`use.unique.browser = false`) y los hooks de `src/test/java/.../stepdefinitions/StepDefinition.java`.*
- *Comentarios con fecha y hora: `src/main/java/.../utils/CommentFactory.java`.*
- *Dirección del sitio en la configuración: `src/test/resources/serenity.conf` (`pages.buggyCars`); textos y elementos como constantes en `src/main/java/.../userinterfaces/CarModelPage.java` y `HomePage.java`; claves de memoria en `src/main/java/.../utils/MemoryKeys.java`.*
- *Contraseña oculta en el reporte: `src/main/java/.../interactions/FillLoginForm.java` (se escribe con `type`, sin `Enter`).*
- *Esperas explícitas (`WaitUntil`): `src/main/java/.../tasks/Vote.java`, `SelectMostPopularModel.java` y `LogIn.java`.*
- *Pausa de dos segundos: `src/main/java/.../utils/Wait.java`.*

*[Cambiar a la diapositiva 11]*

### Diapositiva 11 · Resultados · Jonathan · 1 min

**Jonathan:**

Estos son los resultados. De los dieciséis escenarios, trece pasan siempre. Dos son intermitentes: a veces pasan y a veces no. Y uno falla siempre. Lo importante es que esas tres fallas no son errores de nuestras pruebas: son defectos del sitio. Les muestro cuáles.

*[Archivo: los resultados salen del reporte `target/site/serenity/index.html`]*

*[Cambiar a la diapositiva 12]*

### Diapositiva 12 · Defectos encontrados · Jonathan · 1 min

**Jonathan:**

Encontramos tres problemas en el sitio. El primero: el autor de los comentarios nunca aparece. Lo comprobamos consultando los datos del sitio: el servidor devuelve el autor vacío en todos los comentarios. Por eso ese escenario está marcado con la etiqueta Defecto.

El segundo es intermitente: a veces el sitio confirma el voto, pero no guarda el comentario. De nueve comentarios que enviaron nuestras pruebas, cuatro se perdieron, y en el reporte se ve la captura con el comentario escrito completo antes de votar.

El tercero es un comportamiento: la tabla de comentarios no se actualiza después de votar; el comentario nuevo solo aparece al recargar. Por eso nuestra prueba recarga la página antes de verificar.

Una prueba que falla por un error real del sitio está cumpliendo su función. Ahora se lo mostramos en vivo.

*[Archivos:]*

- *Defecto 1: escenario «El comentario muestra el nombre de su autor», con la etiqueta `@Defecto`, en `src/test/resources/features/votacion/comentar_auto.feature`; lo verifica la Question `src/main/java/.../questions/CommentRegistered.java`.*
- *Defecto 2: lo detecta la Question `src/main/java/.../questions/CommentPublished.java`; la captura del comentario escrito está en el reporte, dentro del escenario que falló.*
- *Comportamiento 3: la recarga está en la interacción `src/main/java/.../interactions/ReloadThePage.java`, y se llama en `VoteStepDefinitions.java`, método `suComentarioDeberiaAparecerEnLaLista`.*

*[Cambiar a la diapositiva 13]*

### Diapositiva 13 · Demostración · Los tres · 3 min

**Jonathan:**

Vamos a ejecutar las pruebas. Excluimos el escenario que siempre falla, para que la demostración se concentre en el flujo.

*[Jonathan escribe en la terminal]*

```powershell
.\gradlew clean test aggregate "-Dcucumber.filter.tags=not @Defecto"
```

*[Para una demostración más corta, ejecutar solo el E2E con "-Dcucumber.filter.tags=@E2E"]*

**Sebastián** *[cuando el navegador abre el formulario de registro]*:

Aquí el actor abre la página de registro. Fíjense que escribe el usuario, el nombre y el apellido. La contraseña también se escribe, pero no aparece en el reporte. Hace clic en Register y espera el mensaje de registro exitoso.

**Brayan** *[cuando inicia sesión]*:

Ahora inicia sesión con esa misma cuenta: escribe el usuario y la contraseña en el encabezado, hace clic en Login y espera el saludo con su nombre.

**Jonathan** *[cuando entra al modelo y vota]*:

Entra al modelo más popular, escribe un comentario con la fecha y la hora, hace clic en Vote y espera el mensaje de agradecimiento. Después recarga la página y verifica que el comentario aparezca en la lista.

*[Archivos: entrar al modelo, `tasks/SelectMostPopularModel.java` · comentario único, `utils/CommentFactory.java` · escribir y votar, `tasks/Vote.java` e `interactions/WriteComment.java` · mensaje de agradecimiento, `questions/TheMessage.java` · recargar, `interactions/ReloadThePage.java` · comentario en la lista, `questions/CommentPublished.java`. Todos bajo `src/main/java/.../`]*

*[Cuando termine la ejecución, abrir target\site\serenity\index.html]*

**Jonathan:**

Este es el reporte que genera Serenity. Aquí está el resumen de todos los escenarios. Si entro a uno, se ve cada paso con su captura de pantalla: esta es la evidencia de lo que hizo la prueba.

*[Plan de respaldo: si falla internet, el sitio no responde o aparece el defecto intermitente, decir lo siguiente y abrir el reporte guardado]*

**Jonathan:**

Como les contamos, el sitio tiene un defecto intermitente con los comentarios. Por eso trajimos un reporte de una ejecución anterior: aquí se ven los mismos pasos con sus capturas.

*[Cambiar a la diapositiva 14]*

### Diapositiva 14 · Aprendizajes y cierre · Los tres · 1 min

**Sebastián:**

Aprendimos que separar el qué del cómo vale la pena: gracias a las tareas y las interacciones, el escenario E2E solo necesitó un paso nuevo. Todo lo demás ya existía.

**Brayan:**

También que las pruebas deben ser independientes: con datos únicos y un navegador limpio en cada escenario, se pueden repetir sin preparar nada a mano.

**Jonathan:**

Y que automatizar también es encontrar defectos: nuestras pruebas detectaron errores reales del sitio, y el reporte deja la evidencia. Muchas gracias por su atención. Quedamos atentos a sus preguntas.

## El reporte de Serenity

### Dónde está y cómo se genera

El reporte se genera con `.\gradlew clean test aggregate` y queda en `target\site\serenity\index.html`. Se abre con doble clic. Aunque Gradle diga `BUILD FAILED` porque algún escenario falló, el reporte se genera igual: en la consola aparece `Generating Serenity Reports` con la ruta del archivo.

Cada ejecución reemplaza el reporte anterior. Si se ejecuta solo una parte (por ejemplo `-Dcucumber.filter.tags=@Autenticacion`), el reporte queda solo con esa parte. Por eso, antes de exponer, hay que ejecutar todo y guardar una copia de la carpeta `target\site\serenity`.

### Resultado de la última ejecución completa

Ejecución completa del 18 de noviembre de 2026: **16 escenarios, 14 pasan y 2 fallan**, los dos por defectos del sitio.

| Feature | Escenario | Resultado |
|---|---|---|
| Registro | Registro exitoso con datos válidos | Pasa |
| Registro | Registro con un nombre de usuario ya existente | Pasa |
| Registro | Contraseña que no cumple la política (4 filas del Outline) | Pasan las 4 |
| Registro | Confirmación de contraseña que no coincide | Pasa |
| Inicio de sesión | Inicio de sesión exitoso | Pasa |
| Inicio de sesión | Inicio de sesión fallido (2 filas del Outline) | Pasan las 2 |
| Votación | Votar un auto dejando un comentario | Pasa |
| Votación | Votar sin escribir comentario | Pasa |
| Votación | Intentar votar sin haber iniciado sesión | Pasa |
| Votación | Intentar votar dos veces el mismo auto | Pasa |
| Votación | El comentario muestra el nombre de su autor (`@Defecto`) | Falla: el sitio no muestra el autor (defecto 1) |
| E2E | Nuevo usuario se registra, inicia sesión y comenta un auto | Falla en esta ejecución: el sitio perdió el comentario (defecto 2, intermitente) |

El defecto 2 es intermitente: en otras ejecuciones el E2E pasó y falló «Votar un auto dejando un comentario». Si en la exposición pasan los dos, mejor; si falla uno, se explica con el defecto.

### Partes del reporte

| Parte | Qué muestra |
|---|---|
| Pestaña **Overall Test Results** | El resumen: el gráfico circular con el porcentaje de escenarios que pasan, el gráfico de resultados (pasan, fallan, pendientes) y el de duración. |
| Pestaña **Requirements** | Los escenarios agrupados por feature (registro, inicio de sesión, votación y E2E), porque en `serenity.conf` se configuraron los niveles «epic» y «feature». |
| Pestaña **Features** | La lista de features con cuántos escenarios pasan y fallan en cada uno. |
| Detalle de un escenario | Los pasos Given, When y Then. Dentro de cada paso, las tareas y las interacciones con el texto de su `@Step` (por ejemplo «Sebastian votes for the model»). |
| Capturas | Una captura en cada acción, porque `serenity.conf` tiene `take.screenshots = FOR_EACH_ACTION`. Al hacer clic se ve la pantalla del navegador en ese momento. |
| Verificaciones | El texto del `@Subject` de cada Question (por ejemplo «the comment ... is published»). Si falla, muestra el valor esperado y el obtenido. |
| Escenario fallido | Aparece en rojo, con el paso exacto donde falló, el mensaje de error y la captura de ese momento. |

### Texto para mostrar el reporte · Jonathan · 1 min

*[Abrir target\site\serenity\index.html]*

**Jonathan:**

Este es el reporte que genera Serenity. En el resumen se ve que se ejecutaron dieciséis escenarios: catorce pasaron y dos fallaron, y esos dos fallos corresponden a los defectos del sitio que les mostramos.

*[Clic en la pestaña Requirements]*

Aquí los escenarios están agrupados por feature: registro, inicio de sesión, votación y el flujo completo.

*[Entrar al escenario «Votar un auto dejando un comentario»]*

Si entramos a un escenario, se ven los pasos del feature, y dentro de cada paso las tareas y las interacciones que ejecutó el actor, con el mismo lenguaje del negocio: «Sebastian votes for the model», «Sebastian writes the comment».

*[Clic en una captura]*

Cada acción tiene su captura de pantalla. Esta es la evidencia de lo que hizo la prueba en el navegador.

*[Entrar al escenario @Defecto, en rojo]*

Y en un escenario fallido, Serenity marca el paso exacto donde falló y muestra qué se esperaba. Aquí esperábamos ver el nombre del autor, y el sitio no lo muestra.

## Respuestas preparadas para las preguntas del profesor

### Diferencia entre una Task y una Interaction · Responde Brayan

La Task dice qué hace el usuario en términos del negocio, por ejemplo votar o registrarse. La Interaction dice cómo lo hace, con acciones técnicas como escribir en un campo o hacer clic. Por ejemplo, la tarea Vote usa la interacción WriteComment para escribir el comentario.

### Dónde está Screenplay en el proyecto · Responde Brayan

Está en el build.gradle. En la línea 8 está el plugin screenplayarchitecture, que generó la estructura del proyecto. Y en las dependencias están serenity-screenplay, que trae las clases del patrón como Actor, Task y Question, y serenity-screenplay-webdriver, que trae lo necesario para el navegador, como Target, Click y Enter.

### Para qué sirve el Runner · Responde Brayan

El Runner le indica a Cucumber dónde están los archivos feature, con la opción features, y dónde están los StepDefinitions, con la opción glue. Si esas rutas no coinciden con las carpetas, Cucumber no encuentra los escenarios.

### Qué es un Scenario Outline · Responde Sebastián

Es una plantilla de escenario que se ejecuta una vez por cada fila de la tabla Examples. En el registro, las cuatro contraseñas inválidas son cuatro ejecuciones de un solo escenario escrito.

### Por qué la página de registro se abre por URL · Responde Sebastián

Porque Angular ignora la navegación a la página en la que ya está. Si usáramos el botón del encabezado, el formulario podría quedar con los datos del intento anterior. Abrir la URL garantiza un formulario limpio.

### Por qué cada ejecución usa un usuario nuevo · Responde Jonathan

Porque Buggy no deja registrar dos veces el mismo usuario ni votar dos veces con la misma cuenta. Con un usuario único en cada ejecución, las pruebas se pueden repetir sin preparar nada; eso es la R de repetible del principio FIRST.

*[Archivo: `src/main/java/.../utils/UserFactory.java`, método `uniqueUsername`]*

### Qué es FIRST · Responde Jonathan

Son cinco características de una buena prueba: rápida, independiente, repetible, que se valida sola y oportuna. Nosotros cuidamos sobre todo que sean independientes y repetibles, con datos únicos y un navegador nuevo en cada escenario.

### Qué es el patrón AAA · Responde Jonathan

Arrange, Act, Assert: preparar, ejecutar y verificar. En Gherkin corresponde a Given, When y Then.

### Por qué el paso del mensaje está en CommonStepDefinitions · Responde Jonathan

Porque la frase «deberia ver el mensaje» aparece en el registro, en el inicio de sesión y en la votación, y Cucumber no permite dos métodos con el mismo texto. Por eso está una sola vez, en una clase común.

*[Archivo: `src/test/java/.../stepdefinitions/CommonStepDefinitions.java`; usa la Question `src/main/java/.../questions/TheMessage.java`]*

### Cómo se obtienen los localizadores · Responde cualquiera

Con las herramientas de desarrollo de Chrome: clic derecho sobre el elemento y luego Inspeccionar. Si el elemento tiene id o name, usamos esos. Si no, escribimos un XPath a mano, buscando por el texto visible o por los atributos, y lo comprobamos con Control F en el panel de elementos.

### Para qué sirve la clase Wait · Responde Jonathan

Solo para la exposición: hace una pausa de dos segundos entre pasos para que se pueda seguir lo que hace el navegador. En un proyecto real no se usan pausas fijas, sino esperas a que cada elemento esté listo, como WaitUntil, que también usamos.

*[Archivo: `src/main/java/.../utils/Wait.java`; la pausa se cambia sin tocar el código con `-Dstep.pause=0`, configurado en `build.gradle`, bloque `tasks.named('test')`]*

### Por qué falla un escenario · Responde Jonathan

Por los defectos del sitio que les mostramos, y en el reporte está la evidencia. El escenario del autor está marcado con la etiqueta Defecto y se puede excluir de la ejecución con el filtro de etiquetas.

*[Archivo: la etiqueta `@Defecto` está en `src/test/resources/features/votacion/comentar_auto.feature`; el filtro por etiquetas se habilitó en `build.gradle`, bloque `tasks.named('test')`]*

## Material de apoyo

- Presentación: `Presentacion_Taller_Automatizacion_BuggyCars.pptx`, en la carpeta de la materia.
- Guía de estudio con cada archivo explicado línea por línea, por pestañas. Jonathan la comparte con el equipo.
- Reporte de Serenity: `target\site\serenity\index.html`.
