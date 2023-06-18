package com.example.hotel.HotelService.service;

import java.util.List;

import com.example.hotel.HotelService.exception.HotelException;
import com.example.hotel.HotelService.model.Hotel;

public interface HotelService {

	public Hotel createHotel(Hotel hotel);

	public Hotel getHotel(String hotelId) throws HotelException;

	public List<Hotel> getAllHotels() throws HotelException;

}
