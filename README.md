
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

**Set up the Database**

1. Navigate to the `src/main/resources/db` directory in the backend project.
2. Use the following command to load the database schema into MySQL:
   ```bash
   mysql -u <username> -p < src/main/resources/db/schema.sql


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

