package com.example.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gateway.model.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	/**
	 * Handles the /login endpoint for retrieving authentication details.
	 *
	 * @param client The OAuth2AuthorizedClient for the "okta" client registration.
	 * @param user   The OidcUser representing the authenticated user.
	 * @return ResponseEntity containing the authentication response.
	 */

	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
			@AuthenticationPrincipal OidcUser user) {

		logger.info("user email id {}", user.getEmail());

		AuthResponse authResponse = new AuthResponse();

		authResponse.setUserId(user.getEmail());

		authResponse.setAccessToken(client.getAccessToken().getTokenValue());

		authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());

		authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

		List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
			return grantedAuthority.getAuthority();
		}).collect(Collectors.toList());

		authResponse.setAuthorities(authorities);

		logger.info("Authentication response generated for user: {}", user.getEmail());

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

}
