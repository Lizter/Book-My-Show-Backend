package com.example.Book_my_show_backend.Controllers;

import com.example.Book_my_show_backend.Dtos.BookedTicketResponseDto;
import com.example.Book_my_show_backend.Dtos.UserRequestDto;
import com.example.Book_my_show_backend.Dtos.UserResponseDto;
import com.example.Book_my_show_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody UserRequestDto userRequestDto){

        return userService.createUser(userRequestDto);

    }

    @GetMapping("/find-user-by-name")
    public ResponseEntity<UserResponseDto> findUserByName(@RequestParam("name") String name){
        UserResponseDto userEntity= userService.findUserByName(name);
        return new ResponseEntity<>(userEntity, HttpStatus.FOUND);
    }

    @GetMapping("/get-all-user-booked-ticket")
    public ResponseEntity<List<BookedTicketResponseDto>> getAllTicketBookedByUser(@RequestParam("userId") int userId){
        List<BookedTicketResponseDto> bookedTicketList= userService.getAllTicketBookedByUser(userId);
        return new ResponseEntity<>(bookedTicketList,HttpStatus.FOUND);
    }




}
