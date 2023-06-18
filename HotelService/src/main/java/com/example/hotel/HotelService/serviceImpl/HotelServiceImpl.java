package com.example.hotel.HotelService.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel.HotelService.model.Hotel;
import com.example.hotel.HotelService.repository.HotelRepository;
import com.example.hotel.HotelService.service.HotelService;

/**
 * Implementation of the HotelService interface.
 */
@Service
public class HotelServiceImpl implements HotelService {
	private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

	@Autowired
	private HotelRepository hotelRepository;

	/**
	 * Creates a new hotel.
	 *
	 * @param hotel the hotel to be created
	 * @return the created hotel
	 */
	@Override
	public Hotel createHotel(Hotel hotel) {
		logger.info("Creating hotel: {}", hotel);
		return hotelRepository.save(hotel);
	}

	/**
	 * Retrieves a hotel by its ID.
	 *
	 * @param hotelId the ID of the hotel to retrieve
	 * @return the hotel object, or null if not found
	 */
	@Override
	public Hotel getHotel(String hotelId) {
		logger.info("Retrieving hotel with ID: {}", hotelId);
		return hotelRepository.findById(hotelId).orElse(null);
	}

	/**
	 * Retrieves all hotels.
	 *
	 * @return a list of all hotels
	 */
	@Override
	public List<Hotel> getAllHotels() {
		logger.info("Retrieving all hotels");
		return hotelRepository.findAll();
	}
}
