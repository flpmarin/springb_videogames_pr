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

## Team Videogames-hub
- [Antonela Díaz](https://github.com/antoneladg91)
- [Sergio Domínguez](https://github.com/DISTRONYX)
- [Juan José Romero](https://github.com/juanjo2gm)
- [Felipe Marín](http://github.com/flpmarin)
