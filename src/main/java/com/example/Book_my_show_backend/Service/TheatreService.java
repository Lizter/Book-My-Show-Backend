package com.example.Book_my_show_backend.Service;
import com.example.Book_my_show_backend.Dtos.ShowResponseDto;
import com.example.Book_my_show_backend.Dtos.TheatreRequestDto;
import com.example.Book_my_show_backend.Dtos.TheatreResponseDto;
import com.example.Book_my_show_backend.Enums.SeatType;
import com.example.Book_my_show_backend.Models.MovieEntity;
import com.example.Book_my_show_backend.Models.ShowEntity;
import com.example.Book_my_show_backend.Models.TheatreEntity;
import com.example.Book_my_show_backend.Models.TheatreSeatEntity;
import com.example.Book_my_show_backend.Repository.MovieRepository;
import com.example.Book_my_show_backend.Repository.TheatreRepository;
import com.example.Book_my_show_backend.Repository.TheatreSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.Book_my_show_backend.Enums.SeatType.CLASSIC;
import static com.example.Book_my_show_backend.Enums.SeatType.PLATINUM;

import com.example.Book_my_show_backend.Models.TheatreEntity;


@Service
public class TheatreService {

    @Autowired
    TheatreSeatRepository theatreSeatRepository;

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    MovieRepository movieRepository;


    public String createTheatre(TheatreRequestDto theatreRequestDto){



        TheatreEntity theatre = TheatreEntity.builder().city(theatreRequestDto.getCity()).name(theatreRequestDto.getName()).address(theatreRequestDto.getAddress()).build();
        List<TheatreSeatEntity> theatreSeats= createTheatreSeats();

        //Bidirectional mapping
        theatre.setTheatreSeatEntityList(theatreSeats);

        //once we get list
        //for each theatre seat set TheatreEntity
        for(TheatreSeatEntity theatreSeat:theatreSeats ){

            theatreSeat.setTheatre(theatre);
        }

        theatreRepository.save(theatre);

        return "Theatre added successfully";

    }

    private List<TheatreSeatEntity> createTheatreSeats(){

        List<TheatreSeatEntity> seats= new ArrayList<>();

//        TheatreSeatEntity theatreSeat1= new TheatreSeatEntity("1A",CLASSIC,100);
//        TheatreSeatEntity theatreSeat2= new TheatreSeatEntity("1B",CLASSIC,100);
//        TheatreSeatEntity theatreSeat3= new TheatreSeatEntity("1C",CLASSIC,100);
//        TheatreSeatEntity theatreSeat4= new TheatreSeatEntity("1D",CLASSIC,100);
//        TheatreSeatEntity theatreSeat5= new TheatreSeatEntity("1E",CLASSIC,100);

        //optimised by adding loop

        for(int i=0;i<5;i++)
        {
            char ch = (char)('A'+i);
            String seatNo = "1"+ ch;
            TheatreSeatEntity theatreSeat= new TheatreSeatEntity(seatNo, SeatType.CLASSIC,100);
            seats.add(theatreSeat);
        }

//        TheatreSeatEntity theatreSeat6= new TheatreSeatEntity("2A",PLATINUM,100);
//        TheatreSeatEntity theatreSeat7= new TheatreSeatEntity("2B",PLATINUM,100);
//        TheatreSeatEntity theatreSeat8= new TheatreSeatEntity("2C",PLATINUM,100);
//        TheatreSeatEntity theatreSeat9= new TheatreSeatEntity("2D",PLATINUM,100);
//        TheatreSeatEntity theatreSeat10= new TheatreSeatEntity("2E",PLATINUM,100);

        //optimize
        for(int i=0;i<5;i++)
        {
            char ch = (char)('A'+i);
            String seatNo = "2"+ ch;
            TheatreSeatEntity theatreSeat= new TheatreSeatEntity(seatNo, PLATINUM,200);
            seats.add(theatreSeat);
        }


//        seats.add(theatreSeat1);
//        seats.add(theatreSeat2);
//        seats.add(theatreSeat3);
//        seats.add(theatreSeat4);
//        seats.add(theatreSeat5);
//        seats.add(theatreSeat6);
//        seats.add(theatreSeat7);
//        seats.add(theatreSeat8);
//        seats.add(theatreSeat9);
//        seats.add(theatreSeat10);

        theatreSeatRepository.saveAll(seats);

        return seats;

    }

    public List<String> getTheatreListWithMovie(String movieName){

        List<String> theatreList=new ArrayList<>();

        MovieEntity movieEntity= movieRepository.findByMovieName(movieName);

        List<ShowEntity> showEntityList= movieEntity.getListOfShows();

        for(ShowEntity showEntity: showEntityList) {
            TheatreEntity theatre = showEntity.getTheatre();

            theatreList.add(theatre.getName());
        }
        return theatreList;
    }

    public List<TheatreResponseDto> getAllTheatres(){
        List<TheatreEntity> theatreEntityList= theatreRepository.findAll();

        List<TheatreResponseDto> theatreResponseDtoList= new ArrayList<>();

        for(TheatreEntity theatre: theatreEntityList){

            TheatreResponseDto theatreResponseDto= TheatreResponseDto.builder().id(theatre.getId()).name(theatre.getName()).city(theatre.getCity()).address(theatre.getAddress()).build();

            theatreResponseDtoList.add(theatreResponseDto);
        }

        return theatreResponseDtoList;
    }

    public List<ShowResponseDto> getShowListInTheatreByTheatreName(String name){
        TheatreEntity theatre= theatreRepository.findByName(name);


        List<ShowEntity> showEntityList= theatre.getListOfShows();

        if(showEntityList==null){
            showEntityList= new ArrayList<>();
        }

        List<ShowResponseDto> showEntityResponseDtoList= new ArrayList<>();
        for(ShowEntity shows: showEntityList){

            ShowResponseDto showEntityResponseDto= ShowResponseDto.builder().id(shows.getId()).
                    showDate(shows.getShowDate()).showTime(shows.getShowTime()).movieName(shows.getMovie().getMovieName()).theatreName(shows.getTheatre().getName())
                    .multiplier(shows.getMultiplier()).build();;
            showEntityResponseDtoList.add(showEntityResponseDto);
        }
        return showEntityResponseDtoList;
    }




}
