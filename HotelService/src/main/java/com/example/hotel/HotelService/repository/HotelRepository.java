package com.example.hotel.HotelService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.HotelService.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String>{

}
