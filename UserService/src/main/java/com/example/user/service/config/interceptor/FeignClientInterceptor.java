package com.example.user.service.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {
	private Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);
	@Autowired
	private OAuth2AuthorizedClientManager manager;

	/**
	 * Intercepts the Feign client request and adds the OAuth2 access token as an
	 * Authorization header. The access token is obtained from the
	 * OAuth2AuthorizedClientManager.
	 *
	 * @param template The Feign request template.
	 */
	@Override
	public void apply(RequestTemplate template) {
		// Create an OAuth2AuthorizeRequest to authorize with the client registration ID
		// and principal
		String token = manager.authorize(
				OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build())
				.getAccessToken().getTokenValue();
		logger.info("feign client interceptor: Token :  {} ", token);
		// Add the Authorization header with the access token to the request template
		template.header("Authorization", "Bearer " + token);

	}

}
