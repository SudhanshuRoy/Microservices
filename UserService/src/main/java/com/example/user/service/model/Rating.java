package com.example.user.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Rating implements Serializable {

	@Id
	private String ratingId;

	private String userId;

	private String hotelId;

	private Integer rating;

	private String feedback;

	private Hotel hotel;

}
