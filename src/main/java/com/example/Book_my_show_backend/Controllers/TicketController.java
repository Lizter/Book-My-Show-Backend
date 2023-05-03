package com.example.Book_my_show_backend.Controllers;

import com.example.Book_my_show_backend.Dtos.BookTicketRequestDto;
import com.example.Book_my_show_backend.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/book")
    public String bookTicket(@RequestBody BookTicketRequestDto bookTicketRequestDto){

        try {
            return ticketService.bookTicket(bookTicketRequestDto);
        }
        catch(Exception e){
            return "Requested seats not available";
        }

    }

    @DeleteMapping("cancel-ticket")
    public String cancelTicket(@RequestParam("userId") int userId, @RequestParam("ticketId") int ticketId) {
        int deductedMoney = ticketService.cancelTicket(userId, ticketId);

        try {
            return "Ticket cancelled successfully and Rs." + " " + deductedMoney + " " + "of your amount got deducted";
        } catch (Exception e) {
            return "Ticket cancelled";
        }
    }

}
