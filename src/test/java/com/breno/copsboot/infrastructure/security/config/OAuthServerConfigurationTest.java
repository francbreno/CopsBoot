package com.breno.copsboot.infrastructure.security.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.breno.copsboot.infrastructure.SpringProfiles;
import com.breno.copsboot.user.UserService;
import com.breno.copsboot.user.Users;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // mocks the servlet environment
@ActiveProfiles(SpringProfiles.TEST)
public class OAuthServerConfigurationTest {

	/**
	 * 
	 */
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testGetAccessTokenAsOfficer() throws Exception {
		// DevelopomentDbInitializer is not executed here: @ActiveProfiles(SpringProfiles.TEST)
		userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
		
		String clientId = "test-client-id";
		String clientSecret = "test-client-secret";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("username", Users.OFFICER_EMAIL);
		params.add("password", Users.OFFICER_PASSWORD);
		
		mvc.perform(post("/oauth/token") // performs a HTTP Post request
						.params(params)  // the parameters of the request
						.with(httpBasic(clientId, clientSecret)) // add basic auth header
						.accept("application/json;charset=UTF-8")) // Content negotiation
		   .andExpect(status().isOk()) // expect that the response is OK
		   .andExpect(content().contentType("application/json;charset=UTF-8"))
		   .andDo(print()) // for debugging. not needed for tests
		   .andExpect(jsonPath("access_token").isString())
		   .andExpect(jsonPath("token_type").value("bearer"))
		   .andExpect(jsonPath("refresh_token").isString())
		   .andExpect(jsonPath("expires_in").isNumber())
		   .andExpect(jsonPath("scope").value("mobile_app"));
	}
}
