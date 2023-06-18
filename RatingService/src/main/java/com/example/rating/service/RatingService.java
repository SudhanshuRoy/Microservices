package com.example.rating.service;

import java.util.List;

import com.example.rating.exception.RatingException;
import com.example.rating.model.Rating;

public interface RatingService {

	public Rating giveRating(Rating rating);

	public Rating viewRatingByRatingId(String ratingId) throws RatingException;

	public List<Rating> viewRatingByUserId(String UserId) throws RatingException;

	public List<Rating> RatingByHotelId(String hotelId) throws RatingException;

	public List<Rating> viewAllRating();

}
