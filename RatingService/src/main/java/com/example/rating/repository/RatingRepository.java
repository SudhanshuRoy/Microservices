package com.example.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rating.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {

	public List<Rating> findByHotelId(String hotelId);

	public List<Rating> findByUserId(String userId);

}
