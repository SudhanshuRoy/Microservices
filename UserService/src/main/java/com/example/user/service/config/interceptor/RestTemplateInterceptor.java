package com.example.user.service.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
	private OAuth2AuthorizedClientManager manager;

	private Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

	public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
		this.manager = manager;
	}

	/**
	 * Intercepts the RestTemplate request and adds the OAuth2 access token as an
	 * Authorization header. The access token is obtained from the
	 * OAuth2AuthorizedClientManager.
	 *
	 * @param request   The HTTP request.
	 * @param body      The request body.
	 * @param execution The request execution.
	 * @return The HTTP response.
	 * @throws IOException If an I/O error occurs.
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		// Obtain the access token from the OAuth2AuthorizedClientManager using the
		// authorizeRequest
		String token = manager.authorize(
				OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build())
				.getAccessToken().getTokenValue();

		logger.info("Rest Template interceptor: Token :  {} ", token);
		// Add the Authorization header with the access token to the request headers
		request.getHeaders().add("Authorization", "Bearer " + token);
		return execution.execute(request, body);
	}
}
