package com.example.Book_my_show_backend.Controllers;

import com.example.Book_my_show_backend.Dtos.MovieRequestDto;
import com.example.Book_my_show_backend.Models.MovieEntity;
import com.example.Book_my_show_backend.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public String addMovie(@RequestBody MovieRequestDto movieRequestDto){

        return movieService.addMovie(movieRequestDto);

    }

    //added
    @GetMapping("/get-movie-by-name/{movieName}")
    public ResponseEntity<MovieEntity> getMovieByName(@PathVariable("movieName") String movieName) {
        MovieEntity movie = movieService.getMovieByName(movieName);
        return new ResponseEntity<>(movie, HttpStatus.FOUND);
    }

    @GetMapping("/get-movie-by-id/{id}")
    public ResponseEntity<MovieEntity> getMovieById(@PathVariable("id") int id){
        MovieEntity movie= movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.FOUND);
    }



}
