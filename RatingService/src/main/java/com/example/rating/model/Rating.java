package com.example.rating.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
	@Id
	private String ratingId;

	private String userId;

	private String hotelId;

	private Integer rating;

	private String feedback;

}
