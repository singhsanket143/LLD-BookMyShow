package com.example.bms.services;

import com.example.bms.models.Movie;
import com.example.bms.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    public MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAllMovies() {
        return this.movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findMovieById(long id) {
        return this.movieRepository.findById(id);
    }
}
