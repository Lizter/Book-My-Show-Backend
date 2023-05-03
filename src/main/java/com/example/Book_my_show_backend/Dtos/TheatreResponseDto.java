package com.example.Book_my_show_backend.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreResponseDto {

    private int id;

    private String name;

    private String city;

    private String address;




}
