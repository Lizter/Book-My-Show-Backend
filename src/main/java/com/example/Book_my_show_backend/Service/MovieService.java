package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.MovieRequestDto;
import com.example.Book_my_show_backend.Models.MovieEntity;
import com.example.Book_my_show_backend.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public String addMovie(MovieRequestDto movieRequestDto){

        //Convert Dto to Entity layer for saving it to the Database
        MovieEntity movie = MovieEntity.builder().movieName(movieRequestDto.getName()).duration(movieRequestDto.getDuration()).releaseDate(movieRequestDto.getReleaseDate()).build();

        try {
            movieRepository.save(movie);
        }
        catch(Exception e){
            return "movie could not be added";
        }

        return "movie added successfully";

    }

    public MovieEntity getMovieByName(String movieName) {
        MovieEntity movie = movieRepository.findByMovieName(movieName);

        return movie;
    }

    public MovieEntity getMovieById(int id){
        MovieEntity movie= movieRepository.findById(id).get();

        return movie;
    }



}

