package com.example.user.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Serializable {

	private String ratingId;

	private String userId;

	private String hotelId;

	private Integer rating;

	private String feedback;

	private Hotel hotel;

}
