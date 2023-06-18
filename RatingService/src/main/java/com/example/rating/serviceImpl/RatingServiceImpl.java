package com.example.rating.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rating.exception.RatingException;
import com.example.rating.model.Rating;
import com.example.rating.repository.RatingRepository;
import com.example.rating.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * Saves a new rating.
	 *
	 * @param rating the rating object to be saved
	 * @return the saved rating
	 */
	@Override
	public Rating giveRating(Rating rating) {
		logger.info("Giving rating: {}", rating);
		return ratingRepository.save(rating);
	}

	/**
	 * Retrieves a rating by rating ID.
	 *
	 * @param ratingId the ID of the rating to retrieve
	 * @return the rating object
	 * @throws RatingException if the rating couldn't be found
	 */
	@Override
	public Rating viewRatingByRatingId(String ratingId) throws RatingException {
		logger.info("Viewing rating with rating ID: {}", ratingId);
		return ratingRepository.findById(ratingId)
				.orElseThrow(() -> new RatingException("Rating not found with provided rating ID"));
	}

	/**
	 * Retrieves all ratings by user ID.
	 *
	 * @param userId the ID of the user
	 * @return a list of ratings for the user
	 * @throws RatingException if there is an error while retrieving the ratings
	 */
	@Override
	public List<Rating> viewRatingByUserId(String userId) throws RatingException {
		logger.info("Viewing ratings for user with ID: {}", userId);
		return ratingRepository.findByUserId(userId);
	}

	/**
	 * Retrieves all ratings by hotel ID.
	 *
	 * @param hotelId the ID of the hotel
	 * @return a list of ratings for the hotel
	 * @throws RatingException if there is an error while retrieving the ratings
	 */
	@Override
	public List<Rating> RatingByHotelId(String hotelId) throws RatingException {
		logger.info("Viewing ratings for hotel with ID: {}", hotelId);
		return ratingRepository.findByHotelId(hotelId);
	}

	/**
	 * Retrieves all ratings.
	 *
	 * @return a list of all ratings
	 */
	@Override
	public List<Rating> viewAllRating() {
		logger.info("Viewing all ratings");
		return ratingRepository.findAll();
	}

}
