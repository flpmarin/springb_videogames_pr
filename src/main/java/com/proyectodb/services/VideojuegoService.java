package com.proyectodb.services;

import com.proyectodb.models.Videojuego;
import com.proyectodb.models.Plataforma;
import com.proyectodb.models.Genero;
import com.proyectodb.models.Fecha;
import com.proyectodb.repositories.VideojuegoRepository;
import com.proyectodb.repositories.PlataformaRepository;
import com.proyectodb.repositories.GeneroRepository;
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
    private FechaRepository fechaRepository;

    private static class RawgGameResponse {
        public List<RawgGame> results;

        private static class RawgGame {
            public int id;
            public String name;
            public String released;
            public List<RawgPlatform> platforms;
            public List<RawgGenre> genres;

            private static class RawgPlatform {
                public PlatformDetails platform;

                private static class PlatformDetails {
                    public String name;
                }
            }

            private static class RawgGenre {
                public String name;
            }
        }
    }

    private static class RawgGameDetail {
        public String description;
    }

    public void fetchAndSaveVideojuegos() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.rawg.io/api/games?key=417b1db168dc48608cb268504fa9a94c&page_size=10&page=";// 
        int page = 1;

        try {
            while (true) {
                String currentUrl = apiUrl + page;
                logger.info("Obteniendo datos de la URL: " + currentUrl);
                RawgGameResponse response = restTemplate.getForObject(currentUrl, RawgGameResponse.class);
                List<RawgGameResponse.RawgGame> games = response.results;

                if (games == null || games.isEmpty()) {
                    logger.info("No hay más datos para obtener, saliendo del bucle.");
                    break;
                }

                for (RawgGameResponse.RawgGame game : games) {
                    if (game.name == null || game.released == null) {
                        logger.warning("Saltando juego con campos faltantes: " + game.id);
                        continue;
                    }

                    // Verificar si ya existe un Videojuego con el mismo nombre
                    Optional<Videojuego> existingVideojuego = videojuegoRepository.findByTitulo(game.name);
                    if (existingVideojuego.isPresent()) {
                        logger.info("El videojuego ya existe, saltando: " + game.name);
                        continue; // Si ya existe, no hacer nada y continuar con el siguiente juego
                    }

                    // Crear un nuevo Videojuego
                    Videojuego videojuego = new Videojuego();
                    videojuego.setTitulo(game.name);

                    // Obtener descripción detallada
                    String detailUrl = "https://api.rawg.io/api/games/" + game.id + "?key=417b1db168dc48608cb268504fa9a94c";
                    logger.info("Obteniendo detalles del juego desde la URL: " + detailUrl);
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
                        logger.warning("Saltando juego sin género: " + game.id);
                        continue;
                    }

                    // Fecha de lanzamiento
                    Date releaseDate;
                    try {
                        releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(game.released);
                    } catch (ParseException e) {
                        logger.warning("Saltando juego con fecha inválida: " + game.id);
                        continue;
                    }

                    Fecha fecha = fechaRepository.findByFecha(releaseDate).orElseGet(() -> {
                        Fecha newFecha = new Fecha();
                        newFecha.setFecha(releaseDate);
                        return fechaRepository.save(newFecha);
                    });
                    videojuego.setId_Fecha_Lanzamiento(fecha.getId());

                    // Guardar el videojuego después de establecer todas las relaciones
                    videojuegoRepository.save(videojuego);
                    logger.info("Juego guardado: " + videojuego.getTitulo());
                }
                page++;
            }
        } catch (HttpClientErrorException | ResourceAccessException e) {
            // Manejar error al llamar a la API
            logger.severe("Error al llamar a la API: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Error inesperado: " + e.getMessage());
        }
    }
}
