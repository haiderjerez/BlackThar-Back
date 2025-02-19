# 🛠️ BlackThar Backend - Spring Boot 3

**BlackThar Backend** es una API RESTful construida con **Spring Boot 3**, utilizando **JWT** para autenticación, **Spring Security**, **JPA con MySQL**, y **CORS habilitado** para su integración con el frontend.

---

## 📌 Tecnologías Usadas
- **Java 17**
- **Spring Boot 3.4.2**
- **Spring Security (JWT)**
- **Spring Data JPA (MySQL)**
- **Spring Web**
- **Lombok**
- **Maven**
- **CORS habilitado**

---

## 🚀 Configuración del Proyecto
### 1️⃣ Clonar los Repositorios
para el back
https://github.com/haiderjerez/BlackThar-Back.git

para el front
https://github.com/haiderjerez/blacktharFront.git

### 2️⃣ Configurar Variables de Entorno
Configura la base de datos en src/main/resources/application.properties:

# Configuración del Servidor
server.port=8080

# Configuración de Base de Datos (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/blackthar_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

# Configuración JWT
jwt.secret=MI_CLAVE_SECRETA_SUPER_SEGURA_256_BITS
jwt.expiration=3600000  # 1 hora en milisegundos

### 3️⃣ Instalar Dependencias
mvn clean install

### 4️⃣ Ejecutar el Proyecto
mvn spring-boot:run
La API estará disponible en:
🔗 http://localhost:8080

## 🛠️ Endpoints Disponibles
###🔐 Autenticación
Método	Endpoint	Descripción
POST	/api/auth/login	Iniciar sesión y obtener un JWT
POST	/api/auth/register	Registrar un nuevo usuario

###👤 Usuarios
Método	Endpoint	Descripción
GET	/api/users/{id}	Obtener usuario por ID
PUT	/api/users/{id}	Actualizar usuario
DELETE	/api/users/{id}	Eliminar usuario

###📝 Reacciones
Método	Endpoint	Descripción
POST	/api/reactions	Crear una reacción
DELETE	/api/reactions/{id}	Eliminar una reacción por ID

### 🔑 Autenticación con JWT
### 1️⃣ Registrar un usuario
```json
POST /api/auth/register
{
  "username": "usuario123",
  "password": "passwordSeguro",
  "email": "user@email.com"
}
```
### 2️⃣ Iniciar sesión
```json
POST /api/auth/login
{
  "username": "usuario123",
  "password": "passwordSeguro"
}
```
## 🔹 Respuesta (JWT Token):

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

###3️⃣ Usar el Token en las peticiones protegidas
Agregar el token en Authorization en cada request:
```makefile
Authorization: Bearer TU_TOKEN_AQUI
```
##📜 Estructura del Proyecto
```pgsql
BlackThar/
│── src/
│   ├── main/
│   │   ├── java/com/Pro/BalckThar/
│   │   │   ├── MiRedSocialV2Application.java
│   │   │   ├── config/            # Configuración de seguridad, CORS, JWT, etc.
│   │   │   ├── common/            # Clases comunes (utilidades, excepciones, constantes)
│   │   │   ├── modules/           # **Módulos principales del sistema**
│   │   │   │   ├── auth/          # Módulo de autenticación y seguridad
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── repository/
│   │   │   │   │   ├── entity/
│   │   │   │   │   ├── dto/
│   │   │   │   │   ├── security/
│   │   │   │   ├── user/          # Módulo de gestión de usuarios
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── repository/
│   │   │   │   │   ├── entity/
│   │   │   │   │   ├── dto/
│   │   │   │   ├── post/          # Módulo de publicaciones
│   │   │   │   ├── comment/       # Módulo de comentarios
│   │   │   │   ├── reaction/      # Módulo de reacciones (likes)
│   │   │   │   ├── follow/        # Módulo de seguimiento de usuarios
│   │   │   │   ├── notification/  # Módulo de notificaciones
│   │   │   ├── exception/         # Manejo global de excepciones
│   │   │   ├── config/            # Configuración de Spring Security, JWT, Swagger
│   │   ├── resources/
│   │   │   ├── application.properties  # Configuración de base de datos, JWT, CORS
│   │   │   ├── application-dev.properties  # Config para entorno de desarrollo
│   │   │   ├── application-prod.properties # Config para entorno de producción
│── pom.xml
```
##🛡 Seguridad y CORS
Spring Security + JWT protege las rutas sensibles.
CORS habilitado en SecurityConfig.java para permitir llamadas desde el frontend.
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    return new UrlBasedCorsConfigurationSource() {{
        registerCorsConfiguration("/**", configuration);
    }};
}
```

###1️⃣ Error: "Cannot connect to database"
✔ Solución: Asegúrate de que MySQL esté corriendo y que la configuración en application.properties sea correcta.

###2️⃣ Error: "JWT token not valid"
✔ Solución: Asegúrate de enviar el token correcto en Authorization: Bearer <TOKEN>.

###3️⃣ Error: "CORS blocked"
✔ Solución: Asegúrate de que CORS está habilitado en SecurityConfig.java.

##3 LICENCIAS
Hecho por: Haider Jerez Vergel
github: https://github.com/haiderjerez


