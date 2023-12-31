package com.example.rating.controller;

import com.example.rating.exception.RatingException;
import com.example.rating.model.Rating;
import com.example.rating.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	/**
	 * Creates a new rating.
	 *
	 * @param rating The Rating object to be created.
	 * @return ResponseEntity containing the created Rating object.
	 */
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
		logger.info("Received request to give rating: {}", rating);
		Rating savedRating = ratingService.giveRating(rating);
		logger.info("Rating given successfully: {}", savedRating);
		return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a rating by its ID.
	 *
	 * @param ratingId The ID of the rating to be retrieved.
	 * @return ResponseEntity containing the retrieved Rating object.
	 * @throws RatingException If the rating with the given ID is not found.
	 */
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/{ratingId}")
	public ResponseEntity<Rating> viewRatingByRatingId(@PathVariable("ratingId") String ratingId)
			throws RatingException {
		logger.info("Received request to view rating with ID: {}", ratingId);
		Rating rating = ratingService.viewRatingByRatingId(ratingId);
		logger.info("Rating retrieved successfully: {}", rating);
		return new ResponseEntity<>(rating, HttpStatus.OK);
	}

	/**
	 * Retrieves ratings by user ID.
	 *
	 * @param userId The ID of the user whose ratings to be retrieved.
	 * @return ResponseEntity containing the list of retrieved Rating objects.
	 * @throws RatingException If no ratings are found for the given user ID.
	 */
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin') || hasAuthority('Normal')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Rating>> viewRatingByUserId(@PathVariable("userId") String userId)
			throws RatingException {
		logger.info("Received request to view ratings for user with ID: {}", userId);
		List<Rating> ratings = ratingService.viewRatingByUserId(userId);
		logger.info("Ratings retrieved successfully for user with ID {}: {}", userId, ratings);
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}

	/**
	 * Retrieves ratings by hotel ID.
	 *
	 * @param hotelId The ID of the hotel whose ratings to be retrieved.
	 * @return ResponseEntity containing the list of retrieved Rating objects.
	 * @throws RatingException If no ratings are found for the given hotel ID.
	 */
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin') || hasAuthority('Normal')")
	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<List<Rating>> viewRatingByHotelId(@PathVariable("hotelId") String hotelId)
			throws RatingException {
		logger.info("Received request to view ratings for hotel with ID: {}", hotelId);
		List<Rating> ratings = ratingService.RatingByHotelId(hotelId);
		logger.info("Ratings retrieved successfully for hotel with ID {}: {}", hotelId, ratings);
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}

	/**
	 * Retrieves all ratings.
	 *
	 * @return ResponseEntity containing the list of retrieved Rating objects.
	 */
//	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin') || hasAuthority('Normal')")
	@GetMapping("")
	public ResponseEntity<List<Rating>> viewAllRating() {
		logger.info("Received request to view all ratings");
		List<Rating> ratings = ratingService.viewAllRating();
		logger.info("All ratings retrieved successfully: {}", ratings);
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}

	// Exception handling is done in the GlobalExceptionHandler class
}
