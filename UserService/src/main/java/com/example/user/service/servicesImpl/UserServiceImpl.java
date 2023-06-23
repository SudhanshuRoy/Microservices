package com.example.user.service.servicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.user.service.external.services.HotelService;
import com.example.user.service.external.services.RatingService;
import com.example.user.service.model.Hotel;
import com.example.user.service.model.Rating;
import com.example.user.service.model.User;
import com.example.user.service.repository.UserRepository;
import com.example.user.service.services.UserService;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private HotelService hotelService;

	/**
	 * Saves a user.
	 *
	 * @param user the user to be saved
	 * @return the saved user
	 */
	@Override
	public User saveUser(User user) {
		logger.info("Saving user: {}", user);
		return userRepo.save(user);
	}

	/**
	 * Retrieves a user by its ID.
	 *
	 * @param userId the ID of the user to retrieve
	 * @return the user object, or null if not found
	 */
	@Override
	public User getUser(String userId) {
		logger.info("Retrieving user with ID: {}", userId);
		User user = null;
		Optional<User> opt = userRepo.findById(userId);

		if (opt.isPresent()) {
			user = opt.get();

			Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + userId,
					Rating[].class);

			List<Rating> ratingList = Arrays.stream(ratings).toList();

			List<Rating> finalResult = ratingList.stream().map(rating -> {

				/*
				 * ResponseEntity<Hotel> hotelResponse = restTemplate
				 * .getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(),
				 * Hotel.class); System.err.println(hotelResponse);
				 * logger.info("hotel response {}", hotelResponse); Hotel hotel =
				 * hotelResponse.getBody();
				 */

				Hotel hotel = hotelService.getHotel(rating.getHotelId());

				rating.setHotel(hotel);

				return rating;

			}).collect(Collectors.toList());

			user.setRatings(finalResult);

		}
		return user;
	}

	/**
	 * Retrieves all users.
	 *
	 * @return a list of all users
	 */
	@Override
	public List<User> getAllUser() {
		logger.info("Retrieving all users");
		return userRepo.findAll();
	}

}
