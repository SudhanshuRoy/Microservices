package com.example.user.service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {
	@Id
	String userId;
	private String name;
	private String email;
	private String about;
	@Transient
	private List<Rating> ratings =new ArrayList<>();

}
