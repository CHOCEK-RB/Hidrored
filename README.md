### Estilos de Programación:
Things: Se aplico el estilo orientado a objetos, modelando las partes del sistema como entidades del dominio (Usuario), comandos (RegistrarUsuarioCommand) y objetos de transporte (UsuarioDTO). Cada clase tiene una responsabilidad clara, con sus propios datos y comportamiento, siguiendo buenas prácticas de diseño orientado a objetos.

Error/Exception Handling: Manejo validaciones y errores mediante excepciones controladas en los servicios de aplicación. Por ejemplo, si el correo electrónico ya está en uso al registrar un nuevo usuario, se lanza una IllegalStateException. De forma similar, si las credenciales ingresadas en el login no coinciden, se lanza una SecurityException. Esto permite cortar el flujo de ejecución y enviar mensajes claros sobre lo que falló, manteniendo el código limpio y la lógica de negocio centralizada.

RESTful: Se usa para organizar los endpoints de la API. Aunque el fragmento de código mostrado no contiene los controladores directamente, las clases de aplicación como UsuarioApplicationService están diseñadas para ser usadas desde endpoints REST del tipo /api/usuarios, siguiendo una estructura clara basada en recursos. Cada acción del servicio representa un caso de uso que puede mapearse a un verbo HTTP (POST para registrar, GET para obtener, etc.), y los datos que se exponen al cliente se encapsulan mediante objetos DTO como UsuarioDTO.

###Lab 11

Practicas usadas:
Se aplicaron practicas para mejorar la legibilidad y mantenibilidad. Primero, se usaron nombres claros, descriptivos y consistentes en español, como ServicioUsuario, repositorioUsuario o codificadorContrasenia, lo que ayuda a que el propósito de cada clase o variable sea evidente sin necesidad de leer toda la implementación.

Ademas, se implemento el principio de funciones pequeñas, esto mejora la claridad y permite reutilización. Se añadieron comentarios y Javadoc en los métodos públicos para explicar claramente qué hace cada uno, qué recibe y qué retorna, facilitando el trabajo en equipo y la documentación automática. También se mejoró la estructura del código fuente, ordenando los elementos de forma lógica: atributos primero, luego constructor, métodos públicos y finalmente los privados, siguiendo un estándar de legibilidad. 

