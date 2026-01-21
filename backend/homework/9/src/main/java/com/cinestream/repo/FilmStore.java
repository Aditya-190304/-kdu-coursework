package com.cinestream.repo;

import com.cinestream.model.Director;
import com.cinestream.model.Film;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FilmStore {

    // keeping these lists in memory to act as my simple database
    private final List<Film> films = new ArrayList<>();
    private final List<Director> directors = new ArrayList<>();

    @PostConstruct
    public void loadDefaults() {
        // i'm adding some dummy data here so i have something to test with immediately
        directors.add(new Director("dir-1", "christopher nolan", 15));
        directors.add(new Director("dir-2", "jon favreau", 8));

        films.add(new Film("mov-1", "inception", "sci-fi", "dir-1", new ArrayList<>()));
        films.add(new Film("mov-2", "iron man", "action", "dir-2", new ArrayList<>()));
    }

    public Optional<Film> getFilm(String id) {
        // a stream filter to find the movie by its id
        return films.stream().filter(f -> f.id().equals(id)).findFirst();
    }

    public Optional<Director> getDirector(String id) {
        //  looking up the director by id from my list
        return directors.stream().filter(d -> d.id().equals(id)).findFirst();
    }

    public Film save(Film film) {
        // i'm removing the old version and adding the new one to simulate an update since i don't have a real db
        films.removeIf(f -> f.id().equals(film.id()));
        films.add(film);
        return film;
    }
}