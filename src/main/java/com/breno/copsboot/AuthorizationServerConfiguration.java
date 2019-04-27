package com.breno.copsboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer // Gives an authorization and a token endpoints
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager authenticationManager;
	/*
	 * It's necessary to create a class that implements this.
	 * 
	 * Spring Security needs to know what are the app's users.
	 */
	private UserDetailsService userDetailsService;
	
	/*
	 * Responsible for generate a hash from the user's password.
	 * 
	 * Spring Security needs this same guy to validate the passwords.
	 */
	private PasswordEncoder passwordEncoder;
	
	// What Spring will use to store the generated access tokens.
	private TokenStore tokenStore;
	
	protected AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, TokenStore tokenStore) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.tokenStore = tokenStore;
	}
	
	/**
	 * Defines the object responsible for passwords encoding.
	 */
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder);
	}
	
	/**
	 * 
	 */
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // tutorial purposes only
			   .withClient("copsboot-mobile-client") // hardcoded client ID
			   .authorizedGrantTypes("password", "refreshtoken")
			   .scopes("mobile_app")
			   .resourceIds(RESOURCE_ID)
			   .secret(passwordEncoder.encode("sadk!@lsadkjl4350&#aoji"));
	}
	
	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				 .authenticationManager(authenticationManager)
				 .userDetailsService(userDetailsService);
	}
}
