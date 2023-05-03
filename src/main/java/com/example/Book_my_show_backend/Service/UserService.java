package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.BookedTicketResponseDto;
import com.example.Book_my_show_backend.Dtos.UserRequestDto;
import com.example.Book_my_show_backend.Dtos.UserResponseDto;
import com.example.Book_my_show_backend.Models.TicketEntity;
import com.example.Book_my_show_backend.Models.UserEntity;
import com.example.Book_my_show_backend.Repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String createUser(UserRequestDto userRequestDto){

        //Converting userRequestDto to UserEntity
        UserEntity userEntity= UserEntity.builder().name(userRequestDto.getName()).mobile(userRequestDto.getMobile()).build();

        try {
            userRepository.save(userEntity);
        }
        catch(Exception e){
            return "User could not be added";
        }

        return "User added successfully";

    }

    public UserResponseDto findUserByName(String name){
        UserEntity userEntity= userRepository.findByName(name);
        UserResponseDto  userResponseDto = UserResponseDto.builder()
                .id(userEntity.getId()).age(userEntity.getAge()).name(userEntity.getName()).mobile(userEntity.getMobile())
                .createdOn(userEntity.getCreatedOn())
                .build();
        return userResponseDto;
    }

    public List<BookedTicketResponseDto> getAllTicketBookedByUser(int userId){

        UserEntity userEntity= userRepository.findById(userId).get();

        List<TicketEntity> ticketEntityList= userEntity.getListOfTickets();

        List<BookedTicketResponseDto> bookedTicketList= new ArrayList<>();

        for(TicketEntity ticket: ticketEntityList){

            BookedTicketResponseDto bookedTicketResponseDto=BookedTicketResponseDto.builder()
                    .amount(ticket.getAmount()).bookedDate(ticket.getBooked_at()).movieName(ticket.getShow().getMovie().getMovieName())
                    .theatreName(ticket.getShow().getTheatre().getName()).bookedTime(ticket.getShow().getShowTime())
                    .build();


            bookedTicketList.add(bookedTicketResponseDto);
        }
        return bookedTicketList;
    }




}
