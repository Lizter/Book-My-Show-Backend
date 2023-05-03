package com.example.Book_my_show_backend.Repository;

import com.example.Book_my_show_backend.Models.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<TheatreEntity,Integer> {

    TheatreEntity findByName(String name);
    TheatreEntity findByNameAndCity(String name, String city);

}
