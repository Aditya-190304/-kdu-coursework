package com.cinestream.controller;

import com.cinestream.service.CineManager;
import com.cinestream.model.Director;
import com.cinestream.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

// i set up this controller to handle all the graphql requests coming from the client
@Controller
public class MovieResolver {

    private static final Logger log = LoggerFactory.getLogger(MovieResolver.class);

    private final CineManager manager;

    // injecting the manager here so i can send the business logic
    public MovieResolver(CineManager manager) {
        this.manager = manager;
    }

    @QueryMapping
    public Film findMovieById(@Argument String id) {
        // i'm passing the id straight to the manager
        log.info("fetching the movie with its id: {}", id);
        return manager.findFilm(id);
    }

    @SchemaMapping(typeName = "Movie", field = "director")
    public Director resolveDirector(Film film) {
        // i used schema mapping here to fix the n+1 problem by only fetching the director when asked
        log.info("resolving director for film '{}' (ID: {})", film.title(), film.directorId());
        return manager.findDirector(film.directorId());
    }

    @MutationMapping
    public Film addReview(@Argument String movieId, @Argument String comment, @Argument Integer rating) {
        // i'm handling the mutation here so the client gets the updated movie back immediately
        log.info(" added review to film {}", movieId);
        return manager.addReview(movieId, comment, rating);
    }
}