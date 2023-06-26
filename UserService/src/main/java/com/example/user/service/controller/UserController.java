package com.example.user.service.controller;

import com.example.user.service.exception.UserException;
import com.example.user.service.external.services.RatingService;
import com.example.user.service.model.Rating;
import com.example.user.service.model.User;
import com.example.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RatingService ratingService;

	/**
	 * Creates a new user.
	 *
	 * @param user the user object to be created
	 * @return the created user object
	 * @throws UserException if there is an error while creating the user
	 */
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) throws UserException {
		logger.info("Received request to create user: {}", user);

		User savedUser = userService.saveUser(user);

		logger.info("User created successfully: {}", savedUser);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a user by ID.
	 *
	 * @param userId the ID of the user to retrieve
	 * @return the user object
	 * @throws UserException if the user couldn't be found
	 */
	int retryCount = 1;
	@PreAuthorize("hasAuthority('Admin')  || hasAuthority('Normal')")
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod =, "ratingHotelFallBack")
	@Retry(name = "ratinghotel", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> viewUser(@PathVariable("userId") String userId) throws UserException {
		logger.info("Received request to view user with ID: {}", userId);

		logger.info("retry count : {}", retryCount++);

		User user = userService.getUser(userId);

		if (user == null) {
			logger.error("User with ID {} not found", userId);
			throw new UserException("User not found");
		}

		logger.info("User retrieved successfully: {}", user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
		logger.info("fall back function called because service is down ", ex.getMessage());
		User user = User.builder().userId("01").name("Ram !").email("ram@gmail.com")
				.about("if any service is down then this user will be returned").build();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Retrieves all users.
	 *
	 * @return a list of all users
	 * @throws UserException if there is an error while retrieving the users
	 */
	@PreAuthorize("hasAuthority('Admin')  || hasAuthority('Normal')")
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() throws UserException {
		logger.info("Received request to retrieve all users");

		List<User> users = userService.getAllUser();

		logger.info("Retrieved {} users", users.size());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Admin')  || hasAuthority('Normal')")
	@PostMapping("/createRating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {

		Rating savedRating = ratingService.creatRating(rating);

		return new ResponseEntity<Rating>(savedRating, HttpStatus.CREATED);
	}

	// Exception handling is done in the GlobalExceptionHandler class
}
