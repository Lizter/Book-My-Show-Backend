package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.ShowRequestDto;
import com.example.Book_my_show_backend.Models.*;
import com.example.Book_my_show_backend.Repository.MovieRepository;
import com.example.Book_my_show_backend.Repository.ShowRepository;
import com.example.Book_my_show_backend.Repository.ShowSeatsRepository;
import com.example.Book_my_show_backend.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    ShowSeatsRepository showSeatsRepository;

    @Autowired
    ShowRepository showRepository;


    public String addShow(ShowRequestDto showRequestDto){

        //show Entity creation
        ShowEntity showEntity= ShowEntity.builder().showDate(showRequestDto.getShowDate()).showTime(showRequestDto.getShowTime()).multiplier(showRequestDto.getMultiplier()).build();


        //we need to get movieRepo
        MovieEntity movieEntity = movieRepository.findByMovieName(showRequestDto.getMovieName());

        //we need to get theatreRepo
        TheatreEntity theatreEntity= theatreRepository.findById(showRequestDto.getTheatreId()).get();

        showEntity.setTheatre(theatreEntity);
        showEntity.setMovie(movieEntity);

        //because  we are doing bidirectional mapping
        //Approach 1:
//        List<ShowEntity> currentListOfShow= movieEntity.getListOfShows();
//        currentListOfShow.add(showEntity);
//        movieEntity.setListOfShows(currentListOfShow);

        //Approach 2:

        movieEntity.getListOfShows().add(showEntity);
        theatreEntity.getListOfShows().add(showEntity);

        //theatreRepository.save(theatreEntity)


        List<ShowSeatEntity> seatEntityList= createShowSeats(theatreEntity.getTheatreSeatEntityList());

        showEntity.setListOfSeats(seatEntityList);

        //For each show seat : we need to mark to which show it belongs (foreign key)

        for(ShowSeatEntity showSeat: seatEntityList)
        {
            showSeat.setShow(showEntity);
        }

        movieRepository.save(movieEntity);
        theatreRepository.save(theatreEntity);
        //showRepository.save(showEntity); - this does not need to be called because parent save method is called


        return "Show added successfully";



    }

    public List<ShowSeatEntity> createShowSeats(List<TheatreSeatEntity> theatreSeatEntityList){

        List<ShowSeatEntity> seats= new ArrayList<>();

        for(TheatreSeatEntity theatreSeat: theatreSeatEntityList){

            ShowSeatEntity showSeat = ShowSeatEntity.builder().seatNo(theatreSeat.getSeatNo()).seatType(theatreSeat.getSeatType()).build();

            seats.add(showSeat);
        }
        showSeatsRepository.saveAll(seats);

        return seats;

    }


}
