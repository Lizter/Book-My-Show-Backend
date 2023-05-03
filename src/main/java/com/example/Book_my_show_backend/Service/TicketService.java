package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.BookTicketRequestDto;
import com.example.Book_my_show_backend.Models.ShowEntity;
import com.example.Book_my_show_backend.Models.ShowSeatEntity;
import com.example.Book_my_show_backend.Models.TicketEntity;
import com.example.Book_my_show_backend.Models.UserEntity;
import com.example.Book_my_show_backend.Repository.ShowRepository;
import com.example.Book_my_show_backend.Repository.TicketRepository;
import com.example.Book_my_show_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;


    public String bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception{

        //get the requested seats
        List<String> requestedSeats= bookTicketRequestDto.getRequestSeats();

        ShowEntity showEntity = showRepository.findById(bookTicketRequestDto.getShowId()).get();

        UserEntity userEntity= userRepository.findById(bookTicketRequestDto.getUserId()).get();


        //Get the showSeats from showEntity
        List<ShowSeatEntity> showSeats = showEntity.getListOfSeats();

        //validate if the seats requested by the user can be allocated

        List<ShowSeatEntity> bookedSeats = new ArrayList<>();

        for(ShowSeatEntity showSeat: showSeats)
        {
            String seatNo= showSeat.getSeatNo();

            if(showSeat.isBooked()==false&&requestedSeats.contains(seatNo)){
                bookedSeats.add(showSeat);
            }
        }

        if(bookedSeats.size()!=requestedSeats.size()){
            //Some or all seats requested by the user are unavailable
            throw new Exception ("Requested seats are not available");
        }

        //If code reaches here it means bookedSeats actually contain booked seats

        TicketEntity ticketEntity = new TicketEntity();

        double totalAmount=0;
        double multiplier= showEntity.getMultiplier();
        int rate=0;
        String allotedSeats="";

        //Calculating amount, setting bookStatus, setting
        for(ShowSeatEntity bookedSeat : bookedSeats)
        {
            bookedSeat.setBooked(true);
            bookedSeat.setBookedAt(new Date());
            bookedSeat.setTicket(ticketEntity);
            bookedSeat.setShow(showEntity);

            String seatNo= bookedSeat.getSeatNo();
            allotedSeats= allotedSeats+seatNo+",";

            if(seatNo.charAt(0)=='1'){
                rate=100;
            }
            else{
                rate=200;
            }
            totalAmount= totalAmount+ multiplier*rate;
        }

        ticketEntity.setBooked_at(new Date());
        ticketEntity.setAmount((int)totalAmount);
        ticketEntity.setShow(showEntity);
        ticketEntity.setUser(userEntity);
        ticketEntity.setBookedSeats(bookedSeats);
        ticketEntity.setAlloted_seats(allotedSeats);

        //To be done - add bidirectional mapping

        ticketRepository.save(ticketEntity);

        return "Successfully booked a ticket";


    }

    public int cancelTicket(int userId, int ticketId) {


        //get ticket entity to be cancelled
        TicketEntity ticketEntity = ticketRepository.findById(ticketId).get();



        int totalAmount= ticketEntity.getAmount();

        int deductedMoney=(int) ((20*totalAmount)/100);

        ShowEntity showEntity= ticketEntity.getShow();

        //deleting ticket from list of ticket from showEntity

        List<TicketEntity> ticketOfShow= showEntity.getListOfTickets();
        ticketOfShow.remove(ticketEntity);
        showEntity.setListOfTickets(ticketOfShow);

        showRepository.save(showEntity);


        UserEntity userEntity = userRepository.findById(userId).get();

        //deleting ticket from list of tickets of user and updating user

        List<TicketEntity> ticketEntityList = userEntity.getListOfTickets();
        ticketEntityList.remove(ticketEntity);
        userEntity.setListOfTickets(ticketEntityList);


        List<ShowSeatEntity> bookedSeats = ticketEntity.getBookedSeats();

        for (ShowSeatEntity bookedSeat : bookedSeats) {

            bookedSeat.setBooked(false);
            bookedSeat.setBookedAt(null);
            bookedSeat.setTicket(null);

        }
        bookedSeats.clear();

        ticketRepository.delete(ticketEntity);
        userRepository.save(userEntity);

        return deductedMoney;
    }

}
