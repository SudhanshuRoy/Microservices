package com.example.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.user.service.model.Rating;

@FeignClient("RATING-SERVICE")
public interface RatingService {

	@PostMapping("/ratings/create")
	Rating creatRating(Rating rating);
}
