package com.cinestream.service;

import com.cinestream.repo.FilmStore;
import com.cinestream.model.Director;
import com.cinestream.model.Film;
import com.cinestream.model.Review;
import com.cinestream.exception.MissingItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CineManager {

    private static final Logger log = LoggerFactory.getLogger(CineManager.class);
    private final FilmStore store;

    // injecting the store here to access the data
    public CineManager(FilmStore store) {
        this.store = store;
    }

    public Film findFilm(String id) {
        // i'm using orElseThrow here to avoid nested if checks
        return store.getFilm(id)
                .orElseThrow(() -> {
                    log.warn("search failed for film id: {} not found.", id);
                    return new MissingItemException("no film with the id: " + id);
                });
    }

    public Director findDirector(String directorId) {
        // fetching the director directly or throwing an exception if the data is missing
        return store.getDirector(directorId)
                .orElseThrow(() -> {
                    log.error("director id {} missing.", directorId);
                    return new MissingItemException("director iD " + directorId + " is missing");
                });
    }

    public Film addReview(String filmId, String comment, Integer rating) {
        log.debug("review for film {}", filmId);

        // reusing my existing method to find the film
        Film film = findFilm(filmId);

        // creating a new film object with the added review since records are immutable
        Film updated = film.addReview(new Review(comment, rating));
        Film saved = store.save(updated);

        log.info("review added to '{}'", saved.title());
        return saved;
    }
}