package com.example.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.user.service.interceptor.RestTemplateInterceptor;

@Configuration
@Component
public class RestClientInterceptorConfig {
	@Autowired
	private static OAuth2AuthorizedClientManager manager;

	@Bean
	@LoadBalanced
	static RestTemplate restTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new RestTemplateInterceptor(manager));
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

}
