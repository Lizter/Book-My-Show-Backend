package com.example.Book_my_show_backend.Dtos;

import com.example.Book_my_show_backend.Models.MovieEntity;
import com.example.Book_my_show_backend.Models.TheatreEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowResponseDto {

    private int id;

    private LocalDate showDate;

    private LocalTime showTime;

    private double multiplier;

    private String movieName;

    private String theatreName;

}
