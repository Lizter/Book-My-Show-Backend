package com.example.Book_my_show_backend.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookedTicketResponseDto {

    private String movieName;

    private String theatreName;

    private Date bookedDate;

    private LocalTime bookedTime;

    private int amount;
}
