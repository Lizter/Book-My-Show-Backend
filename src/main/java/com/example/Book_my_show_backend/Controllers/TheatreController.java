package com.example.Book_my_show_backend.Controllers;

import com.example.Book_my_show_backend.Dtos.ShowResponseDto;
import com.example.Book_my_show_backend.Dtos.TheatreRequestDto;
import com.example.Book_my_show_backend.Dtos.TheatreResponseDto;
import com.example.Book_my_show_backend.Models.ShowEntity;
import com.example.Book_my_show_backend.Models.TheatreEntity;
import com.example.Book_my_show_backend.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    TheatreService theatreService;

    @PostMapping("/add")
    public String addTheatre(@RequestBody TheatreRequestDto theatreRequestDto){

        return theatreService.createTheatre(theatreRequestDto);
    }

    @GetMapping("/get-all-theatreList-where-movie-is-there")
    public ResponseEntity<List<String>> getAllTheatreList(@RequestParam("movieName") String movieName){
        List<String> theatreList= theatreService.getTheatreListWithMovie(movieName);
        return new ResponseEntity<>(theatreList, HttpStatus.FOUND);
    }


    @GetMapping("/get-all-theatres")
    public ResponseEntity<List<TheatreResponseDto>> getAllTheatres(){
        List<TheatreResponseDto> theatreResponseDtoList= theatreService.getAllTheatres();
        return new ResponseEntity<>(theatreResponseDtoList, HttpStatus.FOUND);
    }

    @GetMapping("/get-showsList-in-theatre-by-theatreName/{name}")
    public ResponseEntity<List<ShowResponseDto>> getShowListInTheatreByTheatreName(@PathVariable("name") String name){

        List<ShowResponseDto> showEntityResponseDto= theatreService.getShowListInTheatreByTheatreName(name);
        return new ResponseEntity<>(showEntityResponseDto, HttpStatus.FOUND);
    }


}
