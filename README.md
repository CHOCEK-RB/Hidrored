### Estilos de Programación:
Things: Se aplico el estilo orientado a objetos, modelando las partes del sistema como entidades del dominio (Usuario), comandos (RegistrarUsuarioCommand) y objetos de transporte (UsuarioDTO). Cada clase tiene una responsabilidad clara, con sus propios datos y comportamiento, siguiendo buenas prácticas de diseño orientado a objetos.

Error/Exception Handling: Manejo validaciones y errores mediante excepciones controladas en los servicios de aplicación. Por ejemplo, si el correo electrónico ya está en uso al registrar un nuevo usuario, se lanza una IllegalStateException. De forma similar, si las credenciales ingresadas en el login no coinciden, se lanza una SecurityException. Esto permite cortar el flujo de ejecución y enviar mensajes claros sobre lo que falló, manteniendo el código limpio y la lógica de negocio centralizada.

RESTful: Se usa para organizar los endpoints de la API. Aunque el fragmento de código mostrado no contiene los controladores directamente, las clases de aplicación como UsuarioApplicationService están diseñadas para ser usadas desde endpoints REST del tipo /api/usuarios, siguiendo una estructura clara basada en recursos. Cada acción del servicio representa un caso de uso que puede mapearse a un verbo HTTP (POST para registrar, GET para obtener, etc.), y los datos que se exponen al cliente se encapsulan mediante objetos DTO como UsuarioDTO.

###Lab 11

Practicas usadas:
El uso de nombres claros es fundamental para que el código sea fácil de entender. En este caso, los nombres como repositorioUsuario, codificadorContrasenia, verificarCorreoDisponible, registrarNuevoUsuario y autenticarUsuario son ejemplos bien elegidos. Cada uno indica claramente su propósito y evita ambigüedades. 

En cuanto a las funciones, el código sigue una organización donde cada método realiza una única tarea. Por ejemplo, registrarNuevoUsuario se encarga únicamente de registrar usuarios, mientras que la validación del correo está separada en su propio método (verificarCorreoDisponible). Esta separación mejora la modularidad y hace más fácil reutilizar o probar cada función de forma independiente. También evita que un método crezca innecesariamente en complejidad, lo cual mejora la mantenibilidad del sistema.

Respecto a los comentarios, se usan con moderación y aportan información útil. Por ejemplo, en UsuarioDTO, el comentario sobre el constructor explica que su acceso es privado para controlar su creación desde un método fábrica. Este tipo de comentarios son valiosos porque explican el "por qué" de una decisión de diseño, en lugar de describir lo obvio. 

La estructura del código fuente también está bien aplicada. Cada clase está organizada de forma ordenada: primero se declaran los atributos, luego el constructor, seguido de los métodos públicos y por último los privados. Además, se respetan correctamente las indentaciones y espacios, lo que facilita la lectura visual del código. Las anotaciones como @Service están ubicadas justo donde corresponde, antes de la declaración de la clase, manteniendo la coherencia con las convenciones de Spring.

En lo que respecta a la representación de datos, el uso de un DTO como UsuarioDTO es una decisión correcta. Este patrón te permite exponer sólo los datos necesarios hacia el cliente, protegiendo al mismo tiempo la lógica y atributos internos de las entidades del dominio. Además, el método desdeDominio() es una forma limpia y controlada de transformar una entidad Usuario en un DTO, reforzando la separación de responsabilidades entre capas.

Para el tratamiento de errores, se aplican correctamente excepciones personalizadas. En lugar de lanzar errores genéricos, se define y se usan clases como CorreoDuplicadoException y CredencialesInvalidasException. Esto no sólo mejora la claridad del código, sino que también permite manejar los errores de forma específica en capas superiores (como en controladores o filtros), mostrando mensajes apropiados al usuario o registrando errores de forma más precisa.

Finalmente, el diseño de clases es coherente con la responsabilidad única. ServicioUsuario está claramente enfocado en la lógica de aplicación relacionada con los usuarios, y UsuarioDTO en la transferencia de datos. 
