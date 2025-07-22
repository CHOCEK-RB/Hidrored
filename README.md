Estilos de programación aplicados

Pipeline (Fluent API):
En mis clases de configuración de Spring Security (SecurityConfig.java y WebConfig.java) utilizo el builder pattern para encadenar llamadas en un flujo tipo tubería. Cada llamada devuelve el mismo objeto http, lo que permite una sintaxis concisa y legible. Por ejemplo:
http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz.requestMatchers("/api/**").permitAll().anyRequest().authenticated());

RESTful:
Sigo convenciones REST para organizar mis endpoints. Toda la API queda expuesta bajo el path /api/**, de manera que cada URI representa un recurso, por ejemplo:
/api/reportes
/api/seguimiento
/api/usuarios
La configuración de seguridad en Spring hace que esas rutas sean accesibles sin autenticación y protege el resto de endpoints.

Cookbook:
En React he creado componentes “recetarios” reutilizables que encapsulan fragmentos de UI completos. Cada uno es una receta para montar una pieza de interfaz y luego los combino en mi página principal. Ejemplos:
– IconCard: receta para mostrar una tarjeta con icono, título y descripción, definida como
const IconCard: React.FC<{ icon: JSX.Element; title: string; children: ReactNode }> = ({ icon, title, children }) => (…);
– ReportIcon, TrackIcon e ImproveIcon: recetas para renderizar distintos SVG de iconos.

Things:
Modelado de “cosas” tanto en frontend como en backend:
– En React: cada componente (IconCard, ReportIcon, TrackIcon, ImproveIcon, HomePage) es un objeto con datos (props) y comportamiento (método render).
– En Spring: cada bean (PasswordEncoder, SecurityFilterChain) es una cosa gestionada por el contenedor, con su propia configuración y ciclo de vida. Por ejemplo:
@Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
@Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { … }
