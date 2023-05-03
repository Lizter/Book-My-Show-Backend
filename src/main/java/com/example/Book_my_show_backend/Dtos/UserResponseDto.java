package com.example.Book_my_show_backend.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private int id;

    private  String name;

    private String mobile;

    private int age;

    private Date createdOn;

}
