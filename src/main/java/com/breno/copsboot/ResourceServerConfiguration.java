package com.breno.copsboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer // Gives an resource server
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
	}
	
	public void confifure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			// OPTIONS request are allowed to anyone on any sub-path, allowing
			// "preflight requests".
			.antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
			.and()
			.antMatcher("/api/**").authorizeRequests()
			.anyRequest().authenticated(); // any request must be authenticated
	}
}
