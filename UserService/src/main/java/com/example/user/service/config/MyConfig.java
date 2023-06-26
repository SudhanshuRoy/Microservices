package com.example.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.example.user.service.config.interceptor.RestTemplateInterceptor;

@Configuration
public class MyConfig {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

	/**
	 * Configures a RestTemplate bean with load-balancing support and a custom
	 * interceptor. setting rest client interceptor manually in rest template
	 * 
	 * manager bean is available in this class only that's why we use didn't
	 * autowired and directly called after auto wiring the parameters
	 * 
	 * @return The configured RestTemplate.
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		RestTemplate restTemplate = new RestTemplate();

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

		interceptors. add(
				new RestTemplateInterceptor(manager(clientRegistrationRepository, oAuth2AuthorizedClientRepository)));

		restTemplate.setInterceptors(interceptors);

		return restTemplate;

	}

	/**
	 * Creates a bean for OAuth2AuthorizedClientManager.
	 * 
	 * using this manager only we will extract the token in interceptors
	 *
	 * @param clientRegistrationRepository    The repository for client
	 *                                        registrations.
	 * @param auth2AuthorizedClientRepository The repository for authorized OAuth2
	 *                                        clients.
	 * @return The configured OAuth2AuthorizedClientManager.
	 */

	@Bean
	public OAuth2AuthorizedClientManager manager(ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {
		OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials()
				.build();
		DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
				clientRegistrationRepository, auth2AuthorizedClientRepository);
		defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
		return defaultOAuth2AuthorizedClientManager;

	}

}
