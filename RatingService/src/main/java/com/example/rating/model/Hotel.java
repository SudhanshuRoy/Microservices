package com.example.rating.model;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hotel {
	@Id
	private String hotelId;
	private String name;
	private String location;
	private String about;

}
