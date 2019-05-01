package com.breno.copsboot.user.web;

import static com.breno.copsboot.infrastructure.security.SecurityHelperForMockMvc.HEADER_AUTHORIZATION;
import static com.breno.copsboot.infrastructure.security.SecurityHelperForMockMvc.bearer;
import static com.breno.copsboot.infrastructure.security.SecurityHelperForMockMvc.obtainAccessToken;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.breno.copsboot.infrastructure.SpringProfiles;
import com.breno.copsboot.infrastructure.security.StubUserDetailsService;
import com.breno.copsboot.infrastructure.security.config.AuthorizationServerConfiguration;
import com.breno.copsboot.infrastructure.security.config.ResourceServerConfiguration;
import com.breno.copsboot.infrastructure.security.config.SecurityConfiguration;
import com.breno.copsboot.user.UserService;
import com.breno.copsboot.user.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@ActiveProfiles(SpringProfiles.TEST)
public class UserRestControllerTest {
	
	@TestConfiguration
	@Import({
		ResourceServerConfiguration.class,
		AuthorizationServerConfiguration.class
	})
	static class TestConfig {
		
		@Bean
		public UserDetailsService userDetailsService() {
			return new StubUserDetailsService();
		}
		
//		@Bean
//		public TokenStore tokenStore() {
//			return new InMemoryTokenStore();
//		}
		
		@Bean
		public SecurityConfiguration securityConfiguration() {
			return new SecurityConfiguration();
		}
	}
	
	// Test infrastructure will inject an instance
	@Autowired
    private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService service;	
	
	@Test
	public void givenNotAuthenticated_whenAskingMyDetail_forbidden() throws Exception {
		mvc.perform(get("/api/users/me"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void givenAuthenticatedAsOfficer_whenAskingMyDetails_details_returned() throws Exception {
		String accessToken = obtainAccessToken(
				mvc,
				Users.OFFICER_EMAIL,
				Users.OFFICER_PASSWORD);
		
		when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));
		
		mvc.perform(get("/api/users/me")
				.header(HEADER_AUTHORIZATION, bearer(accessToken)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("email").value(Users.OFFICER_EMAIL))
			.andExpect(jsonPath("roles").isArray())
			.andExpect(jsonPath("roles[0]").value("OFFICER"));
	}
	
	@Test
	public void testCreateOfficer() throws Exception {
		String email = "francbreno@test.com";
		String password = "MyP@assWordi$";
		
		CreateOfficerParameters parameters = new CreateOfficerParameters();
		parameters.setEmail(email);
		parameters.setPassword(password);
		
		when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password));
		
		mvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				// uses Jackson ObjectMapper to transform the parameters object into json data
				.content(objectMapper.writeValueAsString(parameters)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("email").value(email))
			.andExpect(jsonPath("roles").isArray())
			.andExpect(jsonPath("roles[0]").value("OFFICER"));
		
		verify(service).createOfficer(email, password);
	}
}
