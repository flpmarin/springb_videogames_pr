
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


## Prerequisites

1. **Backend (Spring Boot):**
   - Java 17 or higher
   - Maven
   - MySQL 8 or higher

2. **Frontend (Vue.js):**
   - Node.js 16 or higher
   - npm (included with Node.js)

3. **Database:**
   - MySQL installed and configured.

---

## Backend Configuration

1. **Set up the database:**
   - Create the `videojuego` database in MySQL:
     ```sql
     CREATE DATABASE videojuego;
     ```

   - Set up the required tables using the following SQL schema:


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

**Configure the `application.properties` file:**
   - Edit the `application.properties` file with your credentials:
     ```properties
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **(Optional) Configure environment variables**
   Instead of editing the `application.properties` file, you can configure the credentials as global environment variables.

4. **Start the backend:**
   Run the server:
   ```bash
   mvn spring-boot:run

---
## Frontend Configuration

1. Navigate to the videogames directory.

2. Install the dependencies:
   ```bash
   npm install
   ```

3. Run dev server:
   ```bash
   npm run dev
   ```
---

