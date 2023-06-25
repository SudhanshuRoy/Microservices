package com.example.gateway.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

	private String userId;
	private String accessToken;
	private String refreshToken;

	private long expireAt;

	private Collection<String> authorities;

}
