package com.proyectodb.services;

import com.proyectodb.models.Videojuego;
import com.proyectodb.models.Plataforma;
import com.proyectodb.models.Genero;
import com.proyectodb.models.Desarrollador;
import com.proyectodb.models.Fecha;
import com.proyectodb.repositories.VideojuegoRepository;
import com.proyectodb.repositories.PlataformaRepository;
import com.proyectodb.repositories.GeneroRepository;
import com.proyectodb.repositories.DesarrolladorRepository;
import com.proyectodb.repositories.FechaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class VideojuegoService {

    private static final Logger logger = Logger.getLogger(VideojuegoService.class.getName());

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private DesarrolladorRepository desarrolladorRepository;

    @Autowired
    private FechaRepository fechaRepository;

    // Clase auxiliar para mapear los datos de la API de RAWG
    private static class RawgGameResponse {
        public List<RawgGame> results;

        private static class RawgGame {
            public int id;
            public String slug;
            public String name;
            public String released;
            public String background_image;
            public double rating;
            public int rating_top;
            public EsrbRating esrb_rating;
            public List<RawgPlatform> platforms;
            public List<RawgDeveloper> developers;
            public List<RawgGenre> genres;

            private static class EsrbRating {
                public int id;
                public String slug;
                public String name;
            }

            private static class RawgPlatform {
                public PlatformDetails platform;

                private static class PlatformDetails {
                    public int id;
                    public String name;
                }
            }

            private static class RawgDeveloper {
                public int id;
                public String name;
            }

            private static class RawgGenre {
                public int id;
                public String name;
            }
        }
    }

    private static class RawgGameDetail {
        public int id;
        public String slug;
        public String name;
        public String description;
        public String released;
        public List<RawgGameResponse.RawgGame.RawgPlatform> platforms;
        public List<RawgGameResponse.RawgGame.RawgDeveloper> developers;
        public List<RawgGameResponse.RawgGame.RawgGenre> genres;
    }

    public void fetchAndSaveVideojuegos() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.rawg.io/api/games?key=421ffe22fb2e4d74a8842e3b920687a8&page_size=10&page=";
        int page = 1;

        try {
            while (true) {
                String currentUrl = apiUrl + page;
                logger.info("Fetching data from URL: " + currentUrl);
                RawgGameResponse response = restTemplate.getForObject(currentUrl, RawgGameResponse.class);
                List<RawgGameResponse.RawgGame> games = response.results;
                if (games == null || games.isEmpty()) {
                    logger.info("No more data to fetch, breaking loop.");
                    break; // No more data to fetch
                }

                for (RawgGameResponse.RawgGame game : games) {
                    if (game.name == null || game.released == null) {
                        logger.warning("Skipping game with missing fields: " + game.id);
                        continue; // Skip this game if any of these fields is null
                    }

                    // Verificar si ya existe un Videojuego con el mismo nombre
                    Optional<Videojuego> existingVideojuego = videojuegoRepository.findByTitulo(game.name);
                    Videojuego videojuego;
                    if (existingVideojuego.isPresent()) {
                        // Si ya existe, usar el Videojuego existente
                        videojuego = existingVideojuego.get();
                    } else {
                        // Si no existe, crear un nuevo Videojuego
                        videojuego = new Videojuego();
                    }

                    videojuego.setTitulo(game.name);

                    // Obtener descripción detallada
                    String detailUrl = "https://api.rawg.io/api/games/" + game.id + "?key=421ffe22fb2e4d74a8842e3b920687a8";
                    logger.info("Fetching game detail from URL: " + detailUrl);
                    RawgGameDetail gameDetail = restTemplate.getForObject(detailUrl, RawgGameDetail.class);
                    videojuego.setDescripcion(gameDetail.description);

                    // Manejar las relaciones con otras tablas
                    // Plataformas
                    if (game.platforms != null && !game.platforms.isEmpty()) {
                        RawgGameResponse.RawgGame.RawgPlatform rawgPlatform = game.platforms.get(0);
                        String platformName = rawgPlatform.platform.name;
                        Optional<Plataforma> optionalPlataforma = plataformaRepository.findByNombre(platformName);
                        Plataforma plataforma = optionalPlataforma.orElseGet(() -> {
                            Plataforma newPlataforma = new Plataforma();
                            newPlataforma.setNombre(platformName);
                            newPlataforma.setDescripcion("Descripción de " + platformName);
                            return plataformaRepository.save(newPlataforma);
                        });
                        videojuego.setId_Plataforma(plataforma.getId());
                    }

                    // Géneros
                    if (game.genres != null && !game.genres.isEmpty()) {
                        RawgGameResponse.RawgGame.RawgGenre rawgGenre = game.genres.get(0);
                        String genreName = rawgGenre.name;
                        Optional<Genero> optionalGenero = generoRepository.findByNombre(genreName);
                        Genero genero = optionalGenero.orElseGet(() -> {
                            Genero newGenero = new Genero();
                            newGenero.setNombre(genreName);
                            return generoRepository.save(newGenero);
                        });
                        videojuego.setId_Genero(genero.getId());
                    } else {
                        logger.warning("Skipping game without genre: " + game.id);
                        continue;
                    }

                    // Desarrolladores
                    if (game.developers != null && !game.developers.isEmpty()) {
                        RawgGameResponse.RawgGame.RawgDeveloper rawgDeveloper = game.developers.get(0);
                        String developerName = rawgDeveloper.name;
                        Optional<Desarrollador> optionalDesarrollador = desarrolladorRepository.findByNombre(developerName);
                        Desarrollador desarrollador = optionalDesarrollador.orElseGet(() -> {
                            Desarrollador newDesarrollador = new Desarrollador();
                            newDesarrollador.setNombre(developerName);
                            newDesarrollador.setDescripcion("Descripción de " + developerName);
                            return desarrolladorRepository.save(newDesarrollador);
                        });
                        videojuego.setId_Desarrollador(desarrollador.getId());
                    } else {
                        logger.warning("Skipping game without developer: " + game.id);
                        continue;
                    }

                    // Fecha de lanzamiento
                    Date releaseDate;
                    try {
                        releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(game.released);
                    } catch (ParseException e) {
                        logger.warning("Skipping game with invalid date: " + game.id);
                        continue; // Skip this game if the release date is not in the correct format
                    }

                    Fecha fecha = fechaRepository.findByFecha(releaseDate).orElseGet(() -> {
                        Fecha newFecha = new Fecha();
                        newFecha.setFecha(releaseDate);
                        return fechaRepository.save(newFecha);
                    });
                    videojuego.setId_Fecha_Lanzamiento(fecha.getId());

                    videojuegoRepository.save(videojuego);
                    logger.info("Saved game: " + videojuego.getTitulo());
                }
                page++;
            }
        } catch (HttpClientErrorException | ResourceAccessException e) {
            // Handle error when calling the API
            logger.severe("Error al llamar a la API: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
        }
    }
}
