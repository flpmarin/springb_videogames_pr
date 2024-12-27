
## Team Videogames-hub
- [Antonela Díaz](https://github.com/antoneladg91)
- [Sergio Domínguez](https://github.com/DISTRONYX)
- [Juan José Romero](https://github.com/juanjo2gm)
- [Felipe Marín](http://github.com/flpmarin)


# videogame-hub Backend
This is the backend service for the **Videogame Hub App**, built with Spring Boot and MySQL. It provides RESTful APIs for managing a personal video game collection and fetching gaming news from an external API.

![Videogame Hub Preview](./img.webp)

## Features
- Provides APIs for:
  - Handling CRUD operations for video games.
  - Fetching gaming news from an external API.
- Persistent data storage using MySQL.
- Designed to integrate with the Vue.js frontend, where user authentication and management are handled.

---

## Technologies Used
- **Backend Framework:** Spring Boot
- **Database:** MySQL
- **RESTful APIs:** JSON-based endpoints
- **External Services:** Gaming news API

Integration with the Frontend

The backend is designed to work with the Vue.js frontend located at: 
[See videogame-hub-frontend  repo](https://github.com/flpmarin/videogame-hub-frontend)

The login and authentication are handled on the frontend (using Vue.js).
No Spring Security or JWT implementation is used in this project.

### Prerequisites
1. **Java 17** or higher installed.
2. **MySQL** server running locally or accessible remotely.
3. **Maven** installed for dependency management.


# Proyecto Fullstack: Backend en Spring Boot y Frontend en Vue.js

Este proyecto es una aplicación fullstack que utiliza Spring Boot para el backend y Vue.js para el frontend.

## Requisitos previos

1. **Backend (Spring Boot):**
   - Java 17 o superior
   - Maven
   - MySQL 8 o superior

2. **Frontend (Vue.js):**
   - Node.js 16 o superior
   - npm (incluido con Node.js)

3. **Base de datos:**
   - MySQL instalado y configurado.

---

## Configuración del backend

1. **Configura la base de datos:**
   - Crea la base de datos `videojuego` en MySQL:
     ```sql
     CREATE DATABASE videojuego;
     ```

   - Configura las tablas necesarias usando el siguiente esquema SQL.:

     ```sql
     CREATE TABLE `calificaciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_Videojuego` bigint NOT NULL,
  `id_Usuario` bigint NOT NULL,
  `calificacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_Videojuego` (`id_Videojuego`),
  KEY `id_Usuario` (`id_Usuario`),
  CONSTRAINT `calificaciones_ibfk_1` FOREIGN KEY (`id_Videojuego`) REFERENCES `videojuegos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `calificaciones_ibfk_2` FOREIGN KEY (`id_Usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `fechas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1081 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `generos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plataformas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_Usuario` varchar(50) NOT NULL,
  `correo_Electronico` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuarios_videojuegos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_Usuario` bigint NOT NULL,
  `id_Videojuego` bigint NOT NULL,
  `fecha_agregado` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_Usuario` (`id_Usuario`),
  KEY `id_Videojuego` (`id_Videojuego`),
  CONSTRAINT `usuarios_videojuegos_ibfk_1` FOREIGN KEY (`id_Usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `usuarios_videojuegos_ibfk_2` FOREIGN KEY (`id_Videojuego`) REFERENCES `videojuegos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `videojuegos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `id_Plataforma` bigint NOT NULL,
  `id_Fecha_Lanzamiento` bigint NOT NULL,
  `id_Genero` bigint NOT NULL,
  `descripcion` text,
  `id_desarrollador` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_Plataforma` (`id_Plataforma`),
  KEY `id_Fecha_Lanzamiento` (`id_Fecha_Lanzamiento`),
  KEY `id_Genero` (`id_Genero`),
  CONSTRAINT `videojuegos_ibfk_1` FOREIGN KEY (`id_Plataforma`) REFERENCES `plataformas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `videojuegos_ibfk_2` FOREIGN KEY (`id_Fecha_Lanzamiento`) REFERENCES `fechas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `videojuegos_ibfk_3` FOREIGN KEY (`id_Genero`) REFERENCES `generos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1300 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
     ```

2. **Configura el archivo `application.properties`:**
   - Copia el archivo de ejemplo:
     ```bash
     cp src/main/resources/application.properties.example src/main/resources/application.properties
     ```
   - Edita el archivo `application.properties` con tus credenciales de base de datos:
     ```properties
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

3. **(Opcional) Configura variables de entorno**
   En lugar de editar el archivo `application.properties`, puedes configurar las credenciales como variables de entorno globales.

4. **Inicia el backend:**
   Una vez configurado todo, navega al directorio del backend y ejecuta el servidor:
   ```bash
   mvn spring-boot:run
   ```
---

## Configuración del frontend

1. Navega al directorio del videogames.

2. Instala las dependencias:
   ```bash
   npm install
   ```

3. Inicia el servidor de desarrollo:
   ```bash
   npm run dev
   ```
---

