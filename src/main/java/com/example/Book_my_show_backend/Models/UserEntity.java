package com.example.Book_my_show_backend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import java.util.List;


import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class UserEntity {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    private int id;

    private String name;

    private String mobile;

    //new
    private int age;

    //for debugging time stamp will be helpful

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedOn;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<TicketEntity> listOfTickets;






}
