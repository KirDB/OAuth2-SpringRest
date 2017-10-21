package com.rest.template.main;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import com.rest.model.Customer;

public class MainClass {
	
	private static final String CLIENT_ID = "guest";
	private static final String CLIENT_SECRET = "guest";

	public static void main(String[] args) {
		
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
	    ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
	    resource.setClientAuthenticationScheme(AuthenticationScheme.header);
	    resource.setAccessTokenUri("http://localhost:8080/springrestup/oauth/token");
	    resource.setClientId(CLIENT_ID);
	    resource.setClientSecret(CLIENT_SECRET);
	    resource.setGrantType("password");
	    resource.setUsername("bob");
	    resource.setPassword("abc123");
	    OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
	    OAuth2RestTemplate restTemplateQ     = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(accessToken));

	    
	    Customer customer = new Customer("k", "d", "kd@g.com", 0L);
	    System.out.println(restTemplateQ.postForObject("http://localhost:8080/springrestup/customers", customer, Customer.class));
	    
		ResponseEntity<List<Customer>> custList = restTemplateQ.exchange("http://localhost:8080/springrestup/customers", 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>(){});
				//getForObject("http://localhost:8080/springrestup/customers", Customer.class);
		System.out.println(custList.getBody());
		
	}

}
