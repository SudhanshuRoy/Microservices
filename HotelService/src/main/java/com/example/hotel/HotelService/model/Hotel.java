package com.example.hotel.HotelService.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "hotels")
public class Hotel {
	@Id
	private String hotelId;
	private String name;
	private String location;
	private String about;

}
