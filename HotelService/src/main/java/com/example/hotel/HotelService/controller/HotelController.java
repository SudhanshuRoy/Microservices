package com.example.hotel.HotelService.controller;

import com.example.hotel.HotelService.exception.HotelException;
import com.example.hotel.HotelService.model.Hotel;
import com.example.hotel.HotelService.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	private HotelService hotelService;

	/**
	 * Creates a new hotel.
	 *
	 * @param hotel the hotel object to be created
	 * @return the created hotel object
	 * @throws HotelException if there is an error while creating the hotel
	 */
	@PostMapping("/create")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws HotelException {
		logger.info("Received request to create hotel: {}", hotel);

		Hotel createdHotel = hotelService.createHotel(hotel);

		logger.info("Hotel created successfully: {}", createdHotel);
		return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a hotel by ID.
	 *
	 * @param hotelId the ID of the hotel to retrieve
	 * @return the hotel object
	 * @throws HotelException if the hotel couldn't be found
	 */
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getHotel(@PathVariable("hotelId") String hotelId) throws HotelException {
		logger.info("Received request to get hotel with ID: {}", hotelId);

		Hotel hotel = hotelService.getHotel(hotelId);

		if (hotel == null) {
			logger.error("Hotel with ID {} not found", hotelId);
			throw new HotelException("Hotel not found");
		}

		logger.info("Hotel retrieved successfully: {}", hotel);
		return new ResponseEntity<>(hotel, HttpStatus.OK);
	}

	/**
	 * Retrieves all hotels.
	 *
	 * @return a list of all hotels
	 * @throws HotelException if there is an error while retrieving the hotels
	 */
	@GetMapping("")
	public ResponseEntity<List<Hotel>> getAllHotels() throws HotelException {
		logger.info("Received request to retrieve all hotels");

		List<Hotel> hotels = hotelService.getAllHotels();

		logger.info("Retrieved {} hotels", hotels.size());
		return new ResponseEntity<>(hotels, HttpStatus.OK);
	}

	// Exception handling is done in the GlobalExceptionHandler class
}
